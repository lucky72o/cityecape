package com.cityescape.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateTimeHelper {

    public static final String CET = "CET";
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";


    /**
     * Get the current date/time as of CET.
     * @return
     */
    public static DateTime getCurrentDateTime () {
        return new DateTime(DateTimeZone.forID(CET));
    }

    /**
     * Get the date/time as of CET.
     * @return
     */
    public static DateTime getDateTime(DateTime dateTime) {
        return new DateTime(dateTime, DateTimeZone.forID(CET));
    }

    /**
     * Get the date/time as of CET.
     * @return
     */
    public static DateTime getDateTime(Date date) {
        return new DateTime(date, DateTimeZone.forID(CET));
    }


    /**
     * Get the date/time as of CET from String
     * @return
     */
    public static DateTime getDateTime(String date) {
        return new DateTime(date, DateTimeZone.forID(CET));
    }

    /**
     * Get the date/time as of CET from an event timestamp
     * @return
     */
    public static DateTime getEventDateTime(String dateStr) {
        return new DateTime(Long.parseLong(dateStr), DateTimeZone.forID(CET));
    }


    /**
     * Get the date/time as of CET as a String.
     * @return
     */
    public static String getDateTimeAsString(Date date) {

        if(date != null) {
           return getDateTime(date).toString(DateTimeHelper.DATE_FORMAT_PATTERN);
        } else {
            return "";
        }
    }

    public static Long getMillisFromString(String date) {
        return DATE_TIME_FORMAT.parseDateTime(date).getMillis();
    }

}
