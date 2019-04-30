package com.android.structure.managers;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.structure.helperclasses.ui.helper.KeyboardHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.android.structure.callbacks.OnCalendarUpdate;
import com.android.structure.callbacks.OnDatePicked;
import com.android.structure.constatnts.AppConstants;
import com.android.structure.helperclasses.ui.helper.UIHelper;

/**
 * Created by muhammadmuzammil on 4/20/2017.
 */

public class DateManager {


    private static SimpleDateFormat sdfDateInput = new SimpleDateFormat(AppConstants.INPUT_DATE_FORMAT);
    private static SimpleDateFormat sdfDateInputAmPm = new SimpleDateFormat(AppConstants.INPUT_DATE_FORMAT_AM_PM);
    private static SimpleDateFormat sdfLabDateInputAmPm = new SimpleDateFormat(AppConstants.INPUT_LAB_DATE_FORMAT_AM_PM);
    private static SimpleDateFormat sdfDateOuput = new SimpleDateFormat(AppConstants.OUTPUT_DATE_TIME_FORMAT);
    private static SimpleDateFormat sdfTimeInput = new SimpleDateFormat(AppConstants.INPUT_TIME_FORMAT);
    private static SimpleDateFormat sdfTimeOuput = new SimpleDateFormat(AppConstants.OUTPUT_TIME_FORMAT);
    private static SimpleDateFormat sdfUTCOutput = new SimpleDateFormat(AppConstants.OUTPUT_UTC);


    // Custom FOR AKUH
    public static SimpleDateFormat sdfDateInputImmunization = new SimpleDateFormat(AppConstants.INPUT_DATE_FORMAT_IMMUNIZATION);


    // Methods

    public static Date getDate(long millisecond) {
        return new Date(millisecond);
    }

    public static Date getDate(String date) {
        try {
            return sdfLabDateInputAmPm.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getDate(Date date, String format) {
        if (date == null) return "";
        return new SimpleDateFormat(format).format(date);
    }

    public static String getDate(String date, String format) {
        if (date == null) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.format(sdfDateInput.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static long getTimeInMillis(String date, String format) {
        if (date == null || date.isEmpty()) return 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        try {
            return simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static long getTimeInMillis(SimpleDateFormat simpleDateFormat, String date) {
        try {
            return simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return 0;
    }

    public static String getTime(long millisecond) {
        return sdfUTCOutput.format(new Date(millisecond));
    }


    public static String getCurrentUTCDateTime() {
        sdfUTCOutput.setTimeZone(TimeZone.getTimeZone("gmt"));
        return sdfUTCOutput.format(new Date());
    }

    public static String getFormattedDate(String inputDate) {
        try {
            return sdfDateOuput.format(sdfDateInput.parse(inputDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFormattedTime(String inputDate) {
        try {
            return sdfTimeOuput.format(sdfTimeInput.parse(inputDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFormattedTime(Date date) {
        if (date == null) return "";
        return sdfTimeOuput.format(date);
    }


    public static String getFormattedTime(long millis) {
        if (millis == 0) return "";
        return sdfTimeOuput.format(DateManager.getDate(millis));
    }


    public static String getDateAndTimeForInfo(long millis) {
        if (millis == 0) return "-";
        return getProperTimeDifference(getDate(millis), true);
    }

    public static String getFormattedDate(Date date) {
        if (date == null) return "";
        return sdfDateOuput.format(date);
//        return sdfDateOuput.format(sdfTimeInput.parse());
    }

    public static String getFormattedDate(long millis) {
        if (millis == 0) return "";
        return sdfDateOuput.format(DateManager.getDate(millis));
    }

    public static String getCustomFormattedDate(String format, Date date) {
        if (date == null) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }


    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static long getCurrentMillis() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis();
    }


    public static String getCurrentFormattedDate() {
//        return sdfDateInput.format(Calendar.getInstance().getTime());
        return sdfDateInputAmPm.format(Calendar.getInstance().getTime());
    }

    public static String getCurrentFormattedDate(String format) {
        SimpleDateFormat sdfInput = new SimpleDateFormat(format);
        return sdfInput.format(Calendar.getInstance().getTime());
    }

    public static String getCurrentFormattedDate(long time, String format) {
        SimpleDateFormat sdfInput = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return sdfInput.format(calendar.getTime());
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static String getTimeFromSeconds(int sec) {
        long hours = (sec) / (60 * 60);
        long minutes = (sec / 60) - (hours * 60);
        long seconds = sec % 60;
        return (hours < 1 ? "" : ((hours < 10) ? "0" + hours : hours) + ":") + ((minutes < 10) ? "0" + minutes : minutes) + ":" + ((seconds < 10) ? "0" + seconds : seconds);
    }

    public static String getTimeFromSecondsLong(long sec) {
        long minutes = sec / 60;
        long seconds = sec % 60;
        return ((minutes < 10) ? "0" + minutes : minutes) + ":" + ((seconds < 10) ? "0" + seconds : seconds);
    }

    public static String getTimeDifference(Date date) {
        Calendar cal1 = Calendar.getInstance(); /*Provided*/
        Calendar todayCal = Calendar.getInstance(); /*Current Time*/
        cal1.setTime(date);
        String mDateStr = getFormattedDate(date);
//        System.out.println("---------------RegDate---------" + mDateStr);
        String mDesiredString = "";
//        System.out.println("----------------CurrentTime-----------" + getCurrentFormattedDate(todayCal.getTimeInMillis() , OUTPUT_DATE_TIME_FORMAT));
        long diff = todayCal.getTimeInMillis() - cal1.getTimeInMillis();

        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffMinutes < 60) {
            mDesiredString = diffMinutes + " mins ago";
        } else if (diffHours < 23) {
            mDesiredString = diffHours + " hours ago";
        } else if (diffDays < 7) {
            mDesiredString = diffDays + " days ago";
        } else {
            mDesiredString = mDateStr;
        }

        return mDesiredString;
    }

    public static long getTimeDifferenceInSeconds(long millis) // Give and return in UTC Timestamp
    {
        Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("UTC")); /*Provided*/
        Calendar todayCal = Calendar.getInstance(); /*Current Time*/
        cal1.setTimeInMillis(millis);
        return (todayCal.getTimeInMillis() - cal1.getTimeInMillis()) / 1000;
    }


    public static String getProperTimeDifference(Date date, boolean isMessageInfo) {
        Calendar cal1 = Calendar.getInstance(); /*Provided*/
        Calendar todayCal = Calendar.getInstance(); /*Current Time*/
        if (date == null)
            return "";
        cal1.setTime(date);
        String mDateStr = getFormattedDate(date);
//        String mDesiredString = "";
        StringBuilder mDesiredString = new StringBuilder();
        long diff = todayCal.getTimeInMillis() - cal1.getTimeInMillis();

        long diffSecs = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffSecs < 60) {
            mDesiredString.append(" Just now");
        } else if (diffMinutes < 60) {
            mDesiredString.append(diffMinutes == 1 ? diffMinutes + " minute ago" : diffMinutes + " minutes ago");
        } else if (diffHours < 23) {
            mDesiredString.append("Today, ");
            mDesiredString.append(getFormattedTime(date));
        } else if (diffDays < 7) {
            if (diffDays == 1) {
                mDesiredString.append("Yesterday, ");
                mDesiredString.append(getFormattedTime(date));
            } else {
                mDesiredString.append(getFormattedDate(date));
                if (isMessageInfo) {
                    mDesiredString.append(", " + getFormattedTime(date));
                }
            }
        } else {
            mDesiredString.append(mDateStr);
        }

        return mDesiredString.toString();
    }

    public static String getProperTimeDifferenceStrings(Date date) {
        Calendar cal1 = Calendar.getInstance();  //Provided
        Calendar todayCal = Calendar.getInstance(); //Current Time
        cal1.setTime(date);
        String mDateStr = getFormattedDate(date);
        String mDesiredString = "";
        long diff = todayCal.getTimeInMillis() - cal1.getTimeInMillis();

        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffMinutes < 60) {
            mDesiredString = "Today";
        } else if (diffHours < 23) {
            mDesiredString = "Today";
        } else if (diffDays < 7) {
            if (diffDays == 1)
                mDesiredString = "Yesterday ";
            else
                mDesiredString = diffHours + " days ago";
        } else {
            mDesiredString = mDateStr;
        }

        return mDesiredString;
    }


    public static String getBroadcastTimeDifference(long date, boolean appendCreatedAt) {
        Calendar cal1 = Calendar.getInstance(); /*Provided*/
        Calendar todayCal = Calendar.getInstance(); /*Current Time*/
        cal1.setTimeInMillis(date);
        String mDateStr = getFormattedDate(date);
        StringBuilder mDesiredString = new StringBuilder();
        long diff = todayCal.getTimeInMillis() - cal1.getTimeInMillis();

        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (appendCreatedAt)
            mDesiredString.append("Created ");

        if (diffHours > 0 && diffHours < 23) {
            mDesiredString.append("today at ");
            mDesiredString.append(getFormattedTime(date));
        } else if (diffDays > 0 && diffDays < 7) {
            if (diffDays == 1) {
                mDesiredString.append(" yesterday at ");
                mDesiredString.append(getFormattedTime(date));
            } else {
                mDesiredString.append(" at ");
                mDesiredString.append(getFormattedDate(date));
            }
        } else {
            mDesiredString.append(" at ");
            mDesiredString.append(mDateStr);
        }

        return mDesiredString.toString();
    }


    public static String getMuteTimeDifference(long date) {
        Calendar cal1 = Calendar.getInstance(); /*Provided*/
        Calendar todayCal = Calendar.getInstance(); /*Current Time*/
        cal1.setTimeInMillis(date);
        String mDateStr = getFormattedDate(date);
        StringBuilder mDesiredString = new StringBuilder();
        long diff = cal1.getTimeInMillis() - todayCal.getTimeInMillis();

        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);


        if (diffHours > 0 && diffHours < 23) {
            mDesiredString.append("today, ");
            mDesiredString.append(getFormattedTime(date));
        } else if (diffDays > 0 && diffDays < 7) {
            if (diffDays == 1) {
                mDesiredString.append("tomorrow, ");
                mDesiredString.append(getFormattedTime(date));
            } else {
                mDesiredString.append(getFormattedDate(date));
                mDesiredString.append(" ");
                mDesiredString.append(getFormattedTime(date));
            }
        } else {
//            mDesiredString.append(mDateStr);
            mDesiredString.append(getFormattedDate(date));
            mDesiredString.append(" ");
            mDesiredString.append(getFormattedTime(date));
        }

        return mDesiredString.toString();
    }

    public static String getChatsDifferenceStrings(Date date) {
        Calendar cal1 = Calendar.getInstance();  //Provided
        Calendar todayCal = Calendar.getInstance(); //Current Time
        if (date == null)
            return "";
        cal1.setTime(date);
        String mDateStr = getFormattedDate(date);
        String mDesiredString = "";
        long diff = todayCal.getTimeInMillis() - cal1.getTimeInMillis();

        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffHours < 23 || diffMinutes < 60) {
//            mDesiredString = "Today";
            mDesiredString = sdfTimeOuput.format(date);
        } else if (diffDays < 7) {
            if (diffDays == 1)
                mDesiredString = "Yesterday ";
            else
                mDesiredString = diffDays + " days ago";
        } else {
            mDesiredString = mDateStr;
        }

        return mDesiredString;
    }


    public static void showDatePicker(final Context context, final TextView textView, final String customDateFormatToShow, final DatePickerDialog.OnDateSetListener onDateSetListener, boolean isCurrentDateMaxiumum, boolean isCurrentDateMinimum, Date dateToOpen) {
            KeyboardHelper.hideSoftKeyboard(context, textView);
            final Calendar myCalendar;
            myCalendar = Calendar.getInstance();
            if (dateToOpen != null) {
                myCalendar.setTime(dateToOpen);
            }
            DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = customDateFormatToShow; // In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                //UIHelper.showLongToastInCenter(context, sdf.format(myCalendar.getTime()));
                if (textView != null) {
                    textView.setText(sdf.format(myCalendar.getTime()));
                }
                if (onDateSetListener != null) {
                    onDateSetListener.onDateSet(view, year, monthOfYear, dayOfMonth);
                }
            };
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            if (isCurrentDateMaxiumum) {
                datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
            }
            if (isCurrentDateMinimum) {
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
            }
            datePickerDialog.show();
    }




    public static void showDatePicker(final Context context, final TextView textView, final DatePickerDialog.OnDateSetListener onDateSetListener, boolean isCurrentDateMaxiumum) {

        if (textView != null) {
            final Calendar myCalendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "MMMM dd, yyyy"; // In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    //UIHelper.showLongToastInCenter(context, sdf.format(myCalendar.getTime()));
                    textView.setText(sdf.format(myCalendar.getTime()));
                    if (onDateSetListener != null) {
                        onDateSetListener.onDateSet(view, year, monthOfYear, dayOfMonth);
                    }
                }

            };
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            if (isCurrentDateMaxiumum) {
                datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
            }
            datePickerDialog.show();
        } else {
            UIHelper.showLongToastInCenter(context, "Unable to show Date picker");
        }
    }

    public static void showDatePicker(final Context context, final OnDatePicked onDatePicked, boolean isCurrentDateMaxiumum, boolean isCurrentDateMinimum) {

        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MMMM dd, yyyy"; // In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                //UIHelper.showLongToastInCenter(context, sdf.format(myCalendar.getTime()));
                if (onDatePicked != null) {
                    onDatePicked.onDatePicked(sdf.format(myCalendar.getTime()));

                }

            }

        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        if (isCurrentDateMaxiumum) {
            datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
        }
        if (isCurrentDateMinimum) {
            datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
        }
        datePickerDialog.show();
    }


    public static void showDateTimePicker(final Context context, final TextView textView, final OnCalendarUpdate onCalendarUpdate, boolean setCurrentDateMinimum) {

        if (textView != null) {
            final Calendar myCalendar = Calendar.getInstance();

            final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    myCalendar.set(Calendar.HOUR_OF_DAY, i);
                    myCalendar.set(Calendar.MINUTE, i1);

                    String myFormat = "MMMM dd, yyyy HH:mm"; // In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    textView.setText(sdf.format(myCalendar.getTime()));

                    if (onCalendarUpdate != null) {
                        onCalendarUpdate.onCalendarUpdate(myCalendar);
                    }
                }
            };


            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(context, timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false);
                    timePickerDialog.show();


                }

            };

            DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            if (setCurrentDateMinimum) {
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
            }
            datePickerDialog.show();
        } else {
            UIHelper.showLongToastInCenter(context, "Unable to show Date picker");
        }
    }
}
