package com.example.trabajo_final_grupo_11_dam;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabajo_final_grupo_11_dam.Restaurantes;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private Context mContext;
    private List<Restaurantes> mRestaurantList;
    Calendar calendar = Calendar.getInstance();
    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    int currentMinute = calendar.get(Calendar.MINUTE);



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
        } else if (restaurantType.equals("Turca")) {
            holder.restaurantImage.setImageResource(R.drawable.turkish_image);
        } else if (restaurantType.equals("India")) {
            holder.restaurantImage.setImageResource(R.drawable.indian_image);
        } else {
            // Default image if the type doesn't match any specific image
            holder.restaurantImage.setImageResource(R.drawable.icono_restaurante);
        }


        // Get the opening and closing hours of the current restaurant
        String openingHour = currentRestaurant.getHorarioApertura();
        String closingHour = currentRestaurant.getHorarioCierre();

        // Parse the opening and closing hours to extract the hour and minute values
        int openingHourValue = extractHour(openingHour);
        int openingMinuteValue = extractMinute(openingHour);
        int closingHourValue = extractHour(closingHour);
        int closingMinuteValue = extractMinute(closingHour);

        // Compare the current time with the opening and closing hours
        if (isRestaurantClosed(currentHour, currentMinute, openingHourValue, openingMinuteValue, closingHourValue, closingMinuteValue)) {
            // Restaurant is closed, apply the desired visual indication (e.g., grey filter)
            holder.restaurantImage.setColorFilter(ContextCompat.getColor(mContext, R.color.lightGrey), PorterDuff.Mode.MULTIPLY);
        } else {
            // Restaurant is open, remove any visual filters (e.g., reset color filter)
            holder.restaurantImage.clearColorFilter();
        }

        holder.restaurantName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Restaurantes clickedRestaurant = mRestaurantList.get(position);
                    Log.d("RestaurantAdapter", "Restaurant: " + clickedRestaurant.getNombre() +
                            ", Opening Hour: " + clickedRestaurant.getHorarioApertura() +
                            ", Closing Hour: " + clickedRestaurant.getHorarioCierre());

                    int openingHour = extractHour(clickedRestaurant.getHorarioApertura());
                    int openingMinute = extractMinute(clickedRestaurant.getHorarioApertura());

                    int closingHour = extractHour(clickedRestaurant.getHorarioCierre());
                    int closingMinute = extractMinute(clickedRestaurant.getHorarioCierre());

                    Calendar now = Calendar.getInstance();
                    int currentHour = now.get(Calendar.HOUR_OF_DAY);
                    int currentMinute = now.get(Calendar.MINUTE);

                    if (isRestaurantClosed(currentHour, currentMinute, openingHour, openingMinute, closingHour, closingMinute)) {
                        // Restaurant is closed, show error message
                        Toast.makeText(mContext, "El restaurante se encuentra cerrado ahora mismo.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Restaurant is open, perform the desired action (e.g., navigate to restaurant details)
                        // Add your code here to handle the click event when the restaurant is open
                    }
                }
            }
        });

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



    private int extractHour(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.HOUR_OF_DAY);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int extractMinute(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MINUTE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }






    private boolean isRestaurantClosed(int currentHour, int currentMinute, int openingHour, int openingMinute, int closingHour, int closingMinute) {
        if (openingHour < closingHour) {
            if (currentHour < openingHour || currentHour > closingHour) {
                return true; // Restaurant is closed if the current hour is before opening or after closing
            } else if (currentHour == openingHour && currentMinute < openingMinute) {
                return true; // Restaurant is closed if the current hour is the opening hour but the current minute is before opening minute
            } else if (currentHour == closingHour && currentMinute > closingMinute) {
                return true; // Restaurant is closed if the current hour is the closing hour but the current minute is after closing minute
            } else {
                return false; // Restaurant is open
            }
        } else if (openingHour == closingHour) {
            if (currentHour == openingHour) {
                if (currentMinute < openingMinute || currentMinute > closingMinute) {
                    return true; // Restaurant is closed if the current hour is the opening/closing hour but current minute is outside the open interval
                } else {
                    return false; // Restaurant is open
                }
            } else {
                return (currentHour < openingHour || currentHour > closingHour); // Restaurant is closed if current hour is outside the open interval
            }
        } else { // openingHour > closingHour
            if (currentHour > closingHour && currentHour < openingHour) {
                return true; // Restaurant is closed if the current hour is after closing and before opening
            } else if ((currentHour == closingHour && currentMinute > closingMinute) ||
                    (currentHour == openingHour && currentMinute < openingMinute)) {
                return true; // Restaurant is closed if it's the closing/opening hour but the minute is outside the open interval
            } else {
                return false; // Restaurant is open
            }
        }
    }
}

