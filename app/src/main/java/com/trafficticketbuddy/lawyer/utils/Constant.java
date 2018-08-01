package com.trafficticketbuddy.lawyer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by root on 3/1/18.
 */

public class Constant {
   // public static  String BASE_URL="http://13.58.150.208/buddy/";
   public static  String BASE_URL="http://www.trafficticketbuddy.ca/buddy/";
    public static  String ABOUT_US_URL="http://www.trafficticketbuddy.ca/contact-mobile.php";
    public static  String TERMS_URL="http://www.trafficticketbuddy.ca/terms-conditions.php";
    public static  String PRIVACY_URL="http://www.trafficticketbuddy.ca/privacy-policy.php";
   // public static  String BASE_URL=getBaseURL();
    public static  String API_KEY="0a2b8d7f9243305f2a4700e1870f673a";
    public static String USER_TYPE="LAWYER";
    public static String USER_COUNTRY="Canada";

    public enum ENVIRONMENT{
        DEVELOPMENT, STAGING, PRODUCTION
    }

    public static  final ENVIRONMENT environment = ENVIRONMENT.DEVELOPMENT;

    private static String getBaseURL() {
        if (environment == ENVIRONMENT.PRODUCTION) {
            return "http://13.58.150.208/buddy/";
        } else if (environment == ENVIRONMENT.STAGING) {
            return "http://13.58.150.208/buddy/";
        } else if (environment == ENVIRONMENT.DEVELOPMENT) {
            return "http://13.58.150.208/buddy/";
        }
        return null;

    }

    public static Long getMillisecond(String datetime){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date date = simpleDateFormat.parse(datetime);

            System.out.println("date : "+simpleDateFormat.format(date));
           return date.getTime();
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }
        return null;
    }

    public static String convertSecondsToHMmSs(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h,m,s);
    }

    public static int getDayDiff(String inputString1,String inputString2){
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        //String inputString1 = "23 01 1997";
       // String inputString2 = "27 04 1997";
        int daycount =0;

        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long endMili = date2.getTime();
            long startMili = date1.getTime();
            long diff = endMili - startMili;
            daycount =   (int) (diff / (1000*60*60*24));
           // System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            String err = e.getMessage();
            System.out.println("!!!! "+e.getMessage());
            e.printStackTrace();
           // return 0;
        }
        return (Math.abs(daycount)+1);
    }


}
