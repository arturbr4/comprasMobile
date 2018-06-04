package br.com.mercados.compras.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.mercados.compras.R;

public class Utils {

    public static Calendar convertStringToCalendar(String data){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            cal.setTime(sdf.parse(data));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static Calendar convertTimeMilisToCalendar(Long timeMilis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMilis);
        return calendar;
    }

    public static String convertCalendarToStringWithPattern(Calendar data , String pattern){
        String dataFormatada = "Sem Data";
        if(data != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            dataFormatada = sdf.format(data.getTime());
        }
        return dataFormatada;
    }

    public static String formataDoubleEConverterParaString(Double valor){
        String numeroFormatado = "0.00";
        String simboloMonetario = "R$ ";
        if(valor != null && valor > 0) {
            DecimalFormat df = new DecimalFormat("0.00");
            numeroFormatado = df.format(valor).toString();
        }
        return simboloMonetario + numeroFormatado;
    }

    public static Boolean convertIntToBoolean(Integer valor){
        return valor >0? true : false;
    }


    public static Bitmap tratarImagem(String caminhoFoto){
        try {
            return converterParaBitmap(caminhoFoto);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
            // TODO Implementar catch, e passar uma imagem default seguida de alguma mensagem de erro
        }
    }


    // TODO ALterar o nome do metodo para melhor dizer o que faz
    private static Bitmap converterParaBitmap(String caminhoFoto) throws IOException {
        if (caminhoFoto != null) {
            ExifInterface exif = null;

            exif = new ExifInterface(caminhoFoto);
            Integer orientacao = Integer.parseInt(exif.getAttribute(ExifInterface.TAG_ORIENTATION));
            Bitmap fotoReduzida = abrirFotoEReduzir(caminhoFoto);

            switch(orientacao){
                case ExifInterface.ORIENTATION_NORMAL:
                    return rotacionarBitmap(fotoReduzida,0);
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotacionarBitmap(fotoReduzida,90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotacionarBitmap(fotoReduzida,180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotacionarBitmap(fotoReduzida,270);

            }
        }
        return null;
    }

    private static Bitmap abrirFotoEReduzir(String caminhoFoto) {
        Bitmap fotoBitmap = BitmapFactory.decodeFile(caminhoFoto);
        return Bitmap.createScaledBitmap(fotoBitmap, 300, 300, true);
    }

    private static Bitmap rotacionarBitmap(Bitmap foto, Integer angulo) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angulo);
        Bitmap fotoBitmapRotacionada = Bitmap.createBitmap(foto,0,0,foto.getWidth(),foto.getHeight(),matrix,true);
        return fotoBitmapRotacionada;
    }



}
