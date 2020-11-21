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

import com.Fuel.fuelservice.Objects.Car;
import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.fragment.BottomSheetFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;



public class CarRecViewAdapter extends RecyclerView.Adapter<CarRecViewAdapter.ViewHolder> {

    private ArrayList<Car> cars = new ArrayList<>();
    private Context context;

    public CarRecViewAdapter(Context context) {
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
        return cars.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        String carInfo =
                cars.get(position).getModel()) +  +
                cars.get(position).getManufacturer());

        holder.StationTitle.setText(cars.get(position).getRegNumber().toUpperCase());
        holder.PetrolPrice.setText(cars.get(position).getModel());
        holder.DieselPrice.setText(cars.get(position).getManufacturer());
        holder.nearby.setText("");


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Enter specific item

                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(((FragmentActivity)context).getSupportFragmentManager(), bottomSheetFragment.getTag());
                Car car = cars.get(position);

                String fuelStationName = car.getRegNumber();
                String petrolString = String.valueOf(car.getModel());
                String dieselString = String.valueOf(car.getManufacturer());


                Intent intent = new Intent(context, BottomSheetFragment.class);
                Bundle bundle = new Bundle();
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


    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView StationTitle;
        private TextView PetrolPrice;
        private TextView DieselPrice;
        private TextView TvStationView;
        private ImageView imageView;
        private CardView parent;
        private TextView nearby;

        public ViewHolder(@NonNull View StationView) {
            super(StationView);
            parent = itemView.findViewById(R.id.recycleViewParent);
            StationTitle = StationView.findViewById(R.id.headerText);
            PetrolPrice = StationView.findViewById(R.id.petrolPrice);
            DieselPrice = StationView.findViewById(R.id.dieselPrice);
            imageView = itemView.findViewById(R.id.icon);
            nearby = StationView.findViewById(R.id.nearby);
        }
    }

    public long getItemId(int position) {
        return cars.get(position).getId();
    }
}
