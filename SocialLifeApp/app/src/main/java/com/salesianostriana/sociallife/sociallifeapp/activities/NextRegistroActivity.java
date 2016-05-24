package com.salesianostriana.sociallife.sociallifeapp.activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.nononsenseapps.filepicker.FilePickerActivity;
import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.ResultUsuario;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.Users;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.UsuariosApi;
import com.salesianostriana.sociallife.sociallifeapp.utiles.RoundedTransformation;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class NextRegistroActivity extends AppCompatActivity {

    TextView txtCiudad, txtPoblacion;
    EditText editBiografia, editNombre, editApellidos;
    Button btn_finalizar;
    ImageView imgUsuario, imgBuscar;

    int id_obtenido;
    String ruta_imagen;
    Uri uriData;


    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static final int REQUEST_CODE_AUTOCOMPLETE_2 = 2;
    private static final int FILE_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_registro);

        new GetUsuarioTask().execute(Preferencias.preferencias.getString("email", null));

        txtCiudad = (TextView) findViewById(R.id.txtCiudadRegistro);
        txtPoblacion = (TextView) findViewById(R.id.txtPoblacionRegistro);
        editBiografia = (EditText) findViewById(R.id.editTextBiografiaRegistro);
        editNombre = (EditText) findViewById(R.id.editTextNombreRegistro);
        editApellidos = (EditText)findViewById(R.id.editTextApellidosRegistro);
        btn_finalizar = (Button) findViewById(R.id.btn_finalizar);
        imgUsuario = (ImageView) findViewById(R.id.imageViewFotoUserRegistro);
        imgBuscar = (ImageView) findViewById(R.id.imageViewBuscarFotoRegistro);

        txtPoblacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.abrirBuscador(REQUEST_CODE_AUTOCOMPLETE,NextRegistroActivity.this,Utils.filtro_regiones);
            }
        });

        txtCiudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.abrirBuscador(REQUEST_CODE_AUTOCOMPLETE_2,NextRegistroActivity.this,Utils.filtro_regiones);
            }
        });

        imgBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NextRegistroActivity.this, FilePickerActivity.class);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
                i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
                startActivityForResult(i, FILE_CODE);
            }
        });

        btn_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String pob = txtPoblacion.getText().toString();
                String cid = txtCiudad.getText().toString();
                String bio = editBiografia.getText().toString();
                String id = String.valueOf(id_obtenido);
                String nom = editNombre.getText().toString();
                String ape = editApellidos.getText().toString();


                if (pob.isEmpty() || cid.isEmpty() || bio.isEmpty() || id.isEmpty() || ruta_imagen.isEmpty()) {
                    Toast.makeText(NextRegistroActivity.this, "Por favor, asegurese de rellenar todos los campos del formulario", Toast.LENGTH_SHORT).show();
                } else {

                    ResultUsuario res = new ResultUsuario();
                    res.setFirstName(nom);
                    res.setLastName(ape);
                    new ActualizarNombreTask().execute(res);

                    Preferencias.editor.putString("ciudad", cid).apply();

                    //Proceso de subida de un nuevo Usuario al servidor.
                    String tkn = Preferencias.preferencias.getString("token", null);
                    UsuariosApi service = Servicio.instanciarServicio(UsuariosApi.class,tkn);
                    Log.i("Token",tkn);

                    RequestBody poblacion = Utils.createStringRequest(txtPoblacion.getText().toString());
                    RequestBody ciudad = Utils.createStringRequest(txtCiudad.getText().toString());
                    RequestBody biografia = Utils.createStringRequest(editBiografia.getText().toString());
                    RequestBody usuario = Utils.createStringRequest(id);
                    File file = new File(ruta_imagen);
                    RequestBody foto =
                            RequestBody.create(MediaType.parse("image/*"), file);

                    Call<String> call = service.nuevoUsuario(usuario, poblacion, ciudad, biografia, ciudad, foto);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Response<String> response, Retrofit retrofit) {
                            if (response.code() == 201) {
                                Toast.makeText(NextRegistroActivity.this, "Registro finalizado", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(NextRegistroActivity.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                NextRegistroActivity.this.finish();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                        }
                    });

                    Toast.makeText(NextRegistroActivity.this, "Registro finalizado", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(NextRegistroActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    NextRegistroActivity.this.finish();
                }
            }
        });
    }

    boolean salir = false;
    @Override
    public void onBackPressed() {
        if(salir){
            super.onBackPressed();
        }
        this.salir = true;
        Toast.makeText(this, "Por favor, termine de editar su perfil.", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                salir = false;
            }
        }, 2000);
        /*startActivity(new Intent(this, NextRegistroActivity.class));
        this.finish();*/
    }

    private class ActualizarNombreTask extends AsyncTask<ResultUsuario, Void, String>{

        @Override
        protected String doInBackground(ResultUsuario... params) {
            Call<ResultUsuario> call = Servicio.instanciarServicio(UsuariosApi.class,Preferencias.preferencias.getString("token", null)).editUser(String.valueOf(id_obtenido),params[0]);
            Response<ResultUsuario> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private class GetUsuarioTask extends AsyncTask<String, Void, Users> {

        @Override
        protected Users doInBackground(String... params) {
            Call<Users> call = Servicio.instanciarServicio(UsuariosApi.class, Preferencias.preferencias.getString("token", null)).buscarUsuarioPorEmail(params[0]);
            Response<Users> response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert response != null;
            return response.body();
        }

        @Override
        protected void onPostExecute(Users users) {
            super.onPostExecute(users);
            id_obtenido = users.getResults().get(0).getId();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                txtPoblacion.setText(place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
            } else if (resultCode == RESULT_CANCELED) {
            }

        } else if (requestCode == REQUEST_CODE_AUTOCOMPLETE_2) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                txtCiudad.setText(place.getName());
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
                uriData = uri;
                Picasso.with(this).load(uri)
                            .resize(120, 120)
                            .error(R.drawable.logo)
                            .transform(new RoundedTransformation(70, 0))
                            .placeholder(R.drawable.logo)
                            .into(imgUsuario);
                Log.i("DIRECCION", uri.getPath());
                ruta_imagen = uri.getPath();
            }
        }

    }
}
