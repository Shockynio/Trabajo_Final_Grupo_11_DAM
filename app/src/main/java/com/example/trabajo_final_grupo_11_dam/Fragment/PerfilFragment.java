package com.example.trabajo_final_grupo_11_dam.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trabajo_final_grupo_11_dam.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    private TextView tvPDCalle;
    private TextView tvPDCalleInfo; //"Vilafranca del Penedès, \nBarcelona,\nEspaña \n08720"
    private TextView tvPEEmail2;
    private TextView tvPNNomCompleto;
    private TextView tvPTMobil2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);


        tvPDCalle = root.findViewById(R.id.tv_pd_calle);
        tvPDCalleInfo = root.findViewById(R.id.tv_pd_calle_info);
        tvPEEmail2 = root.findViewById(R.id.tv_pe_email2);
        tvPNNomCompleto = root.findViewById(R.id.tv_pn_nom_completo);
        tvPTMobil2 = root.findViewById(R.id.tv_pt_mobil2);

        //TODO: Poner datos de BD en los TextView dependiondo del usuario(Cliente, Empleado)
        // o incluso Restaurante??
        // Se necesita más información??



        return root;
    }
}