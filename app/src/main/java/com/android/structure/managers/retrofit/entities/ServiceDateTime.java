package com.android.structure.managers.retrofit.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Complete DateTime received from web service, TimeZone is not receieved from
 * the webservice..
 */
public class ServiceDateTime {
    Date date;

    public ServiceDateTime() {
    }

    public ServiceDateTime(String strDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");

        try {
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {

            e.printStackTrace();
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
