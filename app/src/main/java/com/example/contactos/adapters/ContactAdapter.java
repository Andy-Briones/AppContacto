package com.example.contactos.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactos.ContactosActivity;
import com.example.contactos.FormContactosActivity;
import com.example.contactos.R;
import com.example.contactos.entity.Contactos;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactoViewHolder>
{
    private List<Contactos> data;
    private Activity activity;

    public  ContactAdapter(List<Contactos> data, Activity activity){
        this.data=data;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contacto, parent, false);

        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contactos contacto = data.get(position);

        TextView tvNombre = holder.itemView.findViewById(R.id.tvNomContacto);

        tvNombre.setText(contacto.nombre);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FormContactosActivity.class);
                intent.putExtra("contactoid", contacto.id);
                intent.putExtra("contactoName", contacto.nombre);
                intent.putExtra("contactoTelefono", contacto.telefono);
                intent.putExtra("contactoDireccion", contacto.direccion);
                intent.putExtra("contactoGenero", contacto.genero);

                activity.startActivityForResult(intent, 123);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder
    {
        public ContactoViewHolder(@NonNull View itemview){super(itemview);}
    }
}
