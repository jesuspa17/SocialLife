package com.salesianostriana.sociallife.sociallifeapp.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.activities.EditarPerfilActivity;
import com.salesianostriana.sociallife.sociallifeapp.activities.LoginActivity;
import com.salesianostriana.sociallife.sociallifeapp.activities.MainActivity;
import com.salesianostriana.sociallife.sociallifeapp.activities.Preferencias;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.ModificarUbicacion;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.PerfilUsuario;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.UsuarioCompleto;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.UsuariosApi;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;


public class AjustesFragment extends Fragment {


    TextView txtCerrarSesion, txtEditarPerfil,txtCiudadActual;
    LinearLayout linearCambiarCiudad;

    int id_obtenido;


    public AjustesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ajustes, container, false);

        txtCerrarSesion = (TextView)v.findViewById(R.id.textViewCerrarSesion);
        txtEditarPerfil = (TextView) v.findViewById(R.id.textViewEditarPerfil);
        txtCiudadActual = (TextView) v.findViewById(R.id.textViewCiudadActual);
        linearCambiarCiudad = (LinearLayout) v.findViewById(R.id.linearLayoutCambiarCiudad);

        //Para obtener el id del usuario del que voy a modificar la ubicación.
        new ObtenerMisDatos().execute();

        txtCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencias.editor.putString("ciudad","");
                Preferencias.editor.clear();
                Preferencias.editor.commit();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        txtEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditarPerfilActivity.class));
            }
        });

        linearCambiarCiudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.abrirBuscadorEnFragment(Utils.REQUEST_GENERIC_CODE, AjustesFragment.this, Utils.filtro_regiones);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utils.REQUEST_GENERIC_CODE) {
            Place place = PlaceAutocomplete.getPlace(getActivity(), data);
            if(place!=null){
                String ub_actual = String.valueOf(place.getName());
                txtCiudadActual.setText(place.getName());
                ModificarUbicacion mod = new ModificarUbicacion();
                mod.setUbicacionActual(ub_actual);
                new CambiarCiudadActual().execute(mod);
                Preferencias.editor.putString("ciudad", ub_actual);
                Preferencias.editor.apply();
                MainActivity.txtCiudad.setText(R.string.estas_viendo_planes);
                MainActivity.txtCiudad.setText(MainActivity.txtCiudad.getText() + " " + ub_actual);
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
        protected void onPostExecute(List<PerfilUsuario> perfilUsuarios) {
            super.onPostExecute(perfilUsuarios);
            if(perfilUsuarios.size()!=0){
                txtCiudadActual.setText(perfilUsuarios.get(0).getUbicacion_actual());
                id_obtenido = perfilUsuarios.get(0).getId();
            }
        }
    }

    private class CambiarCiudadActual extends AsyncTask<ModificarUbicacion, Void, UsuarioCompleto>{

        @Override
        protected UsuarioCompleto doInBackground(ModificarUbicacion... params) {
            Call<UsuarioCompleto> call = Servicio.instanciarServicio(UsuariosApi.class,Preferencias.preferencias.getString("token",null)).modificarCiudadActual(id_obtenido,params[0]);
            Response<UsuarioCompleto> res = null;

            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.body();
        }

        @Override
        protected void onPostExecute(UsuarioCompleto perfilUsuario) {
            super.onPostExecute(perfilUsuario);
            if(perfilUsuario!=null){
                txtCiudadActual.setText(perfilUsuario.getUbicacion_actual());
            }
        }
    }
}
