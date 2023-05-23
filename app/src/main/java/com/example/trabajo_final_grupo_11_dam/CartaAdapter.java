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
    private CarritoCompra carritoCompra;

    /**
     * Clase ViewHolder para representar los elementos de la lista en el RecyclerView.
     */
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

    /**
     * Constructor de CartaAdapter.
     *
     * @param menuList La lista de elementos de carta a mostrar.
     * @param context  El contexto de la aplicación.
     * @param carritoCompra  La instancia de CarritoCompra.
     */
    public CartaAdapter(List<Carta> menuList, Context context, CarritoCompra carritoCompra) {
        this.menuList = menuList;
        this.context = context;
        this.carritoCompra = carritoCompra;
    }

    /**
     * Crea una nueva instancia de CartaViewHolder al inflar el diseño de elemento de carta.
     *
     * @param parent   El ViewGroup padre en el que se añadirá la vista inflada.
     * @param viewType El tipo de vista del elemento.
     * @return Una nueva instancia de CartaViewHolder.
     */
    @Override
    public CartaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.perfil_carta, parent, false);
        return new CartaViewHolder(view);
    }

    /**
     * Vincula los datos del elemento de carta en la posición dada con el ViewHolder.
     *
     * @param holder   El ViewHolder que contiene las vistas a actualizar.
     * @param position La posición del elemento de carta en la lista.
     */
    @Override
    public void onBindViewHolder(CartaViewHolder holder, int position) {
        Carta cartaItem = menuList.get(position);
        holder.nameTextView.setText(cartaItem.getNombreProducto());
        holder.priceTextView.setText(String.valueOf(cartaItem.getPrecioProducto()));

        // Establecer la imagen basada en el nombre del elemento
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
            // Imagen predeterminada si el tipo no coincide con ninguna imagen específica
            holder.foodImageView.setImageResource(R.drawable.default_food_image);
        }

        holder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carritoCompra.addCarta(cartaItem);
                Toast.makeText(context, "¡Producto Añadido!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return El número total de elementos en la lista.
     */
    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
