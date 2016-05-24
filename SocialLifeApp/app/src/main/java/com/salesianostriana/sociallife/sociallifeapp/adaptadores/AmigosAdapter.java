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
import com.salesianostriana.sociallife.sociallifeapp.activities.PerfilUserActivity;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_seguidos.Seguido;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;

import java.util.List;

/**
 * Created by Jesus Pallares on 25/04/2016.
 */
public class AmigosAdapter extends RecyclerView.Adapter<AmigosAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombreUsuarioSeguido;
        ImageView fotoUsuarioSeguido;
        View v;

        public ViewHolder(View v) {
            super(v);

            txtNombreUsuarioSeguido = (TextView) v.findViewById(R.id.textViewNombreListaSeguidos);
            fotoUsuarioSeguido = (ImageView) v.findViewById(R.id.imageviewFotoListaSeguidos);
            this.v = v;
        }
    }

    private Context context;
    private List<Seguido> lista_seguidos;


    public AmigosAdapter(List<Seguido> lista_seguidos){
        this.lista_seguidos = lista_seguidos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_amigos, parent, false);
        this.context = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Seguido seguidoActual = lista_seguidos.get(position);
        holder.txtNombreUsuarioSeguido.setText(seguidoActual.getUsuarioAmigo().getUsuario().getUsername());
        Utils.cargarImagen(context, seguidoActual.getUsuarioAmigo().getFoto(), holder.fotoUsuarioSeguido);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PerfilUserActivity.class);
                i.putExtra("id_usuario",seguidoActual.getUsuarioAmigo().getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista_seguidos.size();
    }
}
