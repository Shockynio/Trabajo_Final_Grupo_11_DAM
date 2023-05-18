package com.example.trabajo_final_grupo_11_dam;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabajo_final_grupo_11_dam.Restaurantes;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private Context mContext;
    private List<Restaurantes> mRestaurantList;


    public RestaurantAdapter(Context context, List<Restaurantes> restaurantList) {
        this.mContext = context;
        this.mRestaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.perfil_restaurante, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Log.d("RestaurantAdapter", "onBindViewHolder() called for position: " + position);
        Restaurantes currentRestaurant = mRestaurantList.get(position);

        // Set data to the views here.
        holder.restaurantName.setText(currentRestaurant.getNombre());

        // Set the image based on restaurant type
        String restaurantType = currentRestaurant.getEstiloComida();
        if (restaurantType.equals("China")) {
            holder.restaurantImage.setImageResource(R.drawable.chinese_image);
        } else if (restaurantType.equals("Americana")) {
            holder.restaurantImage.setImageResource(R.drawable.american_image);
        } else if (restaurantType.equals("Italiana")) {
            holder.restaurantImage.setImageResource(R.drawable.italian_image);
        } else {
            // Default image if the type doesn't match any specific image
            holder.restaurantImage.setImageResource(R.drawable.icono_restaurante);
        }
    }


    @Override
    public int getItemCount() {
        return mRestaurantList.size();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        // Declare your views here.
        MaterialButton restaurantName;
        ImageView restaurantImage;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize your views here.
            restaurantName = itemView.findViewById(R.id.buttonRestaurantName);
            restaurantImage = itemView.findViewById(R.id.imageViewRestaurant);
        }
    }
}
