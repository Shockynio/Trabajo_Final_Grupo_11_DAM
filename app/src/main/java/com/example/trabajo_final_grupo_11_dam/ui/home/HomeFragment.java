package com.example.trabajo_final_grupo_11_dam.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trabajo_final_grupo_11_dam.R;
import com.example.trabajo_final_grupo_11_dam.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button btnRestaurante;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       /* HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

         binding = FragmentHomeBinding.inflate(inflater, container, false);
         View root = binding.getRoot();
         final TextView textView = binding.textHome;
         homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
         return root;*/

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Obtener referencia a los elementos del perfil
        btnRestaurante = view.findViewById(R.id.btnRestaurante);

        // Agregar función al botón de edición
        btnRestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Realizar acciones al pulsar el botón de edición del perfil
                // TODO; Añadir funcion para acceder a carta en base de datos
                Toast.makeText(getActivity(), "¡Acceso a carta del restaurante!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}