package com.mohannad.tripiano.ui.trips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.backendless.Backendless;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mohannad.tripiano.R;
import com.mohannad.tripiano.data.model.Trip;
import com.mohannad.tripiano.databinding.ActivityTripBinding;
import com.mohannad.tripiano.ui.registeration.RegisterActivity;
import com.mohannad.tripiano.ui.trips.TripFragment.TripFragment;
import com.mohannad.tripiano.ui.trips.addTrip.AddTripActivity;

import java.util.List;

public class TripActivity extends AppCompatActivity {

    ActivityTripBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trip);

        binding.bottomNavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()){
                    case R.id.navigation_coming_trips:
                        fragment= new TripFragment(TripFragment.COMING);
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_past_trips:
                        fragment= new TripFragment(TripFragment.DONE);
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_canceled_trips:
                        fragment= new TripFragment(TripFragment.CANCELED);
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });
        binding.bottomNavigationView2.setSelectedItemId(R.id.navigation_coming_trips);
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openAddTripActivity(View view) {
        Intent intent =new Intent(this, AddTripActivity.class);
        startActivity(intent);
    }
}
