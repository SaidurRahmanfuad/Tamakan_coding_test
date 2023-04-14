package com.saidur.own.tamakan.Utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UtilityHelper {
    private static final String[] monthNameArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static String[] monthNameArrayFull = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    static DatePickerDialog picker;
    static TimePickerDialog pickerTime;


    public static void _datePicker(TextView controlName, Context context) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(context,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String attSelectedMonth = dayOfMonth + "-" + (monthOfYear+1) + "-" + year1;
                    controlName.setText(attSelectedMonth);

                }, year, month, day);
        picker.show();
    }

    @SuppressLint("SetTextI18n")
    public static void _timePicker(TextView controlName, Context context) {
        final int[] thour = {0};
        final int[] tmin = { 0 };

        pickerTime = new TimePickerDialog(context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                (timePicker, hour, min) -> {
                   // thour = hour;
                   // tmin = min;
                    String time = hour + ":" + min;
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        Date date = sdf.parse(time);
                        SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm aa");
                        final String timestring = sdf12.format(date);
                        controlName.setText(timestring);


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }, 12, 0, false);
        pickerTime.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerTime.show();
       ;
    }


    public static <T> List<T> getList(String jsonArray, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return new Gson().fromJson(jsonArray, typeOfT);
    }

}
