package com.Fuel.fuelservice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.Fuel.fuelservice.Objects.FuelStations;

import java.util.ArrayList;



public class FuelStationRecViewAdapter extends RecyclerView.Adapter<FuelStationRecViewAdapter.ViewHolder> {

    private ArrayList<FuelStations> fuelStations = new ArrayList<>();
    private Context context;

    public FuelStationRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fuelstation_list_stations, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.StationTitle.setText(fuelStations.get(position).getName());
        
    }
    public int getStationCount() {
        return fuelStations.size();
    }

    public void setFuelStations(ArrayList<FuelStations> items) {
        this.fuelStations = fuelStations;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView StationTitle;
        private TextView PetrolPrice;
        private TextView DieselPrice;
        private CardView parent;
        private ImageView imageView;
        public ViewHolder(@NonNull View StationView) {
            super(StationView);
            //parent = itemView.findViewById(R.id.recyclerView);
            StationTitle = StationView.findViewById(R.id.headerText);
        }
    }

    }
