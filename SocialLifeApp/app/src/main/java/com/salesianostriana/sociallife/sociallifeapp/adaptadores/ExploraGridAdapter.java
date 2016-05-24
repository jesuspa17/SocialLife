package com.salesianostriana.sociallife.sociallifeapp.adaptadores;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_listas.Amigo;

import java.util.List;

/**
 * Created by Jesus Pallares on 03/05/2016.
 */
public class ExploraGridAdapter extends BaseAdapter {
    Context context;
    private List<Amigo> lista_amigos;

    public ExploraGridAdapter(Context context, List<Amigo> lista_amigos){
        this.context = context;
        this.lista_amigos = lista_amigos;
    }
    @Override
    public int getCount() {
        return lista_amigos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.grid_item_explora,null);
        TextView nombre = (TextView) v.findViewById(R.id.textViewNomExplorar);

        Amigo amigo = lista_amigos.get(position);
        nombre.setText(amigo.getNombre());
        return v;
    }
}
