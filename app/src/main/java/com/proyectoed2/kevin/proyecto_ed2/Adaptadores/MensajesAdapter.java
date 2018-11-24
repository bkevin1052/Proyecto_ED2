package com.proyectoed2.kevin.proyecto_ed2.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mylibrary.SDES;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Mensaje;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MensajesAdapter extends RecyclerView.Adapter<MensajesAdapter.ListaMensajesViewHolder> implements View.OnClickListener {


    private Context miContexto;
    private ArrayList<Mensaje> listaMensajes;
    private View.OnClickListener listener;
    int[] P10,P8,IP,EP,P4;
    public String llave;


    public MensajesAdapter(Context miContexto, ArrayList<Mensaje> listaMensajes) {
        this.miContexto = miContexto;
        this.listaMensajes = listaMensajes;
        P10 = new int[10];
        P8=new int[8];
        IP = new int[8];
        EP = new int[8];
        P4 = new int[4];
        lecturaPermutaciones();
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
        String contraseniaCifrada = "";
        char[] caracter = String.valueOf(mensaje).toCharArray();
        SDES sdesDescifrado;

        try {
            for (char c : caracter) {


                sdesDescifrado = new SDES((char) c, llave, P10, P8, IP, EP, P4);
                String[] llaves = sdesDescifrado.obtenerLlaves(llave);
                contraseniaCifrada += sdesDescifrado.desencriptar((int)c, llaves);
            }
        }
        catch(Exception e)
        {
            String error = e.getMessage();
        }
        holder.textViewMensaje.setText(contraseniaCifrada);

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

    public void lecturaPermutaciones() {

        try {
            InputStream in = miContexto.getResources().openRawResource(R.raw.config);
            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            String linea;
            String[] permutaciones = new String[2];
            if (in != null) {
                while ((linea = rd.readLine()) != null) {
                    permutaciones = linea.split("\\|");
                }
                for (int i = 0; i < permutaciones[0].length(); i++) {
                    P10[i] = Integer.parseInt(String.valueOf(permutaciones[0].charAt(i)));
                }
                for (int i = 0; i < permutaciones[1].length(); i++) {
                    P8[i] = Integer.parseInt(String.valueOf(permutaciones[1].charAt(i)));
                }
                for (int i = 0; i < permutaciones[2].length(); i++) {
                    IP[i] = Integer.parseInt(String.valueOf(permutaciones[2].charAt(i)));
                }
                for (int i = 0; i < permutaciones[3].length(); i++) {
                    EP[i] = Integer.parseInt(String.valueOf(permutaciones[3].charAt(i)));
                }
                for (int i = 0; i < permutaciones[4].length(); i++) {
                    P4[i] = Integer.parseInt(String.valueOf(permutaciones[4].charAt(i)));
                }
            }
        }
        catch (Exception e)
        {
            String error = e.getMessage();
        }
    }
}
