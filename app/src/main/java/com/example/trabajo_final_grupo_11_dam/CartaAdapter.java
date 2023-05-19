package com.example.trabajo_final_grupo_11_dam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartaAdapter extends RecyclerView.Adapter<CartaAdapter.CartaViewHolder> {

    private List<Carta> menuList;
    private Context context;

    public static class CartaViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView priceTextView;
        public Button btn_add_to_cart;
        public ImageView foodImageView;

        public CartaViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.textProductoCarta);
            priceTextView = view.findViewById(R.id.txtPrecioCarta);
            btn_add_to_cart = view.findViewById(R.id.btn_add_to_cart);
            foodImageView = view.findViewById(R.id.foodImageView);
        }
    }

    public CartaAdapter(List<Carta> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @Override
    public CartaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.perfil_carta, parent, false);
        return new CartaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartaViewHolder holder, int position) {
        Carta cartaItem = menuList.get(position);
        holder.nameTextView.setText(cartaItem.getNombreProducto());
        holder.priceTextView.setText(String.valueOf(cartaItem.getPrecioProducto()));

        // Set the image based on item name
        String itemName = cartaItem.getNombreProducto().toLowerCase();
        if (itemName.contains("pizza")) {
            holder.foodImageView.setImageResource(R.drawable.pizza_image);
        } else if (itemName.contains("hamburguesa") || itemName.contains("cheeseburger")) {
            holder.foodImageView.setImageResource(R.drawable.hamburger_image);
        } else if (itemName.contains("chicken") || itemName.contains("chicken nuggets")) {
            holder.foodImageView.setImageResource(R.drawable.chicken_image);
        } else if (itemName.contains("pasta")) {
            holder.foodImageView.setImageResource(R.drawable.pasta_image);
        } else if (itemName.contains("ensalada")) {
            holder.foodImageView.setImageResource(R.drawable.salad_image);
        } else if (itemName.contains("french fries") || itemName.contains("potato fries")) {
            holder.foodImageView.setImageResource(R.drawable.fries_image);
        } else if (itemName.contains("McFlurry")) {
            holder.foodImageView.setImageResource(R.drawable.mcflurry_image);
        } else if (itemName.contains("milkshake")) {
            holder.foodImageView.setImageResource(R.drawable.milkshake_image);
        } else if (itemName.contains("Cocacola") || itemName.contains("Fanta")) {
            holder.foodImageView.setImageResource(R.drawable.soda_image);
        } else if (itemName.contains("taco") || itemName.contains("burrito") || itemName.contains("quesadilla")) {
            holder.foodImageView.setImageResource(R.drawable.mexican_food_image);
        } else if (itemName.contains("doner") || itemName.contains("kebab")) {
            holder.foodImageView.setImageResource(R.drawable.turkish_food_image);
        } else {
            // Default image if the type doesn't match any specific image
            holder.foodImageView.setImageResource(R.drawable.default_food_image);
        }




        holder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add item to cart
                // TODO: Add function to add to cart
                Toast.makeText(context, "¡Producto Añadido!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return menuList.size();
    }
}

