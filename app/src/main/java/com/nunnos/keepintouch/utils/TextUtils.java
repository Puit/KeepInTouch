package com.nunnos.keepintouch.utils;

import java.text.Normalizer;

public class TextUtils {

    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
    public static Boolean isNullOrEmpty(String text){
        return text == null || text.isEmpty();
    }
}
