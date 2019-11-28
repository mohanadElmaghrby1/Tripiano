package com.mohannad.tripiano.ui.trips.TripFragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.IDataStore;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.servercode.BackendlessService;
import com.mohannad.tripiano.R;
import com.mohannad.tripiano.data.model.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class TripFragment extends Fragment implements MapCallable {

    public static final int COMING = 0;
    public static final int DONE = 1;
    public static final int CANCELED = 2;
    RecyclerView tripsRecyclerView;
    private ContentLoadingProgressBar progressBar;
    private int status;

    TripsAdapter tripsAdapter ;


    public TripFragment(int status) {
        this.status = status;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip, container, false);
        tripsRecyclerView = view.findViewById(R.id.rv_trips);
        tripsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tripsAdapter = new TripsAdapter(getContext() , this);
        tripsRecyclerView.setAdapter(tripsAdapter);
        progressBar= view.findViewById(R.id.progress_bar);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       loadTrips();
    }

    private void loadTrips(){
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        progressBar.setVisibility(View.VISIBLE);
        // set where clause
        queryBuilder.setWhereClause("status = "+(status));

        Backendless.Data.of(Trip.class).find(queryBuilder, new AsyncCallback<List<Trip>>(){
            @Override
            public void handleResponse( List<Trip> foundTrips ){
                progressBar.setVisibility(View.INVISIBLE);
                tripsAdapter.updateData(foundTrips);

            }
            @Override
            public void handleFault( BackendlessFault fault ){
                Toast.makeText(getContext() ,fault.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("loading error status "+status,fault.getDetail());
                progressBar.setVisibility(View.INVISIBLE);
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
    }

    @Override
    public void onLocationClick(String latitude, String longitude) {
// Creates an Intent that will load a map of San Francisco
//        Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longitude);
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        startActivity(mapIntent);

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?f=d&saddr=53.447,-0.878&daddr="+latitude+","+longitude));
        intent.setComponent(new ComponentName("com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity"));
        startActivity(intent);
    }

    @Override
    public void onLongClick(final Trip trip , View view) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(getContext(), view);
        //inflating menu from xml resource
        popup.inflate(R.menu.menu_options);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId= item.getItemId();
                if (itemId == R.id.action_delete){

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //delete trip
                                    deleteTrip(trip);
                                    dialog.dismiss();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to delete this trip ?").setPositiveButton("Delete", dialogClickListener)
                            .setNegativeButton("Cancel", dialogClickListener).show();

                }
                return false;
            }
        });
        popup.show();

    }

    private void deleteTrip(Trip trip){
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        progressBar.setVisibility(View.VISIBLE);
        // set where clause
        queryBuilder.setWhereClause("status = "+(status));

        Backendless.Data.of(Trip.class).remove(trip, new AsyncCallback<Long>() {
            @Override
            public void handleResponse(Long response) {
                Toast.makeText(getContext() , "trip deleted" ,Toast.LENGTH_SHORT ).show();
                loadTrips();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getContext() , fault.getMessage() ,Toast.LENGTH_SHORT ).show();
            }
        });
    }
}
