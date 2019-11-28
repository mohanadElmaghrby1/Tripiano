package com.mohannad.tripiano.ui.trips.TripFragment;

import android.view.View;

import com.mohannad.tripiano.data.model.Trip;

public interface MapCallable {
    void onLocationClick(String latitude , String longitude );
    void onLongClick(Trip trip , View view);
}
