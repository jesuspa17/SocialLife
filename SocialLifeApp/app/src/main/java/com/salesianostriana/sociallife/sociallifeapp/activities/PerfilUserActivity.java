package com.salesianostriana.sociallife.sociallifeapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.EstaApuntadoAdapter;
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.PlanAdapter;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.planes.PlanCompleto;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_estoy_apuntado.EstoyApuntadoList;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_seguidos.SeguidosSimpleList;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.UsuarioCompleto;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.PlanesApi;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.SeguidosApi;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.UsuariosApi;
import com.salesianostriana.sociallife.sociallifeapp.utiles.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit.Call;
import retrofit.Response;

public class PerfilUserActivity extends AppCompatActivity {

    Toolbar toolbar;

    ImageView imgUser;
    TextView txtNombre,txtCiudad,txtBiografia;
    Button btnGenerico;
    private RecyclerView recyclerPlanesApuntados,recyclerPlanesCreados;

    String token;
    Integer id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        imgUser = (ImageView) findViewById(R.id.imageViewPerfilUser);
        txtNombre = (TextView)findViewById(R.id.textViewNombrePerfil);
        txtBiografia = (TextView) findViewById(R.id.textViewBiografiaPerfil);
        txtCiudad = (TextView)findViewById(R.id.textViewLocalizacionPerfil);
        btnGenerico = (Button) findViewById(R.id.btnGenericoPerfil);
        recyclerPlanesApuntados = (RecyclerView) findViewById(R.id.recycerEstaApuntado);
        recyclerPlanesCreados = (RecyclerView) findViewById(R.id.recyclerPlanesCreadosPerfil);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerPlanesApuntados.setHasFixedSize(true);
        recyclerPlanesApuntados.setLayoutManager(layoutManager);

        RecyclerView.LayoutManager layoutManager_creados = new LinearLayoutManager(this);
        recyclerPlanesCreados.setHasFixedSize(true);
        recyclerPlanesCreados.setLayoutManager(layoutManager_creados);

        token = Preferencias.preferencias.getString("token",null);
        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            id_user = extras.getInt("id_usuario");
            new ComprobarSiSoyYo().execute();
            new ObtenerPlanesAlosQueEstaApuntado().execute(id_user);
            new ObtenerPlanesVigentesDeUnUsuario().execute(id_user);
        }else{
            new ObtenerMisDatosTask().execute(token);
            btnGenerico.setText("EDITAR PERFIL");
            btnGenerico.setBackground(getDrawable(R.drawable.redondear));
            btnGenerico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(PerfilUserActivity.this, EditarPerfilActivity.class));
                }
            });
        }
    }

    private class ComprobarSiSoyYo extends AsyncTask<String, Void, List<UsuarioCompleto>> {
        @Override
        protected List<UsuarioCompleto> doInBackground(String... params){
            Call<List<UsuarioCompleto>> call = Servicio.instanciarServicio(UsuariosApi.class, token).obtenerTodosMisDatos();
            Response<List<UsuarioCompleto>> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert res != null;
            return res.body();
        }

        @Override
        protected void onPostExecute(List<UsuarioCompleto> usuarioCompleto) {
            super.onPostExecute(usuarioCompleto);
            if(usuarioCompleto!=null) {


                new ObtenerDatosUsuario().execute(id_user);

                if(Objects.equals(usuarioCompleto.get(0).getId(), id_user)){
                    btnGenerico.setText("EDITAR PERFIL");
                    btnGenerico.setBackground(getDrawable(R.drawable.redondear));
                    btnGenerico.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(PerfilUserActivity.this, EditarPerfilActivity.class));
                        }
                    });

                }else{
                    new ObtenerRelacionEntre2Usuarios().execute(usuarioCompleto.get(0).getId(), id_user);
                }
            }else{
                Log.e("ERROR_PERFIL", "ERROR_PERFIL");
            }
        }
    }


    private class ObtenerMisDatosTask extends AsyncTask<String, Void, List<UsuarioCompleto>> {

        @Override
        protected List<UsuarioCompleto> doInBackground(String... params){
            Call<List<UsuarioCompleto>> call = Servicio.instanciarServicio(UsuariosApi.class, token).obtenerTodosMisDatos();
            Response<List<UsuarioCompleto>> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert res != null;
            return res.body();
        }

        @Override
        protected void onPostExecute(List<UsuarioCompleto> usuarioCompleto) {
            super.onPostExecute(usuarioCompleto);
            if(usuarioCompleto!=null){

                //Ejecuto asyntask de las consultas
                new ObtenerPlanesAlosQueEstaApuntado().execute(usuarioCompleto.get(0).getId());
                new ObtenerPlanesVigentesDeUnUsuario().execute(usuarioCompleto.get(0).getId());

                toolbar.setTitle(usuarioCompleto.get(0).getUsuario().getUsername());
                Picasso.with(PerfilUserActivity.this).load(usuarioCompleto.get(0).getFoto()).resize(120,120).error(R.drawable.logo)
                        .transform(new RoundedTransformation(70, 0))
                        .placeholder(R.drawable.logo).into(imgUser);
                txtNombre.setText(usuarioCompleto.get(0).getUsuario().getFirstName() + " " + usuarioCompleto.get(0).getUsuario().getLastName());
                txtCiudad.setText(usuarioCompleto.get(0).getPoblacion() + ", " + usuarioCompleto.get(0).getCiudad());
                txtBiografia.setText(usuarioCompleto.get(0).getBiografia());

            }else{
                Log.e("ERROR_PERFIL", "ERROR_PERFIL");
            }
        }
    }


    private class ObtenerPlanesAlosQueEstaApuntado extends AsyncTask<Integer,Void,EstoyApuntadoList>{

        @Override
        protected EstoyApuntadoList doInBackground(Integer... params) {
            Call<EstoyApuntadoList> call = Servicio.instanciarServicio(PlanesApi.class,Preferencias.preferencias.getString("token",null)).estaApuntadoA(params[0]);
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
            recyclerPlanesApuntados.setAdapter(new EstaApuntadoAdapter(estoyApuntadoList));
        }
    }

    private class ObtenerDatosUsuario extends AsyncTask<Integer,Void,UsuarioCompleto>{

        @Override
        protected UsuarioCompleto doInBackground(Integer... params) {
            Call<UsuarioCompleto> call = Servicio.instanciarServicio(UsuariosApi.class,Preferencias.preferencias.getString("token",null)).obtenerDatosUsuario(params[0]);
            Response<UsuarioCompleto> res = null;

            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.body();
        }

        @Override
        protected void onPostExecute(UsuarioCompleto us) {
            super.onPostExecute(us);

            toolbar.setTitle(us.getUsuario().getUsername());
            Picasso.with(PerfilUserActivity.this).load(us.getFoto()).resize(120, 120).error(R.drawable.logo)
                    .transform(new RoundedTransformation(70, 0))
                    .placeholder(R.drawable.logo).into(imgUser);
            txtNombre.setText(us.getUsuario().getFirstName() + " " + us.getUsuario().getLastName());
            txtCiudad.setText(us.getPoblacion() + ", " + us.getCiudad());
            txtBiografia.setText(us.getBiografia());
        }
    }

    private class ObtenerRelacionEntre2Usuarios extends AsyncTask<Integer,Void, SeguidosSimpleList>{

        @Override
        protected SeguidosSimpleList doInBackground(Integer... params) {
            Call<SeguidosSimpleList> call = Servicio.instanciarServicio(SeguidosApi.class,Preferencias.preferencias.getString("token",null)).obtenerRelacionEntre2Usuarios(params[0],params[1]);
            Response<SeguidosSimpleList> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.body();
        }

        @Override
        protected void onPostExecute(SeguidosSimpleList seguidosSimpleList) {
            super.onPostExecute(seguidosSimpleList);
            if(seguidosSimpleList.getResults().size()==0){
                btnGenerico.setText("SEGUIR");
                btnGenerico.setBackground(getDrawable(R.drawable.anyadir));
            }else{
                btnGenerico.setText("DEJAR DE SEGUIR");
                btnGenerico.setBackground(getDrawable(R.drawable.eliminar));
            }
        }
    }


    private class ObtenerPlanesVigentesDeUnUsuario extends AsyncTask<Integer,Void, List<PlanCompleto>>{

        @Override
        protected List<PlanCompleto> doInBackground(Integer... params) {
            Call<List<PlanCompleto>> call = Servicio.instanciarServicio(PlanesApi.class,Preferencias.preferencias.getString("token",null)).obtenerPlanesVigentesDeUnUsuario(params[0]);
            Response<List<PlanCompleto>> res  = null;
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

            recyclerPlanesCreados.setAdapter(new PlanAdapter(planCompletos,2));
        }
    }


}
