package com.example.contactos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactos.adapters.ContactAdapter;
import com.example.contactos.entity.Contactos;
import com.example.contactos.services.ContactoService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContactosActivity extends AppCompatActivity {
    RecyclerView rvContact;
    SearchView searchContacto;
    String busqueda = "";
    boolean isLoading = false;
    boolean isLastPage = false;
    int currentPage=1;
    List<Contactos> ContactData = new ArrayList<>();
    ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contactos);

        FloatingActionButton button = findViewById(R.id.fabGotoContactForm);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormContactosActivity.class);
            startActivityForResult(intent, 100);
        });

        rvContact = findViewById(R.id.rvContactos);
        rvContact.setLayoutManager(new LinearLayoutManager(this));


        loadMoreColors(busqueda);
        setUpReciclerView();
        setUpSearchView();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == 123) {

            String contactoJSON = data.getStringExtra("contactoJSON");
            Contactos updatedContacto = new Gson().fromJson(contactoJSON, Contactos.class);

            Contactos contacto = ContactData.stream().filter(c -> c.id == updatedContacto.id)
                    .findFirst().orElse(null);
            int position = ContactData.indexOf(contacto);

            if (contacto != null) {
                contacto.nombre = updatedContacto.nombre;
                contacto.telefono = updatedContacto.telefono;
                contacto.direccion = updatedContacto.direccion;
                contacto.genero = updatedContacto.genero;
                adapter.notifyItemChanged(position); //esto es lo relevante
            }
        }
        if (requestCode == 100) {
            String contactoJSON = data.getStringExtra("contactoJSON");
            Contactos createdColor = new Gson().fromJson(contactoJSON, Contactos.class);

            ContactData.add(createdColor);
            adapter.notifyItemInserted(ContactData.size() - 1);
        }
    }
    private void loadMoreColors(String query) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Contactos");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ContactData.clear();
                for (DataSnapshot colorSnapshot : snapshot.getChildren()) {
                    Contactos contacto = colorSnapshot.getValue(Contactos.class);
                    if (contacto != null) {
                        ContactData.add(contacto);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        isLoading = true;
    }
    private void setUpReciclerView() {
        adapter = new ContactAdapter(ContactData, ContactosActivity.this);
        rvContact.setAdapter(adapter);

//        rvContact.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                if (layoutManager == null) return;
//
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//
//                if (!isLoading && !isLastPage)
//                {
//                    if ((visibleItemCount+firstVisibleItemPosition) >= totalItemCount)
//                    {
//                        currentPage++;
//                        loadMoreColors(busqueda);
//                    }
//                }
//            }
//        });
    }
    private void setUpSearchView() {
        searchContacto = findViewById(R.id.searchContacto);
        searchContacto.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("MAIN_APP", query);

                if(!Objects.equals(busqueda, query)) {
                    ContactData.clear();
                    adapter.notifyDataSetChanged();
                    busqueda = query;
                    currentPage = 1;
                    loadMoreColors(busqueda);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("MAIN_APP", newText);
                if (newText.isEmpty()) {
                    ContactData.clear();
                    adapter.notifyDataSetChanged();
                    busqueda = "";
                    currentPage = 1;
                    loadMoreColors(busqueda);
                }
                return false;
            }
        });
    }
}