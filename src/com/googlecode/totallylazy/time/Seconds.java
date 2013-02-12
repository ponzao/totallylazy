package com.googlecode.totallylazy.time;

import java.util.Date;

import static java.util.Calendar.SECOND;

public class Seconds {
    public static Date add(Date date, int amount) {
        return Dates.add(date, SECOND, amount);
    }

    public static Date subtract(Date date, int amount) {
        return Dates.subtract(date, SECOND, amount);
    }

    public static Long between(Date start, Date end) {
        return (end.getTime() - start.getTime()) / 1000L;
    }
}