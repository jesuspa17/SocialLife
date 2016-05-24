package com.salesianostriana.sociallife.sociallifeapp.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.ComentarioAdapter;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.apuntarse.Apuntarse;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.planes.PlanCompleto;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_estoy_apuntado.EstoyApuntadoList;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_listas.Comentario;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.PerfilUsuario;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.PlanesApi;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.UsuariosApi;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

public class DetallesPlanActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    Toolbar toolbar;
    AppBarLayout appBarLayout;
    ImageView imgToolbar,imgUser;
    TextView txtDescripcion,txtCreador,txtFecha,txtApuntarse,txtAsistentes;
    RecyclerView recyclerView;
    LinearLayout linearApuntarse;

    private GoogleMap map;
    MapView mapView;
    private GoogleApiClient mGoogleApiClient = null;

    int idPlanObtenido;
    int idUsuarioObtenido;
    int idEstaApuntado;

    String poblacion, ciudad, coordenadas, localizacion;

    Marker marker_inicial;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_plan);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        imgToolbar = (ImageView) findViewById(R.id.imageViewDetalle);
        imgUser = (ImageView)findViewById(R.id.imageViewUserDetalles);
        txtDescripcion = (TextView) findViewById(R.id.textViewDescripcionDetalles);
        txtCreador = (TextView) findViewById(R.id.textViewCreadorDetalles);
        txtFecha = (TextView) findViewById(R.id.textViewFechaDetalles);
        txtApuntarse = (TextView) findViewById(R.id.txtApuntarse);
        txtAsistentes = (TextView) findViewById(R.id.textViewAsistentesDetalles);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_comentarios);
        mapView = (MapView) findViewById(R.id.mapview);
        linearApuntarse = (LinearLayout) findViewById(R.id.linearApuntarse);


        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            idPlanObtenido = extras.getInt("id_plan");
            new ObtenerMisDatos().execute();
            new ObtenerApuntados().execute(idPlanObtenido);
        }

        txtAsistentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetallesPlanActivity.this, ListadoAsitentesActivity.class);
                i.putExtra("id_plan",idPlanObtenido);
                startActivity(i);
            }
        });

        mapView.setFocusable(false);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<Comentario> lista_comentarios = new ArrayList<>();
        lista_comentarios.add(new Comentario("Este plan apunta maneras! Yo voy!","Tomás Bejarano","Hoy"));
        lista_comentarios.add(new Comentario("Yo también me apunto!", "Jesús Pallares", "Hoy"));
        lista_comentarios.add(new Comentario("Yo paso, no vale pa na", "José Maria Montero", "Hoy"));
        lista_comentarios.add(new Comentario("Que potente está esta aplicación", "Rocío Carmona", "Hoy"));
        lista_comentarios.add(new Comentario("Rt", "Lucía Jara", "Hoy"));

        recyclerView.setAdapter(new ComentarioAdapter(lista_comentarios));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        buildGoogleApiClient();

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        linearApuntarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtApuntarse.getText().toString().equalsIgnoreCase("APUNTARME AL PLAN")){
                    Apuntarse apuntarse = new Apuntarse();
                    apuntarse.setUsuario(idUsuarioObtenido);
                    apuntarse.setPlan(idPlanObtenido);
                    apuntarse.setFecha(Utils.fechaHoyDjango());
                    Log.i("DATOS_APUNTARSE", "IDPLAN: " + idPlanObtenido + " IDUSUARIO; " + idUsuarioObtenido);
                    new ApuntarseAunPlan().execute(apuntarse);
                    new ComprobarSiEstaApuntado().execute(idUsuarioObtenido,idPlanObtenido);
                }else{
                    new BorrarseDelPlan().execute(idEstaApuntado);
                }
            }
        });

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setScrollGesturesEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setZoomGesturesEnabled(false);



        new ObtenerUnPlan(){
            @Override
            protected void onPostExecute(PlanCompleto planCompleto) {
                super.onPostExecute(planCompleto);


                txtFecha.setText(Utils.fechaFormateada(planCompleto.getFecha()));
                txtDescripcion.setText(planCompleto.getDescripcion());
                txtCreador.setText(planCompleto.getUsuario().getUsuario().getFirstName() + " " + planCompleto.getUsuario().getUsuario().getLastName());
                Utils.cargarImagen(DetallesPlanActivity.this, planCompleto.getUsuario().getFoto(), imgUser);
                Picasso.with(DetallesPlanActivity.this).load(planCompleto.getFoto()).fit().into(imgToolbar);
                toolbar.setTitle(planCompleto.getTitulo());
                setSupportActionBar(toolbar);

                localizacion = planCompleto.getLocalizacion();
                poblacion = planCompleto.getPoblacion();
                ciudad = planCompleto.getCiudad();
                coordenadas = planCompleto.getCoordenadas();

                String[] coords = coordenadas.split(",");
                LatLng mi_posicion = new LatLng(Float.parseFloat(coords[0]),Float.parseFloat(coords[1]));
                marker_inicial = map.addMarker(new MarkerOptions()
                        .position(mi_posicion)
                        .draggable(true));

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(mi_posicion, 17));
                marker_inicial.setTitle(localizacion);
                marker_inicial.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_map));
                marker_inicial.showInfoWindow();

                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + poblacion + "," + ciudad + "," + localizacion + "");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });

            }
        }.execute(idPlanObtenido);

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
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
            idUsuarioObtenido = lista.get(0).getId();
            new ComprobarSiEstaApuntado().execute(idUsuarioObtenido, idPlanObtenido);
        }
    }



    private class ObtenerUnPlan extends AsyncTask<Integer,Void, PlanCompleto>{
        @Override
        protected PlanCompleto doInBackground(Integer... params) {

            if(params!=null){
                Call<PlanCompleto> call = Servicio.instanciarServicio(PlanesApi.class, Preferencias.preferencias.getString("token",null)).obtenerUnPlan(params[0]);
                Response<PlanCompleto> res = null;
                try {
                    res = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return res.body();
            }
            return null;
        }

    }

    private class ComprobarSiEstaApuntado extends AsyncTask<Integer, Void, EstoyApuntadoList>{

        @Override
        protected EstoyApuntadoList doInBackground(Integer... params) {
            Call<EstoyApuntadoList> call = Servicio.instanciarServicio(PlanesApi.class,Preferencias.preferencias.getString("token",null)).comprobarSiEstaApuntado(params[0],params[1]);
            Response <EstoyApuntadoList> res = null;

            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert res != null;
            return res.body();
        }

        @Override
        protected void onPostExecute(EstoyApuntadoList estoyApuntado) {
            super.onPostExecute(estoyApuntado);

            if(estoyApuntado.getResults().size()==0){
                txtApuntarse.setText("APUNTARME AL PLAN");
            }else{
                txtApuntarse.setText("BORRARME DEL PLAN");
                idEstaApuntado = estoyApuntado.getResults().get(0).getId();
            }
        }
    }


    private class ApuntarseAunPlan extends AsyncTask<Apuntarse, Void, Apuntarse>{

        @Override
        protected Apuntarse doInBackground(Apuntarse... params) {
            Call<Apuntarse> call = Servicio.instanciarServicio(PlanesApi.class,Preferencias.preferencias.getString("token",null)).apuntarse(params[0]);
            Response<Apuntarse> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.body();
        }

        @Override
        protected void onPostExecute(Apuntarse apuntarse) {
            super.onPostExecute(apuntarse);
            if(apuntarse!=null){
                txtApuntarse.setText("BORRARME DEL PLAN");
            }
        }
    }


    private class BorrarseDelPlan extends AsyncTask<Integer,Void,Integer>{

        @Override
        protected Integer doInBackground(Integer... params) {
            Call<Apuntarse> call = Servicio.instanciarServicio(PlanesApi.class,Preferencias.preferencias.getString("token",null)).borrarseDelPlan(params[0]);
            Response<Apuntarse> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.code();
        }

        @Override
        protected void onPostExecute(Integer code) {
            super.onPostExecute(code);
            if(code == 204){
                txtApuntarse.setText("APUNTARME AL PLAN");
            }
        }
    }


    private class ObtenerApuntados extends AsyncTask<Integer, Void, EstoyApuntadoList>{

        @Override
        protected EstoyApuntadoList doInBackground(Integer... params) {
            Call<EstoyApuntadoList> call = Servicio.instanciarServicio(PlanesApi.class, Preferencias.preferencias.getString("token",null)).obtenerUsuariosApuntados(params[0]);
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
            int num = estoyApuntadoList.getResults().size();
            if(num == 0)txtAsistentes.setText(String.valueOf(estoyApuntadoList.getResults().size()+" asistentes"));
            if(num == 1)txtAsistentes.setText(String.valueOf(estoyApuntadoList.getResults().size()+" asistente"));
            if(num > 1) txtAsistentes.setText(String.valueOf(estoyApuntadoList.getResults().size()+" asistentes"));

        }
    }
}
