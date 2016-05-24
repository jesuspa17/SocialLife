package com.salesianostriana.sociallife.sociallifeapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.planes.PlanCompleto;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.UsuarioCompleto;
import com.salesianostriana.sociallife.sociallifeapp.fragments.AjustesFragment;
import com.salesianostriana.sociallife.sociallifeapp.fragments.EstoyApuntadoFragment;
import com.salesianostriana.sociallife.sociallifeapp.fragments.GenteFragment;
import com.salesianostriana.sociallife.sociallifeapp.fragments.MisPlanesFragment;
import com.salesianostriana.sociallife.sociallifeapp.fragments.PlanesFragment;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.PlanesApi;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.UsuariosApi;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Elementos UI
    ImageView imageViewUsuario;
    TextView txtUsuario;
    public static TextView txtCiudad;

    DrawerLayout drawer;
    NavigationView navigationView;
    public static Toolbar toolbar;
    FloatingActionButton fab;


    String token;
    String ciudad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.planes_en);

        setSupportActionBar(toolbar);

        //Inicializo los elementos de la UI
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.shitstuff);
        fab = (FloatingActionButton) findViewById(R.id.fab);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View cabecera = navigationView.getHeaderView(0);
        imageViewUsuario = (ImageView) cabecera.findViewById(R.id.imageViewFotoUsuario);
        txtUsuario = (TextView) cabecera.findViewById(R.id.navTxtNombreUsuario);
        txtCiudad = (TextView) cabecera.findViewById(R.id.navTxtCiudad);

        imageViewUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PerfilUserActivity.class));
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        //Inicia las preferencias
        Preferencias.iniciarPreferencias(this);
        token = Preferencias.preferencias.getString("token",null);

        Log.i("MAIN_ACTIVITY", "TOKEN " + token);
        Log.i("MAIN_ACTIVITY", "CIUDAD_ACTUAL " + Preferencias.preferencias.getString("ciudad",null));

        if(token!=null){

            //Para que la petición no se haga demasiado rápido
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new ObtenerMisDatosTask().execute(token);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ObtenerMisPlanes().execute(token);
            }
        });

        //Seteo el fragment que se mostrará inicialmente.
        cambiarFragment(new PlanesFragment());
    }


    public void cambiarFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, f).commit();
    }

    boolean salir = false;

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_planes) {

            if(Preferencias.preferencias.getString("ciudad",null)!=null){
                toolbar.setTitle(R.string.planes_en);
                toolbar.setTitle(toolbar.getTitle() + " " + Preferencias.preferencias.getString("ciudad",null));
            }else{
                toolbar.setTitle(R.string.planes_en);
                toolbar.setTitle(toolbar.getTitle() + " " + ciudad);
            }

            cambiarFragment(new PlanesFragment());
            fab.setVisibility(View.VISIBLE);


        } else if (id == R.id.nav_gente) {
            toolbar.setTitle(R.string.gente);
            cambiarFragment(new GenteFragment());
            fab.setVisibility(View.INVISIBLE);

        } else if (id == R.id.nav_mis_planes) {
            toolbar.setTitle(R.string.planes_creados);
            cambiarFragment(new MisPlanesFragment());
            fab.setVisibility(View.INVISIBLE);

        } else if (id == R.id.nav_mist_tickets) {
            toolbar.setTitle(R.string.estoy_apuntado);
            cambiarFragment(new EstoyApuntadoFragment());
            fab.setVisibility(View.INVISIBLE);

        }  else if (id == R.id.nav_ajustes) {
            toolbar.setTitle(R.string.ajustes);
            cambiarFragment(new AjustesFragment());
            fab.setVisibility(View.INVISIBLE);

        }

        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    int id_user;
    public class ObtenerMisDatosTask extends AsyncTask<String, Void, List<UsuarioCompleto>>{

        @Override
        protected List<UsuarioCompleto> doInBackground(String... params){
            Call<List<UsuarioCompleto>> call = Servicio.instanciarServicio(UsuariosApi.class,token).obtenerTodosMisDatos();
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
            if(usuarioCompleto.size()!=0){
                ciudad = usuarioCompleto.get(0).getUbicacion_actual();
                Utils.cargarImagen(MainActivity.this, usuarioCompleto.get(0).getFoto(), imageViewUsuario);
                txtUsuario.setText(txtUsuario.getText() + " " + usuarioCompleto.get(0).getUsuario().getUsername()+" !");
                txtCiudad.setText(txtCiudad.getText() + " " + ciudad);
                toolbar.setTitle(toolbar.getTitle() + " " + ciudad);
                Preferencias.editor.putString("ciudad",ciudad).apply();
                id_user = usuarioCompleto.get(0).getId();

            }else{
                Log.e("ERROR_MAINACTIVITY","ERROR_MAINACTIVITY");
            }
        }
    }

    private class ObtenerMisPlanes extends AsyncTask<String, Void, List<PlanCompleto>> {

        @Override
        protected List<PlanCompleto> doInBackground(String... params) {
            Call<List<PlanCompleto>> call = Servicio.instanciarServicio(PlanesApi.class, params[0]).comprobarPlanesCreados();
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
                if(planCompletos.size()<3){
                    startActivity(new Intent(MainActivity.this, PublicarPlanActivity.class));
                }else{
                    Toast.makeText(MainActivity.this,"Ya ha creado el máximo de planes en vigencia",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
