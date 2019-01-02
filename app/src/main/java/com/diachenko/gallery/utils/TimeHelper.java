package com.diachenko.gallery.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHelper {

    private static final String JANUARY = "jan";
    private static final String FEBRUARY = "feb";
    private static final String MARCH = "mar";
    private static final String APRIL = "apr";
    private static final String MAY = "may";
    private static final String JUNE = "jun";
    private static final String JULY = "jul";
    private static final String AUGUST = "aug";
    private static final String SEPTEMBER = "sep";
    private static final String OCTOBER = "oct";
    private static final String NOVEMBER = "nov";
    private static final String DECEMBER = "dec";
    private static final long ONE_DAY = 86400000;
    private static final long TWO_DAYS = 172800000;

    public static String getTime(long time){
        String lastSeen;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Long timeNow = (new Date().getTime()) ;
        String []todayTime = sdf.format(timeNow).split("-");
        int simpleDayNow = Integer.parseInt(todayTime[0]);

        Date date = new Date(time );
        String thisTime = timeFormat.format(date);
        String []formattedDate = sdf.format(date).split("-");
        int simpleDaySeen = Integer.parseInt(formattedDate[0]);

        String month = getMonth(Integer.valueOf(formattedDate[1]));
        if ((simpleDayNow - simpleDaySeen == 0 && ((timeNow - time) < ONE_DAY) )) {
            lastSeen = thisTime;
        } else {
            if ((simpleDayNow - simpleDaySeen) == 1 && ((timeNow - simpleDayNow) < TWO_DAYS)) {
                lastSeen = "yesterday";
            } else {
                lastSeen = formattedDate[0] + " " + month;
                if (Integer.parseInt(todayTime[2]) != Integer.parseInt(formattedDate[2])){
                    lastSeen +=" " +  formattedDate[2] ;
                }
            }
        }
        return  lastSeen;
    }

    private static String getMonth(int month) {
        String m = "";
        switch (month) {
            case 01:
                m = JANUARY;
                break;
            case 02:
                m = FEBRUARY;
                break;
            case 03:
                m = MARCH;
                break;
            case 04:
                m = APRIL;
                break;
            case 05:
                m = MAY;
                break;
            case 06:
                m = JUNE;
                break;
            case 07:
                m = JULY;
                break;
            case 8:
                m = AUGUST;
                break;
            case 9:
                m = SEPTEMBER;
                break;
            case 10:
                m = OCTOBER;
                break;
            case 11:
                m = NOVEMBER;
                break;
            case 12:
                m = DECEMBER;
                break;
            default:
                m = "месяц";
                break;
        }
        return m;
    }
}
