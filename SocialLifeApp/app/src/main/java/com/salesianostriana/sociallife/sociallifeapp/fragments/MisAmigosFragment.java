package com.salesianostriana.sociallife.sociallifeapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.activities.Preferencias;
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.AmigosAdapter;
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.DividerItemDecoration;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_seguidos.Seguido;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.SeguidosApi;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;


public class MisAmigosFragment extends Fragment {


    private RecyclerView recyclerView;
    private AmigosAdapter amigosAdapter;

    public MisAmigosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mis_amigos,container,false);

        //Inicializo los elementos de la UI
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewMisAmigos);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        new ObtenerListaSeguidos().execute();
        return v;
    }

    private class ObtenerListaSeguidos extends AsyncTask<Void,Void,List<Seguido>>{


        @Override
        protected List<Seguido> doInBackground(Void... params) {
            Call<List<Seguido>> call = Servicio.instanciarServicio(SeguidosApi.class, Preferencias.preferencias.getString("token",null)).obtenerMisUsuariosSeguidos();
            Response<List<Seguido>> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.body();
        }

        @Override
        protected void onPostExecute(List<Seguido> seguidos) {
            super.onPostExecute(seguidos);
            recyclerView.setAdapter(new AmigosAdapter(seguidos));
        }
    }


}
