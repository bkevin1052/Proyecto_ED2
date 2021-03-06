package com.proyectoed2.kevin.proyecto_ed2.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proyectoed2.kevin.proyecto_ed2.Modelos.Chat;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.R;
import com.proyectoed2.kevin.proyecto_ed2.SplashScreenActivity;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ListaChatViewHolder> implements View.OnClickListener {


    private Context miContexto;
    private ArrayList<Chat> data;
    private View.OnClickListener listener;
    public String usuario;


    public ChatAdapter(Context miContexto, ArrayList<Chat> data) {
        this.miContexto = miContexto;
        this.data = data;
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

        Chat seleccion = data.get(position);
        if(SplashScreenActivity.usuario.compareTo(seleccion.getContacto1())==0)
            holder.textViewNombre.setText(seleccion.getContacto2());
        else
            holder.textViewNombre.setText(seleccion.getContacto1());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ListaChatViewHolder extends RecyclerView.ViewHolder{

        TextView textViewNombre;

        public ListaChatViewHolder(View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.nombreContacto);
        }
    }

    public void addAll(ArrayList<Chat> list) {
        if (list.size() > 0) {
            data.clear();
            data.addAll(list);
            notifyDataSetChanged();
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
