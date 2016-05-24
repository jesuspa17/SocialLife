package com.salesianostriana.sociallife.sociallifeapp.activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.nononsenseapps.filepicker.FilePickerActivity;
import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.PerfilUsuario;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.PlanesApi;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.UsuariosApi;
import com.salesianostriana.sociallife.sociallifeapp.utiles.RoundedTransformation;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PublicarPlanActivity extends AppCompatActivity {

    ImageView imagenPlan, imgCamara;
    TextView txtLocalizacion, txtTitulo, txtFecha, txtDescripcion;
    Button btnPublicar;


    String ciudad, poblacion, coordenadas, ruta_imagen, id_usuario;

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static final int FILE_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_plan);

        imagenPlan = (ImageView) findViewById(R.id.imageViewPublicarPlan);
        imgCamara = (ImageView) findViewById(R.id.imageViewCameraPublicar);
        txtLocalizacion = (TextView) findViewById(R.id.publicarTextViewLoc);
        txtTitulo = (TextView) findViewById(R.id.editTextTituloPublicar);
        txtFecha = (TextView) findViewById(R.id.editTextFechaPublicar);
        txtDescripcion = (TextView) findViewById(R.id.editTextDescripcionPublicar);
        btnPublicar = (Button) findViewById(R.id.btnPublicarPlan);

        new ObtenerMisDatos().execute();

        txtLocalizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.abrirBuscador(1, PublicarPlanActivity.this, Utils.filtro_direcciones);
            }
        });

        Picasso.with(this).load(R.drawable.banner)
                .error(R.drawable.logo)
                .fit()
                .transform(new RoundedTransformation(30, 0))
                .placeholder(R.drawable.banner)
                .into(imagenPlan);

        imgCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PublicarPlanActivity.this, FilePickerActivity.class);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
                i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
                startActivityForResult(i, FILE_CODE);
            }
        });

        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tit = txtTitulo.getText().toString();
                String loc = txtLocalizacion.getText().toString();
                String fec = txtFecha.getText().toString();
                String desc = txtDescripcion.getText().toString();

                if (tit.isEmpty() || loc.isEmpty() || fec.isEmpty() || ciudad.isEmpty()
                        || poblacion.isEmpty() || ruta_imagen==null || desc.isEmpty() || id_usuario.isEmpty() || coordenadas.isEmpty()) {

                } else {
                    //Publicar plan
                    String tkn = Preferencias.preferencias.getString("token", null);
                    PlanesApi service = Servicio.instanciarServicio(PlanesApi.class, tkn);
                    Log.i("Token", tkn);

                    RequestBody titulo = Utils.createStringRequest(tit);
                    RequestBody localizacion = Utils.createStringRequest(loc);
                    RequestBody ciu = Utils.createStringRequest(ciudad);
                    File file = new File(ruta_imagen);
                    RequestBody foto =
                            RequestBody.create(MediaType.parse("image/*"), file);
                    RequestBody pob = Utils.createStringRequest(poblacion);
                    RequestBody fecha = Utils.createStringRequest(fec);
                    RequestBody cat = Utils.createStringRequest("2");
                    RequestBody descrip = Utils.createStringRequest(desc);
                    RequestBody user = Utils.createStringRequest(id_usuario);
                    RequestBody coords = Utils.createStringRequest(coordenadas);


                    Call<String> call = service.publicarPlan(titulo, localizacion, ciu, foto, pob, fecha, cat, descrip, coords, user);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Response<String> response, Retrofit retrofit) {
                            if (response.code() == 201) {
                                Toast.makeText(PublicarPlanActivity.this, "Plan publicado", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(PublicarPlanActivity.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                PublicarPlanActivity.this.finish();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                        }
                    });

                    Toast.makeText(PublicarPlanActivity.this, "Plan publicado", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PublicarPlanActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    PublicarPlanActivity.this.finish();

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                txtLocalizacion.setText(place.getName());
                coordenadas = String.valueOf(place.getLatLng()).replace("lat/lng: (","").replace(")","");

                //Para obtener la ciudad
                String[] attr = place.getAddress().toString().split(",");
                if (attr.length == 4) {
                    String pob = attr[1].substring(6, attr[1].length());
                    String ciu = attr[2];

                    ciudad = ciu;
                    poblacion = pob;

                    Toast.makeText(this, pob + " " + ciu + " " + String.valueOf(place.getLatLng()), Toast.LENGTH_SHORT).show();

                } else {

                    String pob = attr[2].substring(6, attr[2].length());
                    String ciu = attr[3];

                    ciudad = ciu;
                    poblacion = pob;

                    Toast.makeText(this, pob + " " + ciu + " " + String.valueOf(place.getLatLng()), Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
            } else if (resultCode == RESULT_CANCELED) {
            }
        } else if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clip = data.getClipData();
                    if (clip != null) {
                        for (int i = 0; i < clip.getItemCount(); i++) {
                            Uri uri = clip.getItemAt(i).getUri();
                        }
                    }

                } else {
                    ArrayList<String> paths = data.getStringArrayListExtra
                            (FilePickerActivity.EXTRA_PATHS);
                    if (paths != null) {
                        for (String path : paths) {
                            Uri uri = Uri.parse(path);
                        }
                    }
                }
            } else {
                Uri uri = data.getData();
                Picasso.with(this).load(uri)
                        .error(R.drawable.logo)
                        .transform(new RoundedTransformation(15, 0))
                        .placeholder(R.drawable.logo)
                        .into(imagenPlan);
                Log.i("DIRECCION", uri.getPath());
                ruta_imagen = uri.getPath();
            }
        }

    }

    private class ObtenerMisDatos extends AsyncTask<Void, Void, List<PerfilUsuario>> {

        @Override
        protected List<PerfilUsuario> doInBackground(Void... params) {
            Call<List<PerfilUsuario>> call = Servicio.instanciarServicio(UsuariosApi.class, Preferencias.preferencias.getString("token", null)).obtenerDatosSimples();
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
            id_usuario = String.valueOf(lista.get(0).getId());
        }
    }
}
