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
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.PerfilUsuario;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.PlanesApi;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.UsuariosApi;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;


public class HoyFragment extends Fragment {


    private RecyclerView recyclerView;

    public HoyFragment() {
    }

    String ub_actual;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hoy,container,false);

        //Inicializo los elementos de la UI
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewPlanesHoy);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        String ubicacion = Preferencias.preferencias.getString("ciudad",null);

        if(ubicacion==null){
            new ObtenerMisDatos().execute();
        }else{
            new ObtenerPlanesDeHoy().execute(Preferencias.preferencias.getString("token", null), ubicacion, Utils.fechaHoyDjango());
        }
        return v;
    }


    private class ObtenerPlanesDeHoy extends AsyncTask<String, Void, List<PlanCompleto>>{

        @Override
        protected List<PlanCompleto> doInBackground(String... params) {
            Call<List<PlanCompleto>> call = Servicio.instanciarServicio(PlanesApi.class,params[0]).obtenerPlanesParaHoy();
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
                recyclerView.setAdapter(new PlanAdapter(planCompletos,0));
            }
        }
    }

    private class ObtenerMisDatos extends AsyncTask<Void, Void, List<PerfilUsuario>>{
        @Override
        protected List<PerfilUsuario> doInBackground(Void... params) {
            Call<List<PerfilUsuario>> call = Servicio.instanciarServicio(UsuariosApi.class,Preferencias.preferencias.getString("token",null)).obtenerDatosSimples();
            Response<List<PerfilUsuario>> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.body();
        }

        @Override
        protected void onPostExecute(List<PerfilUsuario> lista) {
            super.onPostExecute(lista);
            if(lista.size()!=0){
                ub_actual = lista.get(0).getUbicacion_actual();
                new ObtenerPlanesDeHoy().execute(Preferencias.preferencias.getString("token", null), ub_actual, Utils.fechaHoyDjango());
            }
        }
    }


}
