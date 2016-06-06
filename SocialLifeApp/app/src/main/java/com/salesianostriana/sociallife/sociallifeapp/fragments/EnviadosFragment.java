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
import com.salesianostriana.sociallife.sociallifeapp.utiles.Preferencias;
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.DividerItemDecoration;
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.MensajeAdapter;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_mensajes.Mensaje;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.MensajesApi;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;


public class EnviadosFragment extends Fragment {


    private RecyclerView recyclerView;

    public EnviadosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_enviados, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewMensajesEnviados);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        new ObtenerMensajesEnviados().execute();

        return v;
    }

    private class ObtenerMensajesEnviados extends AsyncTask<Void, Void, List<Mensaje>>{

        @Override
        protected List<Mensaje> doInBackground(Void... params) {
            Call<List<Mensaje>> call = Servicio.instanciarServicio(MensajesApi.class, Preferencias.preferencias.getString("token",null)).obtenerEnviados();
            Response<List<Mensaje>> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert res != null;
            return res.body();
        }

        @Override
        protected void onPostExecute(List<Mensaje> mensajes) {
            super.onPostExecute(mensajes);
            recyclerView.setAdapter(new MensajeAdapter(mensajes,1));
        }
    }

}
