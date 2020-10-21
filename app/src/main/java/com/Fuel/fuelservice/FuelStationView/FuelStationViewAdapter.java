package com.Fuel.fuelservice.FuelStationView;

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


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.R;

public class FuelStationViewAdapter extends RecyclerView.Adapter<FuelStationViewAdapter.ViewHolder>{

    private ArrayList<FuelStations> fuelStations = new ArrayList<>();
    private Context context;

    public FuelStationViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fuelstation_list_stations, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTitle.setText(fuelStations.get(position).getTitle());

       /* Glide.with(context)
                .asBitmap()
                .load("https://picsum.photos/200%22")
                .into(holder.imageView);*/

    }

    @Override
    public int getItemCount() {
        return fuelStations.size();
    }

    public void setItems(ArrayList<FuelStations> items) {
        this.fuelStations = fuelStations;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView itemTitle, itemPrice;
        private CardView parent;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.txtName);
        }
    }

}