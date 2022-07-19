package com.nunnos.keepintouch.data;

import java.time.LocalDate;

public class CustomDate {
    private int day;
    private int month;
    private int year;

    public CustomDate() {
        //Required
    }

    public CustomDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    @Override
    public String toString() {
        String date = "";
        if (day < 10) {
            date = date + "0";
        }
        date = date + day + " / ";
        // +1 because January is zero
        if ((month + 1) < 10) {
            date = date + "0";
        }
        date = date + (month + 1) + " / " + year;
        return date;
    }

    public static CustomDate dateFromString(String text) {
        CustomDate date = new CustomDate();
        try {
            date.day = Integer.parseInt(text.substring(0, 2));
            date.month = Integer.parseInt(text.substring(3, 5));
            date.year = Integer.parseInt(text.substring(6, 10));
        }catch (Exception e){
            LocalDate localDate = LocalDate.now();
            date.day = localDate.getDayOfMonth();
            date.month = localDate.getMonthValue();
            date.year = localDate.getYear();
        }

        return date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


}
