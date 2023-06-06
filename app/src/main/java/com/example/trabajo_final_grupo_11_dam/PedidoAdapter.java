package com.example.trabajo_final_grupo_11_dam;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private List<Pedido> pedidos;
    private PedidoClickListener listener;

    public PedidoAdapter(List<Pedido> pedidos, PedidoClickListener listener) {
        this.pedidos = pedidos;
        this.listener = listener;
    }

    public interface PedidoClickListener {
        void onPedidoClick(Pedido pedido, int position);



    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.perfil_pedido, parent, false);
        return new PedidoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);
        holder.bindPedido(pedido);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pedido.getIsTaken()) {
                    Toast.makeText(view.getContext(), "Este pedido ya ha sido tomado.", Toast.LENGTH_SHORT).show();
                } else if (listener != null) {
                    listener.onPedidoClick(pedido, position);
                }
            }
        });
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
        private CardView cardView;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdPedido = itemView.findViewById(R.id.txtProducto);
            tvDireccionRestaurante = itemView.findViewById(R.id.txtDireccion_Restaurante);
            tvDireccionCliente = itemView.findViewById(R.id.txtDireccion_Cliente);
            tvPrecioTotal = itemView.findViewById(R.id.txtPrecio_Total);
            tvClienteUsername = itemView.findViewById(R.id.txtCliente_Username);
            restaurantImage = itemView.findViewById(R.id.IMAGEpedidos);
            cardView = itemView.findViewById(R.id.cardView);
        }

        public void bindPedido(Pedido pedido) {
            tvIdPedido.setText(String.valueOf(pedido.getid_pedido()));
            tvDireccionRestaurante.setText(pedido.getDireccionRestaurante());
            tvDireccionCliente.setText(pedido.getDireccionCliente());
            tvPrecioTotal.setText(String.valueOf(pedido.getPrecioTotal()));
            tvClienteUsername.setText(pedido.getCliente_Username());

            int restaurantId = pedido.getRestauranteId();
            if (restaurantId == 1) {
                restaurantImage.setImageResource(R.drawable.indian_image);
            } else if (restaurantId == 2) {
                restaurantImage.setImageResource(R.drawable.chinese_image);
            } else if (restaurantId == 3) {
                restaurantImage.setImageResource(R.drawable.turkish_image);
            } else if (restaurantId == 4) {
                restaurantImage.setImageResource(R.drawable.american_image);
            } else if (restaurantId == 5) {
                restaurantImage.setImageResource(R.drawable.italian_image);
            } else {
                // Default image if the ID doesn't match any specific image
                restaurantImage.setImageResource(R.drawable.icono_restaurante);
            }

            boolean isFromEncargosEscogidosFragment = false;

            if (isFromEncargosEscogidosFragment) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
                restaurantImage.clearColorFilter();
            } else {
                if (pedido.getIsTaken()) {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.lightGrey));
                    restaurantImage.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.lightGrey), PorterDuff.Mode.MULTIPLY);
                } else {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
                    restaurantImage.clearColorFilter();
                }
            }

        }
    }

}
