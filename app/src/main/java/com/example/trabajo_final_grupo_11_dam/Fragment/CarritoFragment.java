package com.example.trabajo_final_grupo_11_dam.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.trabajo_final_grupo_11_dam.CarritoCompra;
import com.example.trabajo_final_grupo_11_dam.CarritoCompraAdapter;
import com.example.trabajo_final_grupo_11_dam.Carta;
import com.example.trabajo_final_grupo_11_dam.CartaPreferenceHelper;
import com.example.trabajo_final_grupo_11_dam.R;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarritoFragment extends Fragment {

    private RecyclerView rvCarrito;
    private CarritoCompraAdapter adapter;
    private CarritoCompra carritoCompra;
    private Button btnConfirmarPedido, btnCancelerPedido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        rvCarrito = view.findViewById(R.id.rv_carrito);
        rvCarrito.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvCarrito.setLayoutManager(layoutManager);

        carritoCompra = CarritoCompra.getInstance();

        List<Carta> savedCart = CartaPreferenceHelper.loadCart(getContext());
        if(savedCart != null){
            carritoCompra.setCartaList(savedCart);
        }

        adapter = new CarritoCompraAdapter(carritoCompra.getCartaList(), getContext());
        rvCarrito.setAdapter(adapter);

        btnConfirmarPedido = view.findViewById(R.id.btn_confirmar_pedido);
        btnCancelerPedido = view.findViewById(R.id.btn_cancelar_pedido);

        btnConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement your order confirmation logic here
                CartaPreferenceHelper.clearCart(getContext());
                carritoCompra.clear();
                adapter.notifyDataSetChanged();
            }
        });

        btnCancelerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the shopping cart and update the RecyclerView
                CartaPreferenceHelper.clearCart(getContext());
                carritoCompra.clear();
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        CartaPreferenceHelper.saveCart(getContext(), carritoCompra.getCartaList());
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Carta> savedCart = CartaPreferenceHelper.loadCart(getContext());
        if(savedCart != null){
            carritoCompra.setCartaList(savedCart);
            adapter.notifyDataSetChanged();
        }
    }
}

