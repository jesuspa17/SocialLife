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
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_estoy_apuntado.EstoyApuntadoList;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.UsuarioCompleto;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;

/**
 * Created by Jesus Pallares on 22/05/2016.
 */
public class AsistentesAdapter extends RecyclerView.Adapter<AsistentesAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombreAsistente;
        ImageView foto;
        View v;

        public ViewHolder(View v) {
            super(v);

            txtNombreAsistente = (TextView) v.findViewById(R.id.textViewNombreAsistentes);
            foto = (ImageView) v.findViewById(R.id.imageViewFotoAsistentes);
            this.v = v;
        }
    }

    private Context context;
    private EstoyApuntadoList lista;


    public AsistentesAdapter(EstoyApuntadoList lista){
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_asistentes,parent,false);
        this.context = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final UsuarioCompleto usuarioCompleto = lista.getResults().get(position).getUsuario();
        holder.txtNombreAsistente.setText(usuarioCompleto.getUsuario().getUsername());
        Utils.cargarImagen(context, usuarioCompleto.getFoto(), holder.foto);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PerfilUserActivity.class);
                i.putExtra("id_usuario",usuarioCompleto.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.getResults().size();
    }
}