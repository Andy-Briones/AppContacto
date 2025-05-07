package com.example.contactos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.contactos.entity.Contactos;
import com.example.contactos.services.ContactoService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormContactosActivity extends AppCompatActivity {
    Button btnsave;
    Button btndelete;
    ContactoService service;
    String Contactid;
    EditText edtnombre;
    EditText edttelefono;
    EditText edtdireccion;
    EditText edtgenero;
    String contactid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_contactos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://67ff052558f18d7209efd0c8.mockapi.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ContactoService.class);

        setUpViews();
        setUpButtonSave();
        setUpButtonDelete();

        Intent intent = getIntent();
        contactid = intent.getStringExtra("contactoid");
        String ContName = intent.getStringExtra("contactoName");
        String ContTele = intent.getStringExtra("contactoTelefono");
        String ContDire = intent.getStringExtra("contactoDireccion");
        String ContGene = intent.getStringExtra("contactoGenero");

        if (ContName != null && ContTele != null && ContGene != null && ContDire != null)
        {
            edtnombre.setText(ContName);
            edtdireccion.setText(ContDire);
            edttelefono.setText(ContTele);
            edtgenero.setText(ContGene);
        }
        if (contactid == null)
        {
            btndelete.setVisibility(View.GONE);
        }
    }
    private void setUpViews()
    {
        btnsave=findViewById(R.id.btnGuardar);
        btndelete=findViewById(R.id.btnDelete);
        edtnombre=findViewById(R.id.etNombre);
        edttelefono=findViewById(R.id.etTelefono);
        edtdireccion=findViewById(R.id.etDireccion);
        edtgenero=findViewById(R.id.etGenero);
    }
    private void setUpButtonSave()
    {
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish(); //termina la actividad actual
                if (contactid == null)
                {
                    save();
                }
                else
                {
                    update();
                }
            }
        });
    }
    private void setUpButtonDelete()
    {
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contactid != null)
                {
                    delete();
                }
            }
        });
    }
    private void save() {
        Contactos contacto = new Contactos();
        contacto.nombre = edtnombre.getText().toString();
        contacto.telefono = edttelefono.getText().toString();
        contacto.direccion = edtdireccion.getText().toString();
        contacto.genero = edtgenero.getText().toString();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference();
        DatabaseReference contactRef = myref.child("Contactos").push();
        contacto.id = contactRef.getKey();
        contactRef.setValue(contacto)
                .addOnSuccessListener(unused -> {
                    finish();
                    Toast.makeText(getApplicationContext(), "contacto creado", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(f -> {
                    finish();
                    Toast.makeText(getApplicationContext(), "Error al crear contacto", Toast.LENGTH_LONG).show();
                });
    }
    private void update() {
        Contactos contact = new Contactos();
        contact.id = contactid;
        contact.nombre = edtnombre.getText().toString();
        contact.telefono = edttelefono.getText().toString();
        contact.direccion = edtdireccion.getText().toString();
        contact.genero = edtgenero.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Contactos");

        myRef.child(contactid).setValue(contact)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(getApplicationContext(), "contacto creado", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(f -> {
                    Toast.makeText(getApplicationContext(), "Error al crear contacto", Toast.LENGTH_LONG).show();
                });
    }
    private void delete() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Contactos");

        myRef.child(contactid).removeValue()
                .addOnSuccessListener(s -> {
                    finish();
                    Toast.makeText(getApplicationContext(), "Contacto eliminado", Toast.LENGTH_LONG).show();
                });
    }
}