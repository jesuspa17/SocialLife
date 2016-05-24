package com.salesianostriana.sociallife.sociallifeapp.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_listas.Comentario;

import java.util.List;

/**
 * Created by Jesus Pallares on 30/04/2016.
 */
public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolderComentario> {

    private List<Comentario> lista_comentarios;
    private Context context;

    public class ViewHolderComentario extends PlanAdapter.ViewHolder{

        TextView txtComentario,txtFecha,txtUsuario;
        ImageView imgUser;
        View v;

        public ViewHolderComentario(View v) {
            super(v);

            txtUsuario = (TextView) v.findViewById(R.id.textViewNomUserComent);
            txtComentario = (TextView) v.findViewById(R.id.textViewComentarioVal);
            txtFecha = (TextView)v.findViewById(R.id.txtFechaVal);
            imgUser = (ImageView) v.findViewById(R.id.imgViewUserComent);
            this.v = v;

        }
    }

    public ComentarioAdapter(List<Comentario> lista_comentarios){
        this.lista_comentarios = lista_comentarios;
    }

    @Override
    public ViewHolderComentario onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_comentario,parent,false);
        this.context = v.getContext();
        return new ViewHolderComentario(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderComentario holder, int position) {

        Comentario comentario = lista_comentarios.get(position);
        holder.txtUsuario.setText(comentario.getUsuario());
        holder.txtComentario.setText(comentario.getContenido());
        holder.txtFecha.setText(comentario.getFecha());
    }

    @Override
    public int getItemCount() {
        return lista_comentarios.size();
    }
}
