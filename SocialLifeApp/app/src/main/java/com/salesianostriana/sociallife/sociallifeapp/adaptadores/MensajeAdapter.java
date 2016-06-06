package com.salesianostriana.sociallife.sociallifeapp.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.activities.MsgRelacionadosActivity;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_mensajes.Mensaje;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;

import java.util.List;

/**
 * Created by Jesus Pallares on 25/04/2016.
 */
public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNomUser,txtContenido,txtFechaRecibido;
        ImageView fotoUser;
        View v;

        public ViewHolder(View v) {
            super(v);

            txtNomUser = (TextView) v.findViewById(R.id.textViewUserRecibido);
            txtContenido = (TextView) v.findViewById(R.id.textViewContenidoRecibido);
            txtFechaRecibido = (TextView) v.findViewById(R.id.textViewFechaRecibidos);
            fotoUser = (ImageView) v.findViewById(R.id.imageViewUserRecibido);
            this.v = v;
        }
    }

    private Context context;
    private List<Mensaje> lista_recibidos;
    private int tipo;


    public MensajeAdapter(List<Mensaje> lista_recibidos, int tipo){
        this.lista_recibidos = lista_recibidos;
        this.tipo = tipo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(tipo == 1){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_enviados, parent, false);
        }else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_recibidos, parent, false);
        }
        this.context = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Mensaje msgActual = lista_recibidos.get(position);
        if(tipo == 1){
            holder.txtNomUser.setText(context.getResources().getString(R.string.para)+" "+msgActual.getUsuarioReceptor().getUsuario().getUsername());
            Utils.cargarImagen(context, msgActual.getUsuarioReceptor().getFoto(), holder.fotoUser);

            holder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, MsgRelacionadosActivity.class);
                    i.putExtra("id_usuario", msgActual.getUsuarioReceptor().getId());
                    i.putExtra("nom_user",msgActual.getUsuarioReceptor().getUsuario().getUsername());
                    i.putExtra("foto_user",msgActual.getUsuarioReceptor().getFoto());
                    context.startActivity(i);
                }
            });

        }else{
            holder.txtNomUser.setText(context.getResources().getString(R.string.de_msg)+" "+msgActual.getUsuarioEmisor().getUsuario().getUsername());
            Utils.cargarImagen(context, msgActual.getUsuarioEmisor().getFoto(), holder.fotoUser);

            holder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, MsgRelacionadosActivity.class);
                    i.putExtra("id_usuario", msgActual.getUsuarioEmisor().getId());
                    i.putExtra("nom_user",msgActual.getUsuarioEmisor().getUsuario().getUsername());
                    i.putExtra("foto_user",msgActual.getUsuarioEmisor().getFoto());
                    context.startActivity(i);
                }
            });
        }

        holder.txtContenido.setText(msgActual.getContenido());
        holder.txtFechaRecibido.setText(Utils.formatearFechaComentarios(Utils.FORMATO_FECHA, msgActual.getFecha()));


    }

    @Override
    public int getItemCount() {
        return lista_recibidos.size();
    }
}
