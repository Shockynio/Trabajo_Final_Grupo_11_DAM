package Util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabajo_final_grupo_11_dam.LoginCreacionActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Metodos {

    public static void isEmailTaken(Context context, String email, LoginCreacionActivity.VolleyCallback callback) {
        String url = "https://trabajo-final-grupo-11.azurewebsites.net/isEmailTaken/" + email;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean isTaken = response.getBoolean("isTaken");
                    callback.onSuccess(isTaken);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void isUsernameTaken(Context context, String username, LoginCreacionActivity.VolleyCallback callback) {
        String url = "https://trabajo-final-grupo-11.azurewebsites.net/isUsernameTaken/" + username;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean isTaken = response.getBoolean("isTaken");
                    callback.onSuccess(isTaken);
                    Log.d("isUsernameTaken", "Response: " + response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("isUsernameTaken", "Response: " + error);

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }


    //Comprobación de errores en el formulario a través de métodos

    public static boolean isValidSpanishMobileNumber(String mobileNumber) {
        String mobileRegex = "^[6|7|9]\\d{8}$";
        return mobileNumber.matches(mobileRegex);
    }

    public static boolean isValidName(String name) {
        String nameRegex = "^[A-Za-zÁ-Úá-úñÑ ]{2,}(\\s[A-Za-zÁ-Úá-úñÑ ]{2,}){2}$";
        return name.matches(nameRegex);
    }


    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        return email.matches(emailRegex);
    }
}



