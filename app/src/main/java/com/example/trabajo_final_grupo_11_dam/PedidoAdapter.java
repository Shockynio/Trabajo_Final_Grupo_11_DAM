package com.example.trabajo_final_grupo_11_dam;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private static final String TAG = "PedidoViewHolder";

    private List<Pedido> pedidos;
    private PedidoClickListener listener;
    private boolean isFromEncargosEscogidosFragment;
    private boolean isFromPedidosFinalizadosFragment;
    private Pedido selectedPedido = null;




    public List<Pedido> getPedidos() {
        return this.pedidos;
    }

    public PedidoAdapter(List<Pedido> pedidos, PedidoClickListener listener, boolean isFromEncargosEscogidosFragment, boolean isFromPedidosFinalizadosFragment) {
        this.pedidos = pedidos;
        this.listener = listener;
        this.isFromEncargosEscogidosFragment = isFromEncargosEscogidosFragment;
        this.isFromPedidosFinalizadosFragment = isFromPedidosFinalizadosFragment;
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
                if (isFromEncargosEscogidosFragment) {
                    if (holder.cardView.isSelected()) {
                        holder.cardView.setSelected(false);
                        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), android.R.color.white)); // Change color back to default
                        selectedPedido = null; // No selected pedido
                    } else {
                        holder.cardView.setSelected(true);
                        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.orangeCLEAR)); // Change color to light orange
                        selectedPedido = pedido; // Update the selected pedido
                    }
                } else {
                    if (pedido.getIsTaken()) {
                        Log.d("PedidoAdapter", "Pedido está tomado.");

                        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
                        String userEmail = sharedPreferences.getString("email", "Error"); // Usa un valor predeterminado

                        String repartidorAsignadoEmail = pedido.getRepartidorAsignadoEmail();

                        if (repartidorAsignadoEmail != null && repartidorAsignadoEmail.equals(userEmail)) {
                            Toast.makeText(view.getContext(), "Has tomado este pedido.", Toast.LENGTH_SHORT).show();
                        } else if (repartidorAsignadoEmail != null) {
                            Toast.makeText(view.getContext(), "Este pedido ya ha sido tomado por otro repartidor.", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("PedidoAdapter", "repartidorAsignadoEmail es null.");
                        }
                    } else if (listener != null) {
                        Log.d("PedidoAdapter", "listener no es null.");
                        listener.onPedidoClick(pedido, position);
                    } else {
                        Log.d("PedidoAdapter", "listener es null o pedido no está tomado.");
                    }

                }
            }
        });
    }


    public Pedido getSelectedPedido() {
        return selectedPedido;
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
        private TextView tvCreacionPedido;
        private TextView tvTomaPedido;
        private TextView tvPedidoEntregado;
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
            tvCreacionPedido = itemView.findViewById(R.id.txtCreacionPedido);
            tvTomaPedido = itemView.findViewById(R.id.txtTomaPedido);
            tvPedidoEntregado = itemView.findViewById(R.id.txtPedidoEntregado);
        }

        public void bindPedido(Pedido pedido) {

            Log.d(TAG, "bindPedido() called");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

            tvIdPedido.setText(String.valueOf(pedido.getid_pedido()));
            tvDireccionRestaurante.setText(pedido.getDireccionRestaurante());
            tvDireccionCliente.setText(pedido.getDireccionCliente());
            tvPrecioTotal.setText(String.valueOf(pedido.getPrecioTotal()));
            tvClienteUsername.setText(pedido.getCliente_Username());

            if (isFromEncargosEscogidosFragment) {
                tvCreacionPedido.setText(sdf.format(pedido.getCreacionPedido()));
                tvCreacionPedido.setVisibility(View.VISIBLE);
                tvTomaPedido.setText(sdf.format(pedido.getTomaPedido()));
                tvTomaPedido.setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.textViewTomaPedido).setVisibility(View.VISIBLE);
                tvPedidoEntregado.setVisibility(View.GONE);
                itemView.findViewById(R.id.textViewPedidoEntregado).setVisibility(View.GONE);

                Log.d(TAG, "isFromEncargosEscogidosFragment is true");
            }
            else if (isFromPedidosFinalizadosFragment) {
                tvCreacionPedido.setText(sdf.format(pedido.getCreacionPedido()));
                tvTomaPedido.setText(sdf.format(pedido.getTomaPedido()));
                tvPedidoEntregado.setText(sdf.format(pedido.getFinalizacionPedido()));

                // Make all fields and their titles visible
                tvCreacionPedido.setVisibility(View.VISIBLE);
                tvTomaPedido.setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.textViewTomaPedido).setVisibility(View.VISIBLE);
                tvPedidoEntregado.setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.textViewPedidoEntregado).setVisibility(View.VISIBLE);

                Log.d(TAG, "isFromPedidosFinalizadosFragment is true");
            }
            else {
                tvCreacionPedido.setText(sdf.format(pedido.getCreacionPedido()));
                tvCreacionPedido.setVisibility(View.VISIBLE);

                // Hide other fields and their titles
                tvTomaPedido.setVisibility(View.GONE);
                itemView.findViewById(R.id.textViewTomaPedido).setVisibility(View.GONE);
                tvPedidoEntregado.setVisibility(View.GONE);
                itemView.findViewById(R.id.textViewPedidoEntregado).setVisibility(View.GONE);

                Log.d(TAG, "Neither isFromEncargosEscogidosFragment nor isFromPedidosFinalizadosFragment is true");
            }



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
                restaurantImage.setImageResource(R.drawable.icono_restaurante);
            }

            if (isFromEncargosEscogidosFragment) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
                restaurantImage.clearColorFilter();
            } else if (isFromPedidosFinalizadosFragment) {
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



