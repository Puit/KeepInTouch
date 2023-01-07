package com.nunnos.keepintouch.utils;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TextUtils {

    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    public static boolean isNumeric(String string) { //Usar punto, no coma
        if (string == null || string.equals("")) {
            return false;
        }
        try {
            double doubleValue = Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isEmpty(String string) {
        return string == null || string.equals("");
    }

    public static String dateToString(Calendar calendar) {
        return dateToString(calendar, null);
    }

    public static String dateToString(Calendar calendar, String dateFormat) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat(dateFormat);
            return format1.format(calendar.getTime());
        } catch (Exception e) {
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            return format1.format(calendar.getTime());
        }

    }
}
