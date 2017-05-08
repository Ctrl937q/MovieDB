package com.example.moviedb.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());
        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (NullPointerException | ParseException e) {
        }

        return outputDate;
    }
}
