package com.example.trabajo_final_grupo_11_dam.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabajo_final_grupo_11_dam.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EncargosEscogidosragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EncargosEscogidosragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EncargosEscogidosragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EncargosEscogidosragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EncargosEscogidosragment newInstance(String param1, String param2) {
        EncargosEscogidosragment fragment = new EncargosEscogidosragment();
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

    private TextView tvEER2NomRestaurante;
    private ImageView ivRestaurant;

    private TextView tvEEPPedidoInfo;
    private TextView tvEEPPedidoInfo2;
    private TextView tvEEPPedidoInfo3;
    private TextView tvEEPPedidoInfo4;
    private TextView tvEEPPedidoInfo5;

    private TextView tvEEDCalle;
    private TextView tvEEDCalleInfo; //"Vilafranca del Penedès, \nBarcelona,\nEspaña \n08720"
    private TextView tvEEDCalle2;
    private TextView tvEEDCalleInfo2;

    private Button btnFEECompleto;
    private Button btnFEECancelar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_encargos_escogidosragment, container, false);

        //TODO: SELECT TO NOMBRE RESTAURANTE donde se hace el pedido, y su imagen
        tvEER2NomRestaurante = root.findViewById(R.id.tv_eer2_nom_restaurante);
        ivRestaurant = root.findViewById(R.id.iv_restaurant);
        //TODO: SELECT TO PEDIDOS(DIVIDIDOS ENTRE 1.ENTRANTES 2.SEGUNDOS 3.POSTRES 4.BEBEDIAS 5.TAPAS)
        tvEEPPedidoInfo  = root.findViewById(R.id.tv_eep_pedido_info);
        tvEEPPedidoInfo2 = root.findViewById(R.id.tv_eep_pedido_info2);
        tvEEPPedidoInfo3 = root.findViewById(R.id.tv_eep_pedido_info3);
        tvEEPPedidoInfo4 = root.findViewById(R.id.tv_eep_pedido_info4);
        tvEEPPedidoInfo5 = root.findViewById(R.id.tv_eep_pedido_info5);
        //TODO: SELECT TO DIRECCION 1.RESTAURANTE 2.CLIENTE
        tvEEDCalle = root.findViewById(R.id.tv_eed_calle);
        tvEEDCalleInfo = root.findViewById(R.id.tv_eed_calle_info);
        tvEEDCalle2 = root.findViewById(R.id.tv_eed_calle2);
        tvEEDCalleInfo2 = root.findViewById(R.id.tv_eed_calle_info2);

        btnFEECompleto = root.findViewById(R.id.btn_fee_completo);
        btnFEECancelar = root.findViewById(R.id.btn_fee_cancelar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Pedido a ENTREGAR");

        //TODO: Al apretar PEDIDO cambia estado a COMPLETO
        // (Repartidor a completado la entrega)
        btnFEECompleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //TODO: Al apretar PEDIDO cambia estado a DISPONIBLE
        // (Repartidor a cancelado continuar con la enctrega)
        btnFEECancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return root;
    }
}