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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ListaChatViewHolder> implements View.OnClickListener {


    private Context miContexto;
    private ArrayList<Usuario> listasUsuario;
    private View.OnClickListener listener;


    public ChatAdapter(Context miContexto, ArrayList<Usuario> listasUsuario) {
        this.miContexto = miContexto;
        this.listasUsuario = listasUsuario;
    }

    @Override
    public ListaChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(miContexto);
        View view = inflater.inflate(R.layout.single_sms_small_layout,null);
        ListaChatViewHolder holder = new ListaChatViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaChatViewHolder holder, int position) {

        Usuario seleccion = listasUsuario.get(position);
        holder.textViewNombre.setText(seleccion.getUserName());
        holder.textViewUltimoMensaje.setText(seleccion.getUltimoMensaje().getMensaje());
    }

    @Override
    public int getItemCount() {
        return listasUsuario.size();
    }

    class ListaChatViewHolder extends RecyclerView.ViewHolder{

        TextView textViewNombre,textViewUltimoMensaje;

        public ListaChatViewHolder(View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.nombreContacto);
            textViewUltimoMensaje = itemView.findViewById(R.id.ultimoMensaje);
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
