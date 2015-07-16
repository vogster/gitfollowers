package com.unlogicon.gitfollowers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Nik on 17.07.2015.
 */
public class Utils {

    public static final String parseDate(String s){

        StringBuilder output = new StringBuilder();

        TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        f.setTimeZone(utc);
        GregorianCalendar cal = new GregorianCalendar(utc);
        try {
            cal.setTime(f.parse(s));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(cal.getTime());
        output.append(cal.get(Calendar.DAY_OF_MONTH) + " ");
        output.append(month_date.format(cal.getTime()) + " ");
        output.append(cal.get(Calendar.YEAR));
        return output.toString();
    }

}
