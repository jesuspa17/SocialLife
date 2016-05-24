package com.salesianostriana.sociallife.sociallifeapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.AsistentesAdapter;
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.DividerItemDecoration;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_estoy_apuntado.EstoyApuntadoList;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.PlanesApi;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

public class ListadoAsitentesActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_asitentes);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerListadoAsistentes);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            int id_plan = extras.getInt("id_plan");
            new ObtenerApuntados().execute(id_plan);
        }


    }


    private class ObtenerApuntados extends AsyncTask<Integer, Void, EstoyApuntadoList> {

        @Override
        protected EstoyApuntadoList doInBackground(Integer... params) {
            Call<EstoyApuntadoList> call = Servicio.instanciarServicio(PlanesApi.class, Preferencias.preferencias.getString("token", null)).obtenerUsuariosApuntados(params[0]);
            Response<EstoyApuntadoList> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.body();
        }

        @Override
        protected void onPostExecute(EstoyApuntadoList estoyApuntadoList) {
            super.onPostExecute(estoyApuntadoList);

            recyclerView.setAdapter(new AsistentesAdapter(estoyApuntadoList));


        }
    }
}
