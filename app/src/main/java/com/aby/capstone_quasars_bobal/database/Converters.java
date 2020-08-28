package com.aby.capstone_quasars_bobal.database;


import androidx.room.TypeConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public String fromArray(ArrayList<String> strings) {
        StringBuilder string = new StringBuilder();
        for(String s : strings) string.append(s).append(",");

        return string.toString();
    }

    @TypeConverter
    public ArrayList<String> toArray(String concatenatedStrings) {

        return new ArrayList<>(Arrays.asList(concatenatedStrings.split(",")));
    }
}