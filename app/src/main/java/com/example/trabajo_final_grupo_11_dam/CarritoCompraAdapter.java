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

public class CarritoCompraAdapter extends RecyclerView.Adapter<CarritoCompraAdapter.CarritoViewHolder> {

    private List<Carta> itemsCarrito;
    private Context context;
    private OnCartChangeListener listener;

    public CarritoCompraAdapter(List<Carta> itemsCarrito, Context context, OnCartChangeListener listener) {
        this.itemsCarrito = itemsCarrito;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.perfil_producto_carrito, parent, false);
        return new CarritoViewHolder(itemView);
    }

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

    @Override
    public int getItemCount() {
        return itemsCarrito.size();
    }

    public void setItemsCarrito(List<Carta> itemsCarrito) {
        this.itemsCarrito = itemsCarrito;
        notifyDataSetChanged();
        if (listener != null) {
            listener.onCartChange(itemsCarrito);
        }
    }

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

