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
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.EstoyApuntadoAdapter;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_estoy_apuntado.EstoyApuntado;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.PlanesApi;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;


public class EstoyApuntadoFragment extends Fragment {

    RecyclerView recyclerView;


    public EstoyApuntadoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_estoy_apuntado, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_estoy_apuntado);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        new EstoyApuntadoTask().execute();
        return v;
    }



    private class EstoyApuntadoTask extends AsyncTask<Void,Void, List<EstoyApuntado>> {

        @Override
        protected List<EstoyApuntado> doInBackground(Void... params) {

            if(params!=null){
                Call<List<EstoyApuntado>> call = Servicio.instanciarServicio(PlanesApi.class, Preferencias.preferencias.getString("token", null)).estoyApuntadoA();
                Response<List<EstoyApuntado>> res = null;
                try {
                    res = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return res.body();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<EstoyApuntado> lista) {
            super.onPostExecute(lista);
            recyclerView.setAdapter(new EstoyApuntadoAdapter(lista));

        }
    }
    
}
