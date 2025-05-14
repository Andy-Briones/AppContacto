package com.example.contactos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PuntosActivity extends AppCompatActivity {
    Button btnregresa;
    EditText edtlat;
    EditText edtlong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_puntos);

        edtlat = findViewById(R.id.edtLatitud);
        edtlong = findViewById(R.id.edtLongitud);

        btnregresa = findViewById(R.id.btnAtras);
        btnregresa.setOnClickListener(v -> {
            String lat = edtlat.getText().toString();
            String lng = edtlong.getText().toString();

            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("latitud",lat);
            intent.putExtra("longitud",lng);
            startActivityForResult(intent, 100);
        });
    }
}