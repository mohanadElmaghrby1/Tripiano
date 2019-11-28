package com.mohannad.tripiano.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("ValidFragment")
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    DataAndTimeClicked dataAndTime;

    public TimePickerFragment(DataAndTimeClicked dt){
        this.dataAndTime=dt;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));

    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        dataAndTime.timeSelected(hourOfDay+":"+minute);
    }


    public static long dataTimeInMillis(String dateAndTime) throws ParseException {
        //parsing date into mills
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM yyyy HH:mm");
        Date date = dateFormat.parse(dateAndTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }
}