package com.proyectoed2.kevin.proyecto_ed2.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proyectoed2.kevin.proyecto_ed2.Modelos.Mensaje;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.R;

import java.util.ArrayList;

public class MensajesAdapter extends RecyclerView.Adapter<MensajesAdapter.ListaMensajesViewHolder> implements View.OnClickListener {


    private Context miContexto;
    private ArrayList<Mensaje> listaMensajes;
    private View.OnClickListener listener;


    public MensajesAdapter(Context miContexto, ArrayList<Mensaje> listaMensajes) {
        this.miContexto = miContexto;
        this.listaMensajes = listaMensajes;
    }

    @Override
    public ListaMensajesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(miContexto);
        View view = inflater.inflate(R.layout.mensaje_saliente_yo,null);
        ListaMensajesViewHolder holder = new ListaMensajesViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaMensajesViewHolder holder, int position) {

        Mensaje mensaje = listaMensajes.get(position);
        StringBuilder nuevoMensaje = new StringBuilder();
        nuevoMensaje.append(mensaje.getEmisor());
        nuevoMensaje.append(System.lineSeparator());
        nuevoMensaje.append(mensaje.getMensaje());
        holder.textViewMensaje.setText(nuevoMensaje.toString());

    }

    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }

    class ListaMensajesViewHolder extends RecyclerView.ViewHolder{

        TextView textViewMensaje;

        public ListaMensajesViewHolder(View itemView) {
            super(itemView);
            textViewMensaje = itemView.findViewById(R.id.Mensaje);
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
