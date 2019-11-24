package com.mohannad.tripiano.ui.trips.TripFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
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


public class TripFragment extends Fragment {

    public static final int COMING = 0;
    public static final int DONE = 1;
    public static final int CANCELED = 2;
    RecyclerView tripsRecyclerView;
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
        tripsAdapter = new TripsAdapter(getContext());
        tripsRecyclerView.setAdapter(tripsAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        // set where clause
        queryBuilder.setWhereClause("status = "+(status==0?0:status==1?1:2));

        Backendless.Data.of(Trip.class).find(queryBuilder, new AsyncCallback<List<Trip>>(){
            @Override
            public void handleResponse( List<Trip> foundTrips ){
                for (int i = 0; i < foundTrips.size(); i++) {
                    tripsAdapter.updateData(foundTrips);
                    Toast.makeText(getContext() ,foundTrips.get(i).getName() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void handleFault( BackendlessFault fault ){
                Toast.makeText(getContext() ,fault.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("loading error status "+status,fault.getDetail());

                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });

//        Trip trip = new Trip();
//        trip.setLat("65");
//        trip.setLng("65");
//        trip.setName("abc");
//
//        trip.setDescription("msh mhma");
//
//        Date currentTime = Calendar.getInstance().getTime();
////        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM yyyy HH:mm");
//        String dateString = simpleDateFormat.format(currentTime);
//        trip.setDatetime(dateString);
//
//
////
////        String oldstring = "2011-01-18 00:00:00.0";
////        try {
////
////            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(oldstring);
////            trip.setDatetime(oldstring);
////
////
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
//
//
//        //coming
//        trip.setStatus(1);
//        Backendless.Data.save(trip, new AsyncCallback<Trip>() {
//            @Override
//            public void handleResponse(Trip response) {
//                Log.i("sssssssssss", response.getObjectId());
//
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                Log.i("ssssssssssss", fault.getDetail());
//
//            }
//        });

    }
}
