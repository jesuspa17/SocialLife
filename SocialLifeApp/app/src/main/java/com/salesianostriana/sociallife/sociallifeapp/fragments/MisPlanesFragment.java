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
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.PlanAdapter;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.planes.PlanCompleto;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.PlanesApi;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;


public class MisPlanesFragment extends Fragment {


    private RecyclerView recyclerView;

    public MisPlanesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mis_planes, container, false);

        //Inicializo los elementos de la UI
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewMisPlanes);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        new ObtenerMisPlanes().execute(Preferencias.preferencias.getString("token", null));

        return v;
    }

    private class ObtenerMisPlanes extends AsyncTask<String, Void, List<PlanCompleto>> {

        @Override
        protected List<PlanCompleto> doInBackground(String... params) {
            Call<List<PlanCompleto>> call = Servicio.instanciarServicio(PlanesApi.class, params[0]).obtenerMisPlanes();
            Response<List<PlanCompleto>> res = null;

            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert res != null;
            return res.body();
        }

        @Override
        protected void onPostExecute(List<PlanCompleto> planCompletos) {
            super.onPostExecute(planCompletos);
            if(planCompletos!=null){
                recyclerView.setAdapter(new PlanAdapter(planCompletos,1));

            }
        }
    }
}
