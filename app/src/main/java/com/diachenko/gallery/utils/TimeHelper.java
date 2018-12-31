package com.diachenko.gallery.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHelper {

    private static final String JANUARY = "янв";
    private static final String FEBRUARY = "фев";
    private static final String MARCH = "мар";
    private static final String APRIL = "апр";
    private static final String MAY = "мая";
    private static final String JUNE = "июн";
    private static final String JULY = "июл";
    private static final String AUGUST = "авг";
    private static final String SEPTEMBER = "сен";
    private static final String OCTOBER = "окт";
    private static final String NOVEMBER = "ноя";
    private static final String DECEMBER = "дек";

    public static String getTime(long time){
        String lastSeen = "";

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Long timeNow = (new Date().getTime()) ;
        String [] todayTime = sdf.format(timeNow).split("-");
        int simpleDayNow = Integer.parseInt(todayTime[0]);

        Date date = new Date(time );
        String thisTime = timeFormat.format(date);
        String[] formattedDate = sdf.format(date).split("-");
        int simpleDaySeen = Integer.parseInt(formattedDate[0]);

        String month = getMonth(Integer.valueOf(formattedDate[1]));
        if ((simpleDayNow - simpleDaySeen == 0 && ((timeNow - time) < 86400000) )) {
            lastSeen = thisTime;
        } else {
            if ((simpleDayNow - simpleDaySeen) == 1 && ((timeNow - simpleDayNow) < 172800000)) {
                lastSeen = "вчера";
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
        String mounth = "";
        switch (month) {
            case 01:
                mounth = JANUARY;
                break;
            case 02:
                mounth = FEBRUARY;
                break;
            case 03:
                mounth = MARCH;
                break;
            case 04:
                mounth = APRIL;
                break;
            case 05:
                mounth = MAY;
                break;
            case 06:
                mounth = JUNE;
                break;
            case 07:
                mounth = JULY;
                break;
            case 8:
                mounth = AUGUST;
                break;
            case 9:
                mounth = SEPTEMBER;
                break;
            case 10:
                mounth = OCTOBER;
                break;
            case 11:
                mounth = NOVEMBER;
                break;
            case 12:
                mounth = DECEMBER;
                break;
            default:
                mounth = "месяц";
                break;
        }
        return mounth;
    }
}
