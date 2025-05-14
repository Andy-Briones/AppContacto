package com.example.contactos;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.contactos.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private EditText edtLati;
    private EditText edtLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        edtLati= findViewById(R.id.edtLatitud);
        edtLong= findViewById(R.id.edtLongitud);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.e("MAPS", "Latitud recibida: " + getIntent().getStringExtra("latitud"));
        Log.e("MAPS", "Longitud recibida: " + getIntent().getStringExtra("longitud"));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String latitud = getIntent().getStringExtra("latitud");
        String longitud = getIntent().getStringExtra("longitud");

        if (latitud.isEmpty() || longitud.isEmpty())
        {
            Toast.makeText(this, "No a registrado latitud ni longitud", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            double Lat = Double.parseDouble(latitud);
            double Long = Double.parseDouble(longitud);
            LatLng ubicacion = new LatLng(Lat,Long);

            mMap.clear();
            // Add a marker in Sydney and move the camera
            mMap.addMarker(new MarkerOptions().position(ubicacion).title("Ubicacion"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
        }catch (NumberFormatException e){
            Toast.makeText(this, "coordenadas invalidas", Toast.LENGTH_SHORT).show();
        }
    }
}