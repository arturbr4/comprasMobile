package br.com.mercados.compras.util;

import java.math.BigDecimal;
import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

    public static Calendar convertStringToCalendar(String data){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(data.getTime());
    }
}
