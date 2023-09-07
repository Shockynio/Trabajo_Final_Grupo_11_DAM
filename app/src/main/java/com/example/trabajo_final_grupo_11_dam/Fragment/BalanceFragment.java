package com.example.trabajo_final_grupo_11_dam.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Locale;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabajo_final_grupo_11_dam.Pedido;
import com.example.trabajo_final_grupo_11_dam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BalanceFragment extends Fragment {

    private TextView weeklyEarningsTextView;
    private TextView monthlyEarningsTextView;
    private TextView paypalAddressTextView;
    private TextView totalEarningsTextView;
    private TextView monthlyEarningsLabel;
    private List<Pedido> pedidosFinalizados;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);

        // Asegúrate de que los IDs coincidan con los que has definido en el archivo XML
        weeklyEarningsTextView = view.findViewById(R.id.weeklyEarnings);
        monthlyEarningsTextView = view.findViewById(R.id.monthlyEarnings);
        paypalAddressTextView = view.findViewById(R.id.paypalAddress);
        totalEarningsTextView = view.findViewById(R.id.totalEarnings);
        monthlyEarningsLabel = view.findViewById(R.id.monthlyEarningsLabel);

        // Encuentra el ImageView en el diseño y configura un escuchador de clics en él.
        ImageView calendarIcon = view.findViewById(R.id.ic_calendar);
        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMonthYearPicker();
            }
        });

        pedidosFinalizados = new ArrayList<>();

        // Llamamos al método para cargar pedidos y calcular las ganancias
        loadData();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        fetchPaypalAddress(email);

        return view;
    }





    private void loadData() {
            String url = "https://trabajo-final-grupo-11.azurewebsites.net/RetrievePedidos";

            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
                                String userEmail = sharedPreferences.getString("email", "Error");

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // Actualiza el formato

                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject pedidoJson = response.getJSONObject(i);

                                    if (pedidoJson.getBoolean("IsFinished") && pedidoJson.getString("RepartidorAsignadoEmail").equals(userEmail)) {

                                        Date CreacionPedido = null;
                                        Date TomaPedido = null;
                                        Date FinalizacionPedido = null;

                                        try {
                                            if (pedidoJson.has("CreacionPedido") && !pedidoJson.isNull("CreacionPedido")) {
                                                CreacionPedido = sdf.parse(pedidoJson.getString("CreacionPedido"));
                                            }
                                            if (pedidoJson.has("TomaPedido") && !pedidoJson.isNull("TomaPedido")) {
                                                TomaPedido = sdf.parse(pedidoJson.getString("TomaPedido"));
                                            }
                                            if (pedidoJson.has("FinalizacionPedido") && !pedidoJson.isNull("FinalizacionPedido")) {
                                                FinalizacionPedido = sdf.parse(pedidoJson.getString("FinalizacionPedido"));
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        Pedido pedido = new Pedido(
                                                pedidoJson.getInt("id_pedido"),
                                                pedidoJson.getString("Direccion_Restaurante"),
                                                pedidoJson.getString("Direccion_Cliente"),
                                                pedidoJson.getInt("Precio_Total"),
                                                pedidoJson.getInt("RestauranteID"),
                                                pedidoJson.getString("Cliente_Username"),
                                                pedidoJson.getBoolean("IsTaken"),
                                                CreacionPedido,
                                                TomaPedido,
                                                FinalizacionPedido
                                        );

                                        pedidosFinalizados.add(pedido);
                                    }
                                }

                                // Después de cargar los pedidos, calculemos las ganancias
                                calculateEarnings();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Manejo de errores
                        }
                    }
            );

            Volley.newRequestQueue(getContext()).add(request);
    }


    private Integer calculateEarnings(Integer selectedMonth, Integer selectedYear) {
        int weeklyEarnings = 0;
        int monthlyEarnings = 0;
        int totalEarnings = 0;
        int selectedMonthEarnings = 0;  // Ganancias para el mes y año seleccionados

        int earningsPerOrder = 5;  // 5 euros por pedido

        Date currentDate = new Date();

        for (Pedido pedido : pedidosFinalizados) {
            Date finalizationDate = pedido.getFinalizacionPedido();
            if (finalizationDate != null) {
                long diffInMillies = Math.abs(currentDate.getTime() - finalizationDate.getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                if (diffInDays <= 7) {
                    weeklyEarnings += earningsPerOrder;
                }

                if (diffInDays <= 30) {
                    monthlyEarnings += earningsPerOrder;
                }

                totalEarnings += earningsPerOrder;

                if (selectedMonth != null && selectedYear != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(finalizationDate);
                    int orderMonth = cal.get(Calendar.MONTH) + 1;  // Los meses en Calendar son de 0-11
                    int orderYear = cal.get(Calendar.YEAR);

                    if (orderMonth == selectedMonth && orderYear == selectedYear) {
                        selectedMonthEarnings += earningsPerOrder;
                    }
                }
            }
        }

        if (selectedMonth != null && selectedYear != null) {
            // Actualizar la UI para mostrar las ganancias del mes y año seleccionados
            updateUIForSelectedMonth(selectedMonthEarnings, selectedMonth, selectedYear);
            return selectedMonthEarnings;
        } else {
            // Actualizar la UI como de costumbre
            updateUI(weeklyEarnings, monthlyEarnings, totalEarnings);
            return null;  // Puedes devolver 0 o cualquier otro valor que indique que no se ha seleccionado un mes/año específico
        }
    }

    private double calculateEarnings() {
        return calculateEarnings(null, null);
    }







    public void fetchPaypalAddress(String email) {
            String url = "https://trabajo-final-grupo-11.azurewebsites.net/getDriverPaypalAddress?email=" + email;

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String paypalAddress = response.getString("paypalAddress");
                                // Actualizar el TextView con la dirección de PayPal
                                paypalAddressTextView.setText(paypalAddress);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Manejar errores
                        }
                    }
            );

            Volley.newRequestQueue(getContext()).add(request);
    }



    private void updateUI(int weeklyEarnings, int monthlyEarnings, int totalEarnings) {
        // Actualiza las ganancias semanales
        weeklyEarningsTextView.setText(String.format(Locale.getDefault(), "%d €", weeklyEarnings));

        // Actualiza las ganancias mensuales
        monthlyEarningsTextView.setText(String.format(Locale.getDefault(), "%d €", monthlyEarnings));

        // Actualiza las ganancias totales
        totalEarningsTextView.setText(String.format(Locale.getDefault(), "%d €", totalEarnings));
    }

    private void updateUIForSelectedMonth(int selectedMonthEarnings, int selectedMonth, int selectedYear) {
        // Actualizar el TextView con las nuevas ganancias
        monthlyEarningsTextView.setText(String.format(Locale.getDefault(), "%d €", selectedMonthEarnings));

        // Actualizar el label para reflejar el mes seleccionado
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale("es", "ES"));  // Para español
        String monthName = sdf.format(new Date(selectedYear - 1900, selectedMonth - 1, 1));  // Ajustar el año y el mes para Date
        monthlyEarningsLabel.setText("Ganancias de " + monthName);
    }






    private void showMonthYearPicker() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Seleccione el mes y el año");
        dialog.setContentView(R.layout.dialog_month_year_picker);

        final NumberPicker monthPicker = dialog.findViewById(R.id.monthPicker);
        final NumberPicker yearPicker = dialog.findViewById(R.id.yearPicker);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        int lastSelectedMonth = sharedPreferences.getInt("lastSelectedMonth", Calendar.getInstance().get(Calendar.MONTH) + 1);
        int lastSelectedYear = sharedPreferences.getInt("lastSelectedYear", Calendar.getInstance().get(Calendar.YEAR));

        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        final int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        // Configurar para mostrar nombres de los meses
        String[] months = new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setDisplayedValues(months);

        // Límites y valor actual para el año
        yearPicker.setMinValue(currentYear - 10);
        yearPicker.setMaxValue(currentYear);

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == currentYear) {
                    monthPicker.setMaxValue(currentMonth);
                } else {
                    monthPicker.setMaxValue(12);
                }
            }
        });

        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (yearPicker.getValue() == currentYear && newVal > currentMonth) {
                    picker.setValue(oldVal);
                }
            }
        });

        // Establece los valores iniciales
        monthPicker.setValue(lastSelectedMonth);
        yearPicker.setValue(lastSelectedYear);

        Button btnOk = dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedMonth = monthPicker.getValue();
                int selectedYear = yearPicker.getValue();

                // Guarda el mes y el año seleccionados
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("lastSelectedMonth", selectedMonth);
                editor.putInt("lastSelectedYear", selectedYear);
                editor.apply();

                calculateEarnings(selectedMonth, selectedYear);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
