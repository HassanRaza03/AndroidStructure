package com.android.structure.helperclasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.time.DateUtils;


public class DateHelper {

    private DateHelper() {
    }


    public static String getFormattedDeliveryDateAndTime(String stringFromServer) {
        return convertToClientRequiredTimeZone(convertServerStringToRequiredDate(stringFromServer));
    }


    /**
     * Conversion to "yyyy-MM-dd" this format.
     *
     * @param userSelectedDate
     * @return
     */
    public static String convertToServerRequiredTimeZone(Date userSelectedDate) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        return sdf.format(userSelectedDate);
    }


    /**
     * Conversion to "yyyy-MM-dd" this format.
     *
     * @param userSelectedDate
     * @return
     */
    public static String convertToClientRequiredTimeZone(Date userSelectedDate) {
        String myFormat = "dd MMMM,yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(userSelectedDate);
    }


    /**
     * Conversion to "yyyy-MM-dd" this format date.
     *
     * @param stringFromServer
     * @return
     */
    public static Date convertServerStringToRequiredDate(String stringFromServer) {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(stringFromServer);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * Compare Dates to return the difference in formated fashion. Since it
     * access values and requires context, it shoudnt be put in DateHelper.
     * <p/>
     * Yet to decide the best place for this context dependendent code.
     * <p/>
     * for e.g. 3 sec , 23 mins , 5 hrs
     *
     * @return
     */
    public static String retComparisionOfDate(Date minDate, Date maxDate,
                                              Context ctx) {

        // int monthsString = R.string.common_months;
        // int weekString = R.string.common_weeks;
        // int dayString = R.string.common_days;
        // int hrsString = R.string.common_hrs;
        // int minString = R.string.common_mins;
        // int secString = R.string.common_secs;
        int temp;

        temp = computeMonthsBetweenDates(minDate, maxDate);
        // if ( temp > 0 )
        // return format( temp, ctx.getString( monthsString ) );
        //
        // temp = computeWeeksBetweenDates( minDate, maxDate );
        // if ( temp > 0 )
        // return format( temp, ctx.getString( weekString ) );
        //
        // temp = computeDaysBetweenDates( minDate, maxDate );
        // if ( temp > 0 )
        // return format( temp, ctx.getString( dayString ) );
        //
        // temp = computeHoursBetweenDates( minDate, maxDate );
        // if ( temp > 0 )
        // return format( temp, ctx.getString( hrsString ) );
        //
        // temp = computeMinsBetweenDates( minDate, maxDate );
        // if ( temp > 0 )
        // return format( temp, ctx.getString( minString ) );
        //
        // temp = computeSecondsBetweenDates( minDate, maxDate );
        // if ( temp > 0 )
        // return format( temp, ctx.getString( secString ) );

        return null;// format( 0, ctx.getString( secString ) );

    }

    private static String format(int i, String s) {
        return i + " " + s + " ago";
    }

    public static int computeMonthsBetweenDates(Date minDate, Date maxDate) {
        long diff = maxDate.getTime() - minDate.getTime();

        // return (int) (diff / (1000 * 60 * 24 * 60 * 30 ));
        return (int) (diff / (365.24 * 24 * 60 * 60 * 1000 / 12));

    }

    public static int computeWeeksBetweenDates(Date minDate, Date maxDate) {
        long diff = maxDate.getTime() - minDate.getTime();
        return (int) (diff / (24 * 1000 * 60 * 60 * 7));
    }

    public static int computeDaysBetweenDates(Date minDate, Date maxDate) {
        long diff = maxDate.getTime() - minDate.getTime();
        return (int) (diff / (24 * 1000 * 60 * 60));
    }

    public static long differenceDaysBetweenDates(String dateStart, String dateStop) {


        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = null;
        Date d2 = null;
        long diffDays = 0;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
//            System.out.print(diffHours + " hours, ");
//            System.out.print(diffMinutes + " minutes, ");
//            System.out.print(diffSeconds + " seconds.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diffDays;

    }

    public static int computeHoursBetweenDates(Date minDate, Date maxDate) {
        long diff = maxDate.getTime() - minDate.getTime();
        return (int) (diff / (1000 * 60 * 60));
    }

    public static int computeMinsBetweenDates(Date minDate, Date maxDate) {
        long diff = maxDate.getTime() - minDate.getTime();
        return (int) (diff / (1000 * 60));
    }

    public static int computeSecondsBetweenDates(Date minDate, Date maxDate) {
        long diff = maxDate.getTime() - minDate.getTime();
        return (int) (diff / (1000));
    }

    /**
     * Convert Current Date to GMT Date Locale
     */
    public static Date getDateInGMT(Date date) {
        long time = date.getTime();
        long offset = TimeZone.getDefault().getOffset(time);
        return new Date(time - offset);
    }

    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Convert GMT Date to Current Date Locale
     */
    public static Date getDateFromGMT(Date date) {
        long time = date.getTime();
        long offset = TimeZone.getDefault().getOffset(time);
        return new Date(time + offset);
    }

    public static String getCurrentDateFromGMT(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df.format(DateHelper.getDateFromGMT(date));
    }

    public static String getTimeZoneOffset() {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("Z");
        String localTime = date.format(currentLocalTime);
        return localTime;
    }

    public static int getTimeZoneOffsetAsInteger() {

        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        int offsetFromUtc = tz.getOffset(now.getTime()) / 1000;
        return offsetFromUtc;
    }

    public static String yesterdayOrToday(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        Calendar yesterday = Calendar.getInstance();
        yesterday.roll(Calendar.DAY_OF_MONTH, -1);

        if (isToday(date)) {
            return "Today ";
        } else if (isSameDay(date, yesterday.getTime())) {
            return "Yesterday ";
        } else {
            return format.format(date);
        }
    }

    /**
     * <p>
     * Checks if two dates are on the same day ignoring time.
     * </p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDayMonth(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }

    public static boolean isSameMonth(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)

                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }

    /**
     * <p>
     * Checks if two calendars represent the same day ignoring time.
     * </p>
     *
     * @param cal1 the first calendar, not altered, not null
     * @param cal2 the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
                .get(Calendar.DAY_OF_YEAR) == cal2
                .get(Calendar.DAY_OF_YEAR));
    }

    /**
     * <p>
     * Checks if a date is today.
     * </p>
     *
     * @param date the date, not altered, not null.
     * @return true if the date is today.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }

    /**
     * <p>
     * Checks if a calendar date is today.
     * </p>
     *
     * @param cal the calendar, not altered, not null
     * @return true if cal date is today
     * @throws IllegalArgumentException if the calendar is <code>null</code>
     */
    public static boolean isToday(Calendar cal) {
        return isSameDay(cal, Calendar.getInstance());
    }

    /**
     * <p>
     * Checks if the first date is before the second date ignoring time.
     * </p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if the first date day is before the second date day.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isBeforeDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isBeforeDay(cal1, cal2);
    }

    /**
     * <p>
     * Checks if the first calendar date is before the second calendar date
     * ignoring time.
     * </p>
     *
     * @param cal1 the first calendar, not altered, not null.
     * @param cal2 the second calendar, not altered, not null.
     * @return true if cal1 date is before cal2 date ignoring time.
     * @throws IllegalArgumentException if either of the calendars are <code>null</code>
     */
    public static boolean isBeforeDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA))
            return true;
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA))
            return false;
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR))
            return true;
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR))
            return false;
        return cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * <p>
     * Checks if the first date is after the second date ignoring time.
     * </p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if the first date day is after the second date day.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isAfterDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isAfterDay(cal1, cal2);
    }

    /**
     * <p>
     * Checks if the first calendar date is after the second calendar date
     * ignoring time.
     * </p>
     *
     * @param cal1 the first calendar, not altered, not null.
     * @param cal2 the second calendar, not altered, not null.
     * @return true if cal1 date is after cal2 date ignoring time.
     * @throws IllegalArgumentException if either of the calendars are <code>null</code>
     */
    public static boolean isAfterDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA))
            return false;
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA))
            return true;
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR))
            return false;
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR))
            return true;
        return cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * <p>
     * Checks if a date is after today and within a number of days in the
     * future.
     * </p>
     *
     * @param date the date to check, not altered, not null.
     * @param days the number of days.
     * @return true if the date day is after today and within days in the future
     * .
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isWithinDaysFuture(Date date, int days) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return isWithinDaysFuture(cal, days);
    }

    /**
     * Returns true for all dates before today.
     *
     * @return
     */
    public static boolean isFutureDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        cal.add(Calendar.DAY_OF_MONTH, -1);

        return cal.getTime().compareTo(new Date()) >= 0;
    }

    /**
     * <p>
     * Checks if a calendar date is after today and within a number of days in
     * the future.
     * </p>
     *
     * @param cal  the calendar, not altered, not null
     * @param days the number of days.
     * @return true if the calendar date day is after today and within days in
     * the future .
     * @throws IllegalArgumentException if the calendar is <code>null</code>
     */
    public static boolean isWithinDaysFuture(Calendar cal, int days) {
        if (cal == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar today = Calendar.getInstance();
        Calendar future = Calendar.getInstance();
        future.add(Calendar.DAY_OF_YEAR, days);
        return (isAfterDay(cal, today) && !isAfterDay(cal, future));
    }

    /**
     * Returns the given date with the time set to the start of the day.
     */
    public static Date getStart(Date date) {
        return clearTime(date);
    }

    /**
     * Returns the given date with the time values cleared.
     */
    public static Date clearTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Determines whether or not a date has any time values (hour, minute,
     * seconds or millisecondsReturns the given date with the time values
     * cleared.
     */

    /**
     * Determines whether or not a date has any time values.
     *
     * @param date The date.
     * @return true iff the date is not null and any of the date's hour, minute,
     * seconds or millisecond values are greater than zero.
     */
    public static boolean hasTime(Date date) {
        if (date == null) {
            return false;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.HOUR_OF_DAY) > 0) {
            return true;
        }
        if (c.get(Calendar.MINUTE) > 0) {
            return true;
        }
        if (c.get(Calendar.SECOND) > 0) {
            return true;
        }
        return c.get(Calendar.MILLISECOND) > 0;
    }

    /**
     * Returns the given date with time set to the end of the day
     */
    public static Date getEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * Returns the maximum of two dates. A null date is treated as being less
     * than any non-null date.
     */
    public static Date max(Date d1, Date d2) {
        if (d1 == null && d2 == null)
            return null;
        if (d1 == null)
            return d2;
        if (d2 == null)
            return d1;
        return (d1.after(d2)) ? d1 : d2;
    }

    /**
     * Returns the minimum of two dates. A null date is treated as being greater
     * than any non-null date.
     */
    public static Date min(Date d1, Date d2) {
        if (d1 == null && d2 == null)
            return null;
        if (d1 == null)
            return d2;
        if (d2 == null)
            return d1;
        return (d1.before(d2)) ? d1 : d2;
    }

    public static String getElapsedTimeNew(String sDate) {
        long between; // divide by 1000 to convert seconds
        long week = 0;
        long day = 0;
        long hour = 0;
        long minute = 0;
        long second;
        Date end;
        Date begin;

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        // "Fri Mar 07 16:34:38 GMT+05:00 2014"
        DateFormat sdfNew = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy",
                Locale.US);

        String systemTime = sdf.format(new Date());
        String finalTime = "";
        try {

            end = sdf.parse(systemTime);
            begin = sdfNew.parse(sDate);
            between = (end.getTime() - begin.getTime()) / 1000;
            day = between / (24 * 3600);
            hour = between % (24 * 3600) / 3600;
            minute = between % 3600 / 60;
            second = (between % 60) / 60;

            if (day > 0) {
                finalTime = finalTime.concat(" " + day + " day(s) ago");
                return finalTime;
            }
            if (hour > 0) {
                finalTime = finalTime.concat(" " + hour + " hour(s) ago");
                return finalTime;
            }
            if (minute > 0) {
                finalTime = finalTime.concat(" " + minute + " minute(s) ago");
                return finalTime;
            }
            if (second > 0) {
                finalTime = finalTime.concat(" " + second + " second(s) ago");
                return finalTime;
            } else {
                finalTime = finalTime.concat("0 Second(s) ago");
                return finalTime;
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        return finalTime;
    }

    /**
     * The maximum date possible.
     */
    public static Date MAX_DATE = new Date(Long.MAX_VALUE);

    /**
     * @return 2d 15hr ago
     * @author <b>String sDate </b>is Past Date to calculate time elapsed
     */
    public static String getElapsedTime(String sDate) {
        long between; // divide by 1000 to convert seconds
        long week = 0;
        long day = 0;
        long hour = 0;
        long minute = 0;
        long second;
        Date end;
        Date begin;
        if (sDate.contains("."))
            sDate = sDate.substring(0, sDate.indexOf(".")) + "Z";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                Locale.US);

        String systemTime = sdf.format(new Date());
        String finalTime = "";
        try {

            end = sdf.parse(systemTime);
            begin = sdf.parse(sDate);

            long timezone = getTimeZone() * 1000;
            begin.setTime(begin.getTime() + timezone);
            between = (end.getTime() - begin.getTime()) / 1000;
            day = between / (24 * 3600);
            hour = between % (24 * 3600) / 3600;
            minute = between % 3600 / 60;
            second = (between % 60) / 60;

            if (day > 0)
                finalTime = finalTime.concat(" " + day + "d");
            if (hour > 0)
                finalTime = finalTime.concat(" " + hour + "h");

            if (minute > 0)
                finalTime = finalTime.concat(" " + minute + "m");

            if (second > 0)
                finalTime = finalTime.concat(" " + second + "s");

        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        return finalTime;
    }

    /**
     * @return 2d 15hr ago
     * @author <b>String sDate </b>is Past Date to calculate time elapsed
     */
//    public static String getElapsedInDays(String sDate) {
//        long between; // divide by 1000 to convert seconds
//        long week = 0;
//        long day = 0;
//        long hour = 0;
//        long minute = 0;
//        long second;
//        Date end;
//        Date begin;
//
//        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
//
//        String systemTime = sdf.format(new Date()).toString();
//        String finalTime = "";
//        if (!StringUtils.isEmpty(sDate)) {
//
//            try {
//
//                end = sdf.parse(systemTime);
//                begin = sdf.parse(sDate);
//                between = (end.getTime() - begin.getTime()) / 1000;
//                day = between / (24 * 3600);
//                hour = between % (24 * 3600) / 3600;
//                minute = between % 3600 / 60;
//                second = (between % 60) / 60;
//
//                if (day > 48)
//                    try {
//                        finalTime = new Date(sDate).toString();
//
//                    } catch (Exception e) {
//                        // TODO: handle exception
//                    }
//
//                if (hour > 24)
//                    finalTime = " Yesterday";
//
//                if (hour <= 24)
//                    finalTime = " Today";
//
//                if (hour < 12)
//                    finalTime = hour + " hrs ago ";
//
//                if (hour < 1)
//                    finalTime = minute + " min ago ";
//
//                if (minute < 1)
//                    finalTime = second + " sec ago ";
//
//            } catch (ParseException e1) {
//                e1.printStackTrace();
//            }
//        }
//
//        return finalTime;
//    }
    public static String getFormattedDate(Date mDate) {

        SimpleDateFormat daysFormat = new SimpleDateFormat("MMM dd, yyyy",
                Locale.US);
        return daysFormat.format(mDate);
    }

    public static String getFormattedNewDate(Date mDate) {

        SimpleDateFormat daysFormat = new SimpleDateFormat("MM-dd-yyyy",
                Locale.US);
        return daysFormat.format(mDate);
    }

    public static String getFormattedTime(Date mDate) {

        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);
        return timeFormat.format(mDate);
    }

    public static String getFormattedTime24Hrs(Date mDate) {

        SimpleDateFormat timeFormat = new SimpleDateFormat("H:MM ", Locale.US);
        return timeFormat.format(mDate);
    }

    public static String getFormattedTimeNew(Date mDate) {

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM a", Locale.US);
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return timeFormat.format(mDate);
    }

    public static String getFormattedDateTime(Date mDate) {

        SimpleDateFormat timeFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return timeFormat.format(mDate);
    }

    public static String getParsedDateTime(String mDate, boolean getTimeOnly) {
        // FIME: milliseconds creating problems
        if (mDate != null) {

            if (mDate.contains("."))
                mDate = mDate.substring(0, mDate.indexOf(".")) + "Z";
            try {
                // Date date = DateUtils.parseDate(mDate,
                // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
                Date date = sdf.parse(mDate);
                int timezone = getTimeZone();
                int min = ((timezone / (60)) % 60);
                int hr = ((timezone / (60 * 60)) % 24);
                int timeDifference = hr;
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.HOUR_OF_DAY, timeDifference);
                if (min != 0)
                    cal.add(Calendar.MINUTE, min);
                Date newDate = cal.getTime();
                if (getTimeOnly) {
                    String realTime = getFormattedTime(newDate);
                    return realTime;
                } else {
                    return newDate.toString();
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public static String getParsedDateOnly(String mDate) {
        // FIME: milliseconds creating problems
        if (mDate.contains("."))
            mDate = mDate.substring(0, mDate.indexOf(".")) + "Z";
        try {
            // Date date = DateUtils.parseDate(mDate,
            // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss'Z'", Locale.getDefault());
            Date date = sdf.parse(mDate);
            int timezone = getTimeZone();
            int min = ((timezone / (60)) % 60);
            int hr = ((timezone / (60 * 60)) % 24);
            int timeDifference = hr;
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, timeDifference);
            if (min != 0)
                cal.add(Calendar.MINUTE, min);
            Date newDate = cal.getTime();

            String realTime = getFormattedDate(newDate);
            return realTime;

        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    public static String getParsedDateOnly(String mDate, boolean getDateOnly) {
        // FIME: milliseconds creating problems
        if (mDate.contains("."))
            mDate = mDate.substring(0, mDate.indexOf(".")) + "Z";
        try {
            // Date date = DateUtils.parseDate(mDate,
            // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            Date date = sdf.parse(mDate);
            int timezone = getTimeZone();
            int min = ((timezone / (60)) % 60);
            int hr = ((timezone / (60 * 60)) % 24);
            int timeDifference = hr;
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, timeDifference);
            if (min != 0)
                cal.add(Calendar.MINUTE, min);
            Date newDate = cal.getTime();
            if (getDateOnly) {
                String realTime = getFormattedNewDate(newDate);
                return realTime;
            } else {
                return newDate.toString();
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    /**
     * If date is Today, return time else return date in format MM/dd/yyyy
     *
     * @param mDate
     * @return
     */
    public static String getParsedDateORTime(String mDate) {
        // FIME: milliseconds creating problems
        if (mDate.contains("."))
            mDate = mDate.substring(0, mDate.indexOf(".")) + "Z";
        try {
            // Date date = DateUtils.parseDate(mDate,
            // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            Date date = sdf.parse(mDate);
            int timezone = getTimeZone();
            int min = ((timezone / (60)) % 60);
            int hr = ((timezone / (60 * 60)) % 24);
            int timeDifference = hr;
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, timeDifference);

            if (min != 0)
                cal.add(Calendar.MINUTE, min);
            Date newDate = cal.getTime();

            if (isToday(newDate)) {
                String realTime = getFormattedTime(newDate);
                return realTime;
            } else {
                SimpleDateFormat outputFormat = new SimpleDateFormat(
                        "MM/dd/yyyy", Locale.US);
                return outputFormat.format(newDate);
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        return null;
    }

//    public static Date getParsedDateFromString(String mDate) {
//        // FIME: milliseconds creating problems
//        if (mDate.contains("."))
//            mDate = mDate.substring(0, mDate.indexOf(".")) + "Z";
//        try {
//            Date date = DateUtils.parseDate(mDate, "yyyy-MM-dd'T'HH:mm:ss'Z'");
//
//            return date;
//
//        } catch (ParseException e1) {
//            e1.printStackTrace();
//        }
//
//        return null;
//    }

    public static int getTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        int offsetFromUtc = tz.getOffset(now.getTime()) / 1000;
        return offsetFromUtc;
    }

    public static long getSecondsDifference(Timestamp timeStamp) {
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());

        int offset = TimeZone.getDefault().getOffset(timeStamp.getTime());

        if (TimeZone.getDefault().inDaylightTime(
                Calendar.getInstance().getTime())) {
            offset = offset - TimeZone.getDefault().getDSTSavings();
        }
        final long referenceSeconds = (timeStamp.getTime() + offset) / 1000;

        final long currentTimeSeconds = (calendar.getTimeInMillis()) / 1000;

        final long differenceSeconds = (currentTimeSeconds - referenceSeconds);

        return differenceSeconds;
    }



    public static String TimeZone(String dateString) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        Date date = new Date();
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        formatter.setTimeZone(TimeZone.getTimeZone("gmt"));
        Log.e("LOCAL time :" + TimeZone.getTimeZone("gmt"),
                " " + formatter.format(date));
        return formatter.format(date);

    }

    public static String CurrentTimeZone(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        df.setTimeZone(TimeZone.getDefault());
        return df.format(date);

    }

    @SuppressLint("DefaultLocale")
    public static String HoursToAM_PM(String time) {

        DateFormat f1 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date d = null;
        try {
            d = f1.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DateFormat f2 = new SimpleDateFormat("h:mma", Locale.getDefault());
        return f2.format(d).toLowerCase();

    }

    public static String DateConversion(String date, String Orignalformat,
                                        String targetedFormat) {
        String formattedDate = "";
        // DateFormat originalFormat = new SimpleDateFormat("MMMM dd, yyyy",
        // Locale.ENGLISH);
        // DateFormat targetFormat = new SimpleDateFormat("yyyyMMdd",
        // Locale.ENGLISH);

        DateFormat originalFormat = new SimpleDateFormat(Orignalformat,
                Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(targetedFormat,
                Locale.ENGLISH);
        try {
            Date date_retrive = originalFormat.parse(date);
            formattedDate = targetFormat.format(date_retrive); // 20120821

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return formattedDate;

    }

}
