package com.mohannad.tripiano.ui.trips.TripFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohannad.tripiano.R;
import com.mohannad.tripiano.data.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private Context mContext;
    private List<Trip> data;
    private boolean updated=false;
//    private TripCallBack callBack;

//    public TripsAdapter(Context context , TripCallBack callBack) {
//        this.mContext=context;
//        this.callBack=callBack;
//    }


    public TripsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateData(List<Trip> data) {
        this.data = data;
        updated=true;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (data != null && !data.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return viewType==VIEW_TYPE_NORMAL?new TripViewHolder(LayoutInflater.from(parent.getContext()), parent):new EmptyViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType()==VIEW_TYPE_NORMAL){
            Trip Trip = data.get(position);
            TripViewHolder tripViewHolder =(TripViewHolder)holder;
            tripViewHolder.name.setText(Trip.getName());
            tripViewHolder.dateTime.setText(Trip.getName());
            tripViewHolder.locationName.setText(Trip.getName());
            tripViewHolder.description.setText(Trip.getName());
        }else {
            EmptyViewHolder emptyViewHolder =(EmptyViewHolder)holder;
            emptyViewHolder.btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"retry" , Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(!updated)
            return 0;

        if (data == null)
            return 1;

        return data.size();
    }

    class TripViewHolder extends RecyclerView.ViewHolder {

        TextView name, dateTime, locationName, description;

        public TripViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list_trip, parent, false));
            name = itemView.findViewById(R.id.tv_name);
            dateTime = itemView.findViewById(R.id.tv_date_time);
            locationName = itemView.findViewById(R.id.tv_location_name);
            description = itemView.findViewById(R.id.tv_description);
        }

    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {


        Button btnRetry;
        public EmptyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_blog_empty_view, parent, false));
            btnRetry =  itemView.findViewById(R.id.btn_retry);

        }

    }
}
