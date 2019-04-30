package com.android.structure.managers.retrofit;

import com.android.structure.managers.retrofit.entities.ServiceDate;
import com.android.structure.managers.retrofit.entities.ServiceTime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import com.android.structure.managers.retrofit.entities.ServiceDateTime;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GsonFactory {

    public static final String DATE_FROMAT = "dd-MM-yyyy";
    public static final String DATE_FROMAT_2 = "dd/MM/yyyy";
    public static final String TIME_FROMAT = "HH:mm:ss";
    public static final String DATE_TIME_FROMAT = "yyyy-MM-dd HH:mm:ss";

    private static Gson configuredGson;
    private static Gson simpleGson;

    public static Gson getConfiguredGson() {

        if (configuredGson == null) {
            GsonBuilder builder = new GsonBuilder();
            // Date
            builder.registerTypeAdapter(ServiceDate.class,
                    new ServiceDateSerializer(DATE_FROMAT));

            // Date
            builder.registerTypeAdapter(ServiceDate.class, new ServiceDateSerializer(DATE_TIME_FROMAT));
            // Time
            builder.registerTypeAdapter(ServiceTime.class,
                    new ServiceTimeSerializer(TIME_FROMAT));
            // Date Time
            builder.registerTypeAdapter(ServiceDateTime.class,
                    new ServiceDateTimeSerializer(DATE_TIME_FROMAT));
            // java.util.Date
            builder.registerTypeAdapter(Date.class, new DateSerializer(
                    DATE_TIME_FROMAT));


            configuredGson = builder.create();
        }

        return configuredGson;
    }

    public static Gson getSimpleGson() {

        if (simpleGson == null) {
            GsonBuilder builder = new GsonBuilder();
            // Date
            builder.registerTypeAdapter(ServiceDate.class,
                    new ServiceDateSerializer(DATE_FROMAT));
            // Time
            builder.registerTypeAdapter(ServiceTime.class,
                    new ServiceTimeSerializer(TIME_FROMAT));
            // Date Time
            builder.registerTypeAdapter(ServiceDateTime.class,
                    new ServiceDateTimeSerializer(DATE_TIME_FROMAT));
            // java.util.Date
            builder.registerTypeAdapter(Date.class, new DateSerializer(
                    DATE_TIME_FROMAT));

            simpleGson = builder.create();
        }

        return simpleGson;
    }

    public static class ServiceDateSerializer implements
            JsonSerializer<ServiceDate>, JsonDeserializer<ServiceDate> {
        SimpleDateFormat sf;

        public ServiceDateSerializer(String format) {
            sf = new SimpleDateFormat(format);
        }

        @Override
        public JsonElement serialize(ServiceDate src, Type typeOfSrc,
                                     JsonSerializationContext context) {
            return new JsonPrimitive(sf.format(src.getDate()));
        }

        @Override
        public ServiceDate deserialize(JsonElement json, Type arg1,
                                       JsonDeserializationContext arg2) throws JsonParseException {
            ServiceDate date = new ServiceDate();
            try {
                date.setDate(sf
                        .parse(json.getAsJsonPrimitive().getAsString()));
            } catch (ParseException e) {
                // TODO : Add proper logs.
                return null;
            }
            return date;
        }
    }

    public static class ServiceTimeSerializer implements
            JsonSerializer<ServiceTime>, JsonDeserializer<ServiceTime> {

        SimpleDateFormat sf;

        public ServiceTimeSerializer(String format) {
            sf = new SimpleDateFormat(format);
        }

        @Override
        public JsonElement serialize(ServiceTime src, Type typeOfSrc,
                                     JsonSerializationContext context) {

            return new JsonPrimitive(sf.format(src.getDate()));
        }

        @Override
        public ServiceTime deserialize(JsonElement json, Type arg1,
                                       JsonDeserializationContext arg2) throws JsonParseException {
            ServiceTime time = new ServiceTime();
            try {
                time.setDate(sf
                        .parse(json.getAsJsonPrimitive().getAsString()));
            } catch (ParseException e) {
                // TODO : Add proper logs.
                return null;
            }
            return time;
        }
    }

    public static class ServiceDateTimeSerializer implements
            JsonSerializer<ServiceDateTime>, JsonDeserializer<ServiceDateTime> {

        SimpleDateFormat sf;

        public ServiceDateTimeSerializer(String format) {
            sf = new SimpleDateFormat(format);
        }

        @Override
        public ServiceDateTime deserialize(JsonElement json, Type arg1,
                                           JsonDeserializationContext arg2) throws JsonParseException {
            ServiceDateTime time = new ServiceDateTime();
            try {
                time.setDate(sf
                        .parse(json.getAsJsonPrimitive().getAsString()));
            } catch (ParseException e) {
                // TODO : Add proper logs.
                return null;
            }
            return time;
        }

        @Override
        public JsonElement serialize(ServiceDateTime src, Type arg1,
                                     JsonSerializationContext arg2) {
            return new JsonPrimitive(sf.format(src.getDate()));
        }

    }

    public static class DateSerializer implements JsonSerializer<Date>,
            JsonDeserializer<Date> {

        SimpleDateFormat sf;

        public DateSerializer(String format) {
            sf = new SimpleDateFormat(format);
        }

        @Override
        public Date deserialize(JsonElement json, Type arg1,
                                JsonDeserializationContext arg2) throws JsonParseException {
            Date time = null;
            try {
                time = sf.parse(json.getAsJsonPrimitive().getAsString());
            } catch (ParseException e) {
                // TODO : Add proper logs.
                return null;
            }
            return time;
        }

        @Override
        public JsonElement serialize(Date src, Type arg1,
                                     JsonSerializationContext arg2) {
            return new JsonPrimitive(sf.format(src));
        }

    }

}