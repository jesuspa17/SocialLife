package com.salesianostriana.sociallife.sociallifeapp.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_categoria.Categoria;

import java.util.List;

/**
 * Created by Jesus Pallares on 27/05/2016.
 */
public class CategoriasAdapter extends ArrayAdapter<Categoria> {

    Context context;
    List<Categoria> lista_categoria;

    public CategoriasAdapter(Context context,int textViewResourceId,List<Categoria> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.lista_categoria = objects;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.item_categoria, parent, false);

        Categoria catActual = lista_categoria.get(position);

        TextView nom = (TextView) v.findViewById(R.id.textViewItemNombreCategoria);
        nom.setText(catActual.getNombreCategoria());
        return v;
    }
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}