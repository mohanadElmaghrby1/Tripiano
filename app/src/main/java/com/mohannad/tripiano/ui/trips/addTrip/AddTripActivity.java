package com.mohannad.tripiano.ui.trips.addTrip;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.maps.model.LatLng;
import com.mohannad.tripiano.MainActivity;
import com.mohannad.tripiano.R;
import com.mohannad.tripiano.data.model.Trip;
import com.mohannad.tripiano.databinding.ActivityAddTripBinding;
import com.mohannad.tripiano.ui.maps.MapsActivity;
import com.mohannad.tripiano.utils.DataAndTimeClicked;
import com.mohannad.tripiano.utils.DatePickerFragment;
import com.mohannad.tripiano.utils.TimePickerFragment;

public class AddTripActivity extends AppCompatActivity {

//    AddTripActivityBinding binding;
    ActivityAddTripBinding binding;
    private static int MAP_REQUEST_CODE=10;
    private LatLng newLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_trip);
        binding.etTripDatetime.setFocusable(false);
        binding.etTripDatetime.setClickable(true);
        binding.etLocationMap.setFocusable(false);
        binding.etLocationMap.setClickable(true);


    }

    public void addTrip(View view) {
        String name = binding.etTripName.getText().toString();
        String description = binding.etDescription.getText().toString();
        String locationname = binding.etLocationName.getText().toString();
        String datetime = binding.etTripDatetime.getText().toString();
        Trip trip=new Trip();
        trip.setName(name);
        if (newLocation!=null){
            trip.setLat(newLocation.latitude+"");
            trip.setLng(newLocation.longitude+"");
        }
        trip.setDatetime(datetime);
        trip.setStatus(0);
        trip.setDescription(description);
        trip.setLocationName(locationname);


        Backendless.Data.save(trip, new AsyncCallback<Trip>() {
            @Override
            public void handleResponse(Trip response) {
                Log.i("sssssssssss", response.getObjectId());
                Toast.makeText(getApplicationContext(),"new trip saved",Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("ssssssssssss", fault.getDetail());
                Toast.makeText(getApplicationContext(),fault.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

    public void showdatetime(View view) {

        showTimePicker();

        DatePickerFragment datePickerFragment = new DatePickerFragment(new DataAndTimeClicked() {
            @Override
            public void dateSelected(String date) {
                binding.etTripDatetime.setText(date);
            }

            @Override
            public void timeSelected(String time) {

            }
        });
        datePickerFragment.show(getSupportFragmentManager(),"tag");

    }

    public void showTimePicker(){
        TimePickerFragment timePickerFragment = new TimePickerFragment(new DataAndTimeClicked() {
            @Override
            public void dateSelected(String date) {
            }

            @Override
            public void timeSelected(String time) {
                binding.etTripDatetime.setText(binding.etTripDatetime.getText()+" "+time);
            }
        });
        timePickerFragment.show(getSupportFragmentManager(),"tag");
    }

    public void openMap(View view) {
        startActivityForResult(new Intent(this, MapsActivity.class) ,MAP_REQUEST_CODE );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MAP_REQUEST_CODE && resultCode==RESULT_OK){
            newLocation = (LatLng) data.getExtras().get("newlocation");
            String locationName = data.getStringExtra("locationname");
            binding.etLocationMap.setText(locationName);
        }
    }
}
