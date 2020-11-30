package com.Fuel.fuelservice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.fragment.BottomSheetFragment;
import com.bumptech.glide.Glide;
import com.google.maps.android.SphericalUtil;

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
        return fuelStations.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.StationTitle.setText(fuelStations.get(position).getName());
        holder.PetrolPrice.setText(fuelStations.get(position).petrolToString());
        holder.DieselPrice.setText(fuelStations.get(position).dieselToString());


        holder.parent.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view) {
                // Enter specific item

            BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
            bottomSheetFragment.show(((FragmentActivity)context).getSupportFragmentManager(), bottomSheetFragment.getTag());
                FuelStations fuelStation = fuelStations.get(position);

                String fuelStationId    = String.valueOf(fuelStation.getId());
                String fuelStationName = fuelStation.getName();
                String petrolString = String.valueOf(fuelStation.getPetrolPrice());
                String dieselString = String.valueOf(fuelStation.getDieselPrice());


                Intent intent = new Intent(context, BottomSheetFragment.class);
                Bundle bundle = new Bundle();
                bundle.putString("FuelStationId", fuelStationId);
                bundle.putString("FuelStation", fuelStationName);
                bundle.putString("DieselPrice", petrolString);
                bundle.putString("PetrolPrice", dieselString);
                intent.putExtra("myPackage", bundle);

                bottomSheetFragment.setArguments(bundle);

            }
    });

        Glide.with(context)
                .asBitmap()

                .load("data:image/png;base64,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")
                .into(holder.imageView);
    }


    public void setFuelStations(ArrayList<FuelStations> fuelStations) {
        this.fuelStations = fuelStations;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView StationTitle;
        private TextView PetrolPrice;
        private TextView DieselPrice;
        private TextView TvStationView;
        private ImageView imageView;
        private CardView parent;

        public ViewHolder(@NonNull View StationView) {
            super(StationView);
            parent = itemView.findViewById(R.id.recycleViewParent);
            StationTitle = StationView.findViewById(R.id.headerText);
            PetrolPrice = StationView.findViewById(R.id.petrolPrice);
            DieselPrice = StationView.findViewById(R.id.dieselPrice);
            imageView = itemView.findViewById(R.id.icon);
        }
    }

    public long getItemId(int position) {
        return fuelStations.get(position).getId();
    }
}
