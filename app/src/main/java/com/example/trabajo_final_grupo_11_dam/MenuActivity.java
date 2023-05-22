package com.example.trabajo_final_grupo_11_dam;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajo_final_grupo_11_dam.databinding.ActivityMenuBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;

    private NavigationView navigationView;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.carritoFragment, R.id.perfilFragment, R.id.encargosDisponiblesFragment, R.id.encargosEscogidosragment, R.id.cartaFragment,
                R.id.anyadirCartaFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Fetch user data and update the header view
        //fetchUserData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_menu, menu);

        // Get the current fragment ID
        int currentFragmentId = NavHostFragment.findNavController(getSupportFragmentManager().getPrimaryNavigationFragment()).getCurrentDestination().getId();

        // Check if the current fragment is PerfilFragment
        if (currentFragmentId == R.id.perfilFragment) {
            // Hide the "back_menu" item
            MenuItem backMenuItem = menu.findItem(R.id.action_back);
            backMenuItem.setVisible(false);
        } else {
            // Show the "back_menu" item for other fragments
            MenuItem backMenuItem = menu.findItem(R.id.action_back);
            backMenuItem.setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);

         // TODO: POR ALGUNA RAZON ESTO NO FUNCIONA, HAY QUE ELIMINAR EL BOTÓN PERO NO LO HACE, OS TOCA INVESTIGAR POR QUÉ Y ESCONDER EL BOTON
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                // Clear shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Go back to the previous activity
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


   /* private void fetchUserData() {
        String url = "https://trabajo-final-grupo-11.azurewebsites.net/selectuserdata";

        // Create a StringRequest with the GET method
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse the response and extract the user's information
                        try {
                            Log.d("MenuActivity", "Response: " + response);

                            JSONObject jsonResponse = new JSONObject(response);
                            String fullName = jsonResponse.getString("fullName");
                            String email = jsonResponse.getString("email");

                            Log.d("MenuActivity", "Full Name: " + fullName);
                            Log.d("MenuActivity", "Email: " + email);

                            // Update the header view with the retrieved user's information
                            updateHeaderView(fullName, email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        Log.e("MenuActivity", "Error fetching user data: " + error.getMessage());
                    }
                });

        // Add the request to the request queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
*/

    private void updateHeaderView(String fullName, String email) {
        headerView = navigationView.getHeaderView(0);
        TextView fullNameTextView = headerView.findViewById(R.id.tv_full_name);
        TextView emailTextView = headerView.findViewById(R.id.tv_email);

        fullNameTextView.setText(fullName);
        emailTextView.setText(email);
    }
}
