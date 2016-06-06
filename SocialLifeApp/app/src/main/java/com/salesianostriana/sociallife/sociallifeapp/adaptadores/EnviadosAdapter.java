package com.salesianostriana.sociallife.sociallifeapp.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_mensajes.Mensaje;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;

import java.util.List;

/**
 * Created by Jesus Pallares on 25/04/2016.
 */
public class EnviadosAdapter extends RecyclerView.Adapter<EnviadosAdapter.ViewHolder> {

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


    public EnviadosAdapter(List<Mensaje> lista_recibidos){
        this.lista_recibidos = lista_recibidos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_recibidos, parent, false);
        this.context = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Mensaje msgActual = lista_recibidos.get(position);
        holder.txtNomUser.setText("De: "+msgActual.getUsuarioEmisor().getUsuario().getUsername());
        Utils.cargarImagen(context, msgActual.getUsuarioEmisor().getFoto(), holder.fotoUser);
        holder.txtContenido.setText(msgActual.getContenido());
        holder.txtFechaRecibido.setText(Utils.formatearFechaComentarios(Utils.FORMATO_FECHA,msgActual.getFecha()));

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista_recibidos.size();
    }
}
