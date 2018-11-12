package com.proyectoed2.kevin.proyecto_ed2.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.R;

import java.util.ArrayList;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ListaContactosViewHolder> implements View.OnClickListener {


    private Context miContexto;
    private ArrayList<Usuario> listaContactos;
    private View.OnClickListener listener;


    public ContactosAdapter(Context miContexto, ArrayList<Usuario> listaContactos) {
        this.miContexto = miContexto;
        this.listaContactos = listaContactos;
    }

    @Override
    public ListaContactosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(miContexto);
        View view = inflater.inflate(R.layout.single_sms_small_layout_2,null);
        ListaContactosViewHolder holder = new ListaContactosViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaContactosViewHolder holder, int position) {

        Usuario seleccion = listaContactos.get(position);
        holder.textViewNombre.setText(seleccion.getUserName());
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    class ListaContactosViewHolder extends RecyclerView.ViewHolder{

        TextView textViewNombre;

        public ListaContactosViewHolder(View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.nombreContacto);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

        if(listener!= null){
            listener.onClick(view);
        }
    }
}
