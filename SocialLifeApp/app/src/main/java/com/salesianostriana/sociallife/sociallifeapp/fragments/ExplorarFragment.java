package com.salesianostriana.sociallife.sociallifeapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.ExploraGridAdapter;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_listas.Amigo;

import java.util.ArrayList;
import java.util.List;


public class ExplorarFragment extends Fragment {

    GridView gridView;

    public ExplorarFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_explorar, container, false);

        gridView = (GridView) v.findViewById(R.id.gridViewExplora);

        List<Amigo> lista_amigos = new ArrayList<>();
        lista_amigos.add(new Amigo("Pepe Flauta"));
        lista_amigos.add(new Amigo("El balilla"));
        lista_amigos.add(new Amigo("Pepe Flauta"));
        lista_amigos.add(new Amigo("El balilla"));
        lista_amigos.add(new Amigo("Pepe Flauta"));
        lista_amigos.add(new Amigo("El balilla"));
        lista_amigos.add(new Amigo("Pepe Flauta"));
        lista_amigos.add(new Amigo("El balilla"));
        lista_amigos.add(new Amigo("Pepe Flauta"));
        lista_amigos.add(new Amigo("El balilla"));

        gridView.setAdapter(new ExploraGridAdapter(getActivity(),lista_amigos));



        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
}
