package com.example.trabajo_final_grupo_11_dam;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para mostrar los elementos del carrito de compras en un RecyclerView.
 */
public class CarritoCompraAdapter extends RecyclerView.Adapter<CarritoCompraAdapter.CarritoViewHolder> {

    private List<Carta> itemsCarrito;
    private Context context;
    private OnCartChangeListener listener;

    /**
     * Constructor del adaptador.
     * @param itemsCarrito Lista de cartas del carrito de compras.
     * @param context Contexto de la aplicación.
     * @param listener Listener para los cambios en el carrito de compras.
     */
    public CarritoCompraAdapter(List<Carta> itemsCarrito, Context context, OnCartChangeListener listener) {
        this.itemsCarrito = itemsCarrito;
        this.context = context;
        this.listener = listener;
    }

    /**
     * Crea y devuelve una instancia de CarritoViewHolder.
     * @param parent El ViewGroup padre en el que se mostrará el nuevo View.
     * @param viewType El tipo de la vista.
     * @return Una nueva instancia de CarritoViewHolder.
     */
    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.perfil_producto_carrito, parent, false);
        return new CarritoViewHolder(itemView);
    }

    /**
     * Vincula los datos de una carta específica a los elementos de la vista en la posición dada.
     * @param holder El ViewHolder en el que se establecerán los datos.
     * @param position La posición del elemento en la lista de datos.
     */
    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        Carta cartaItem = itemsCarrito.get(position);
        holder.nombreProducto.setText(cartaItem.getNombreProducto());
        holder.precioProducto.setText(String.valueOf(cartaItem.getPrecioProducto()));

        holder.btnEliminarDelCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    itemsCarrito.remove(currentPosition);
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onCartChange(itemsCarrito);
                    }
                    Toast.makeText(context, "Producto eliminado del carrito", Toast.LENGTH_SHORT).show();
                    Log.d("CarritoCompraAdapter", "Item removed from carrito: " + cartaItem.getNombreProducto());
                }
            }
        });
    }

    /**
     * Devuelve el número de elementos en la lista de datos.
     * @return El número de elementos en la lista.
     */
    @Override
    public int getItemCount() {
        return itemsCarrito.size();
    }

    /**
     * Actualiza la lista de cartas del carrito y notifica los cambios.
     * @param itemsCarrito Nueva lista de cartas del carrito.
     */
    public void setItemsCarrito(List<Carta> itemsCarrito) {
        this.itemsCarrito = itemsCarrito;
        notifyDataSetChanged();
        if (listener != null) {
            listener.onCartChange(itemsCarrito);
        }
    }

    /**
     * ViewHolder para los elementos de la lista del carrito de compras.
     */
    public class CarritoViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreProducto, precioProducto;
        private Button btnEliminarDelCarrito;

        public CarritoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreProducto = itemView.findViewById(R.id.nombreProductoTextView);
            precioProducto = itemView.findViewById(R.id.precioProductoTextView);
            btnEliminarDelCarrito = itemView.findViewById(R.id.btnEliminarDelCarrito);
        }
    }
}

