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
                .load("https://picsum.photos/100")
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
