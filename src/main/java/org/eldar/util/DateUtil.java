package org.eldar.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Integer getCurrentYear(){
        return getCurrentTime(Calendar.YEAR);
    }

    public static Integer getCurrentMonth(){
        return getCurrentTime(Calendar.MONTH)+1;
    }

    public static Integer getCurrentDay(){
        return getCurrentTime(Calendar.DAY_OF_MONTH);
    }

    private static Integer getCurrentTime(int timeType) {
        return Calendar.getInstance().get(timeType);
    }

    public static Integer getNextYear() {
        return getSpecificTimeFromNow(Calendar.YEAR, 1);
    }

    public static Integer getNextMonth() {
        return getSpecificTimeFromNow(Calendar.MONTH, 1);
    }

    public static Integer getPreviousYear() {
        return getSpecificTimeFromNow(Calendar.YEAR, -1);
    }

    public static Integer getPreviousMonth() {
        return getSpecificTimeFromNow(Calendar.MONTH, -1);
    }

    private static Integer getSpecificTimeFromNow(int timeType, int amount){
        Calendar calendar = Calendar.getInstance();
        calendar.add(timeType, amount);
        return calendar.get(timeType);
    }

    public static Date getCurrentDate(){
        return Calendar.getInstance().getTime();
    }

    public static String getStringDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}
