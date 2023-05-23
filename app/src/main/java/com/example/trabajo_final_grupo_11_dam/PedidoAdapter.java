package com.example.trabajo_final_grupo_11_dam;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private List<Pedido> pedidos;

    public PedidoAdapter(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.perfil_pedido, parent, false);
        return new PedidoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoAdapter.PedidoViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);

        // Debugging code for all fields
        String idPedido = holder.itemView.getContext().getResources().getString(R.string.textIDPedido);
        String direccionRestaurante = holder.itemView.getContext().getResources().getString(R.string.textDireccion_Restaurante);
        String direccionCliente = holder.itemView.getContext().getResources().getString(R.string.textDireccion_Cliente);
        String precioTotal = holder.itemView.getContext().getResources().getString(R.string.textPrecio_Total);
        String clienteUsername = holder.itemView.getContext().getResources().getString(R.string.textCliente_Username);


        Log.d("PedidoAdapter", "idPedido: " + idPedido);
        Log.d("PedidoAdapter", "Direccion Restaurante: " + direccionRestaurante);
        Log.d("PedidoAdapter", "Direccion Cliente: " + direccionCliente);
        Log.d("PedidoAdapter", "Precio Total: " + precioTotal);
        Log.d("PedidoAdapter", "Cliente Username: " + clienteUsername);


        holder.tvIdPedido.setText(String.valueOf(pedido.getid_pedido()));
        holder.tvDireccionRestaurante.setText(pedido.getDireccionRestaurante());
        holder.tvPrecioTotal.setText(String.valueOf(pedido.getPrecioTotal()));
        holder.tvDireccionCliente.setText(pedido.getDireccionCliente());
        holder.tvClienteUsername.setText(pedido.getCliente_Username());


        int restaurantId = pedido.getRestauranteId();
        if (restaurantId == 1) {
            holder.restaurantImage.setImageResource(R.drawable.indian_image);
        } else if (restaurantId == 2) {
            holder.restaurantImage.setImageResource(R.drawable.chinese_image);
        } else if (restaurantId == 3) {
            holder.restaurantImage.setImageResource(R.drawable.turkish_image);
        } else if (restaurantId == 4) {
            holder.restaurantImage.setImageResource(R.drawable.american_image);
        } else if (restaurantId == 5) {
            holder.restaurantImage.setImageResource(R.drawable.italian_image);
        } else {
            // Default image if the ID doesn't match any specific image
            holder.restaurantImage.setImageResource(R.drawable.icono_restaurante);
        }

    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public class PedidoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvIdPedido;
        private TextView tvDireccionRestaurante;
        private TextView tvDireccionCliente;
        private TextView tvPrecioTotal;
        private TextView tvClienteUsername;
        private ImageView restaurantImage;
        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdPedido = itemView.findViewById(R.id.txtProducto);
            tvDireccionRestaurante = itemView.findViewById(R.id.txtDireccion_Restaurante);
            tvDireccionCliente = itemView.findViewById(R.id.txtDireccion_Cliente);
            tvPrecioTotal = itemView.findViewById(R.id.txtPrecio_Total);
            tvClienteUsername = itemView.findViewById(R.id.txtCliente_Username);
            restaurantImage = itemView.findViewById(R.id.IMAGEpedidos);
        }

        public void bindPedido(Pedido pedido) {
            tvIdPedido.setText(String.valueOf(pedido.getid_pedido()));
            tvDireccionRestaurante.setText(pedido.getDireccionRestaurante());
            tvDireccionCliente.setText(pedido.getDireccionCliente());
            tvPrecioTotal.setText(String.valueOf(pedido.getPrecioTotal()));
            tvClienteUsername.setText(pedido.getCliente_Username());
        }
    }
}
