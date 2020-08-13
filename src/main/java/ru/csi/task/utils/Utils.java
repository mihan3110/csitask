package ru.csi.task.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
//Класс-библиотека с основными методами
public class Utils {


    //зменение даты по секундам
    public static Date setTime(Date date, Integer var) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, var);
        date = cal.getTime();
        return date;
    }
    //Проверка местонахождения цен
    public static boolean isOldContainsNew(Date beginPrice1, Date endPrice1, Date beginPrice2, Date endPrice2) {
        boolean s =beginPrice1.before(beginPrice2) && endPrice2.before(endPrice1)  ;
        return s;
    }

    public static boolean isNewContainsOld(Date beginPrice1, Date endPrice1, Date beginPrice2, Date endPrice2) {
        boolean s =beginPrice2.before(beginPrice1) && endPrice2.after(endPrice1);
        return s;
    }

    public static boolean isNewRight(Date beginPrice1, Date endPrice1, Date beginPrice2, Date endPrice2) {
        boolean s =beginPrice1.before(beginPrice2) && beginPrice2.before(endPrice1) && endPrice1.before(endPrice2);
        return s;
    }

    public static boolean isNewLeft(Date beginPrice1, Date endPrice1, Date beginPrice2, Date endPrice2) {
        boolean s =beginPrice1.after(beginPrice2) && endPrice2.after(beginPrice1) && endPrice1.after(endPrice2);
        return s;
    }
    public static Date getStartDate(Month month, int day) {
        LocalDate date = LocalDate.of(2020, month, day);
        return Date.from(date.atTime(LocalTime.MIN).plusHours(-4).toInstant(ZoneOffset.UTC));
    }

    public static Date getEndDate(Month month, int day) {
        LocalDate date = LocalDate.of(2020, month, day);
        return Date.from(date.atTime(LocalTime.MIN).plusDays(1).plusHours(-4).plusSeconds(-1).toInstant(ZoneOffset.UTC));
    }
}
