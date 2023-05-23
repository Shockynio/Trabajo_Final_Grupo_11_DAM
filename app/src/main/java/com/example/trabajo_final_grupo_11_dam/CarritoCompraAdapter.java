package com.example.trabajo_final_grupo_11_dam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarritoCompraAdapter extends RecyclerView.Adapter<CarritoCompraAdapter.CarritoViewHolder> {

    private List<Carta> itemsCarrito;
    private Context context;

    public CarritoCompraAdapter(List<Carta> itemsCarrito, Context context) {
        this.itemsCarrito = itemsCarrito;
        this.context = context;
    }

    public class CarritoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreProductoTextView;
        TextView precioProductoTextView;
        Button btnEliminarDelCarrito;

        public CarritoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreProductoTextView = itemView.findViewById(R.id.nombreProductoTextView);
            precioProductoTextView = itemView.findViewById(R.id.precioProductoTextView);
            btnEliminarDelCarrito = itemView.findViewById(R.id.btnEliminarDelCarrito);
        }
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.perfil_producto_carrito, parent, false);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        Carta cartaItem = itemsCarrito.get(position);
        holder.nombreProductoTextView.setText(cartaItem.getNombreProducto());
        holder.precioProductoTextView.setText(String.valueOf(cartaItem.getPrecioProducto()));
        holder.btnEliminarDelCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {  // Verifica si el ítem todavía existe
                    itemsCarrito.remove(currentPosition);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Producto eliminado del carrito", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsCarrito.size();
    }
}
