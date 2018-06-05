package br.com.mercados.compras.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.mercados.compras.R;
import br.com.mercados.compras.dao.CompraDAO;
import br.com.mercados.compras.dao.ProdutoDAO;
import br.com.mercados.compras.helpers.IncluirCompraHelper;
import br.com.mercados.compras.helpers.IncluirProdutoHelper;
import br.com.mercados.compras.model.Compra;
import br.com.mercados.compras.model.Produto;

public class IncluirComprasActivity extends AppCompatActivity {

    private static final int ID_GPS = 12;
    private IncluirCompraHelper helper;
    private LocationManager locationManager;
    private LocationListener locationListener;


    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_compra);
        helper = new IncluirCompraHelper(this);

        Intent intent = getIntent();
        Compra compra = (Compra) intent.getSerializableExtra("compra");
        if (compra != null) {
            helper.preencherFormularioCompra(compra);
        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET},ID_GPS);

                return;
            }
            else{
                buscarLocalizacao();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_incluir_compra,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_incluir_compra_salvar:
                Compra compra = helper.retornaCompra();
                CompraDAO dao = new CompraDAO();
                dao.inserirCompra(compra);
                Toast.makeText(IncluirComprasActivity.this, "Compra Salva", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case ID_GPS:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    buscarLocalizacao();
            break;
        }
    }

    @SuppressLint("MissingPermission")
    private void buscarLocalizacao() {
        Location coordenadaMaisRecente;
        Address endereco = null;

        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener,null);
        Location coordenadaGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener,null);
        Location coordenadaNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        coordenadaMaisRecente = verificarLocalizacaoMaisRecente(coordenadaGPS,coordenadaNetwork);
        endereco = buscarEnderecoComCoordenadas(coordenadaMaisRecente);

        if(endereco != null) {
            TextView textView = findViewById(R.id.incluir_compra_local);
            textView.setText(endereco.getAddressLine(0));

            TextView textView2 = findViewById(R.id.incluir_compra_nomeLocal);
            textView2.setText(endereco.getLocality());
        }

        //TODO terminar de inserit no TextView
        //inserirEnderecoNoFormulario(endereco);


//        locationManager.requestLocationUpdates("gps",5000,0,locationListener);

       // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        //locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener,null);
    }


    private Address buscarEnderecoComCoordenadas(Location coordenadaMaisRecente) {
        try {
            return buscarEnderecoComCoordenada(coordenadaMaisRecente);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Address buscarEnderecoComCoordenada(Location coordenadaMaisRecente) throws IOException {
        Geocoder geocoder;
        List<Address> enderecoCompleto = null;
        geocoder = new Geocoder(IncluirComprasActivity.this, Locale.getDefault());

        enderecoCompleto = geocoder.getFromLocation(coordenadaMaisRecente.getLatitude(), coordenadaMaisRecente.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        //TODO Retirar esse codigo daqui apois implementar como vai incluir no TextView
//        String address = enderecoCompleto.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//        String city = enderecoCompleto.get(0).getLocality();
//        String state = enderecoCompleto.get(0).getAdminArea();
//        String country = enderecoCompleto.get(0).getCountryName();
//        String postalCode = enderecoCompleto.get(0).getPostalCode();
//        String knownName = enderecoCompleto.get(0).getFeatureName(); // O
//        String localityName = enderecoCompleto.get(0).getSubLocality();

       // Toast.makeText(IncluirComprasActivity.this,"Adress "+address+" , Nome: "+knownName,Toast.LENGTH_LONG).show();

        if(enderecoCompleto.size() > 0)
            return enderecoCompleto.get(0);
        else
            return null;
    }

    private Location verificarLocalizacaoMaisRecente(Location coordenadaGPS, Location coordenadaNetwork) {
        //TODO Verificar se o horario da ultima coordenada e muito antigo, caso seja retornar null
        if (coordenadaGPS != null && coordenadaNetwork != null) {
            if (coordenadaGPS.getTime() < coordenadaNetwork.getTime())
                return coordenadaNetwork;
            else
                return coordenadaGPS;
        }
        if (coordenadaGPS == null && coordenadaNetwork != null)
            return coordenadaNetwork;

        if (coordenadaNetwork == null && coordenadaGPS != null)
            return coordenadaGPS;

        return null;
    }
}
