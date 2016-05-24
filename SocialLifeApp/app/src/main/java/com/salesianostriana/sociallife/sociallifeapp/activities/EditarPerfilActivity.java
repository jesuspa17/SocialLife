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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.nononsenseapps.filepicker.FilePickerActivity;
import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.PerfilUsuario;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.ResultUsuario;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.UsuarioCompleto;
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

public class EditarPerfilActivity extends AppCompatActivity {

    TextView txtCiudad, txtPoblacion;
    EditText editBiografia, editNombre, editApellidos;
    Button btnGuardarCambios;
    ImageView imgUsuario, imgBuscar;


    String id_user_simple;
    String id_obtenido;
    String ruta_imagen;
    Uri uriData;


    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static final int REQUEST_CODE_AUTOCOMPLETE_2 = 2;
    private static final int FILE_CODE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        new ObtenerUsuarioCompleto().execute(Preferencias.preferencias.getString("token", null));

        txtCiudad = (TextView) findViewById(R.id.txtCiudadEditar);
        txtPoblacion = (TextView) findViewById(R.id.txtPoblacionEditar);
        editBiografia = (EditText) findViewById(R.id.editTextBiografiaEditar);
        editNombre = (EditText) findViewById(R.id.editTextNombreEditar);
        editApellidos = (EditText) findViewById(R.id.editTextApellidosEditar);
        btnGuardarCambios = (Button) findViewById(R.id.btnGuardarCambiosPerfil);
        imgUsuario = (ImageView) findViewById(R.id.imageViewFotoUserEditar);
        imgBuscar = (ImageView) findViewById(R.id.imageViewBuscarFotoEditar);


        txtPoblacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.abrirBuscador(REQUEST_CODE_AUTOCOMPLETE, EditarPerfilActivity.this, new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS).build());
            }
        });

        txtCiudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.abrirBuscador(REQUEST_CODE_AUTOCOMPLETE_2, EditarPerfilActivity.this, new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS).build());
            }
        });

        imgBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditarPerfilActivity.this, FilePickerActivity.class);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
                i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
                startActivityForResult(i, FILE_CODE);
            }
        });

        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pob = txtPoblacion.getText().toString();
                String cid = txtCiudad.getText().toString();
                String bio = editBiografia.getText().toString();
                String id = String.valueOf(id_obtenido);
                String nom = editNombre.getText().toString();
                String ape = editApellidos.getText().toString();

                if (pob.isEmpty() || cid.isEmpty() || bio.isEmpty() || id.isEmpty() || ruta_imagen.isEmpty() || nom.isEmpty() || ape.isEmpty()) {
                    Toast.makeText(EditarPerfilActivity.this, "Por favor, asegurese de rellenar todos los campos del formulario", Toast.LENGTH_SHORT).show();
                } else {

                    ResultUsuario res = new ResultUsuario();
                    res.setFirstName(nom);
                    res.setLastName(ape);

                    PerfilUsuario perfnuevo = new PerfilUsuario();
                    perfnuevo.setPoblacion(pob);
                    perfnuevo.setCiudad(cid);
                    perfnuevo.setBiografia(bio);
                    perfnuevo.setUbicacion_actual(cid);

                    new ActualizarNombreTask().execute(res);
                    new ActualizarPerfilTask().execute(perfnuevo);


                    String tkn = Preferencias.preferencias.getString("token", null);
                    UsuariosApi service = Servicio.instanciarServicio(UsuariosApi.class, tkn);
                    File file = new File(ruta_imagen);
                    RequestBody foto =
                            RequestBody.create(MediaType.parse("image/*"), file);
                    Call<PerfilUsuario> call = service.editarFoto(id_user_simple, foto);
                    call.enqueue(new Callback<PerfilUsuario>() {
                        @Override
                        public void onResponse(Response<PerfilUsuario> response, Retrofit retrofit) {
                            if (response.code() == 201) {
                            }
                        }
                        @Override
                        public void onFailure(Throwable t) {
                        }
                    });
                    Intent i = new Intent(EditarPerfilActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    EditarPerfilActivity.this.finish();
                }
            }
        });

    }


    private class ActualizarPerfilTask extends AsyncTask<PerfilUsuario, Void, PerfilUsuario> {

        @Override
        protected PerfilUsuario doInBackground(PerfilUsuario... params) {
            Call<PerfilUsuario> call = Servicio.instanciarServicio(UsuariosApi.class, Preferencias.preferencias.getString("token", null)).editarDatosPersonales(id_user_simple, params[0]);
            try {
                Response<PerfilUsuario> res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class ActualizarNombreTask extends AsyncTask<ResultUsuario, Void, String> {

        @Override
        protected String doInBackground(ResultUsuario... params) {
            Call<ResultUsuario> call = Servicio.instanciarServicio(UsuariosApi.class, Preferencias.preferencias.getString("token", null)).editUser(String.valueOf(id_obtenido), params[0]);
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


    private class ObtenerUsuarioCompleto extends AsyncTask<String, Void, List<UsuarioCompleto>> {

        @Override
        protected List<UsuarioCompleto> doInBackground(String... params) {
            Call<List<UsuarioCompleto>> call = Servicio.instanciarServicio(UsuariosApi.class, params[0]).obtenerTodosMisDatos();
            Response<List<UsuarioCompleto>> res = null;

            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert res != null;
            if (res.code() == 200) {
                return res.body();
            }
            return null;
        }


        @Override
        protected void onPostExecute(List<UsuarioCompleto> usuarioCompleto) {
            super.onPostExecute(usuarioCompleto);
            if (usuarioCompleto.size() != 0) {
                UsuarioCompleto uc = usuarioCompleto.get(0);

                id_user_simple = String.valueOf(uc.getId());
                id_obtenido = String.valueOf(uc.getUsuario().getId());
                Log.i("ID_OBTENIDO", "ID_OBTENIDO " + id_user_simple);
                Log.i("ID_OBTENIDO", "ID_OBTENIDO " + id_obtenido);
                editNombre.setText(uc.getUsuario().getFirstName());
                editApellidos.setText(uc.getUsuario().getLastName());
                txtCiudad.setText(uc.getCiudad());
                txtPoblacion.setText(uc.getPoblacion());
                editBiografia.setText(uc.getBiografia());
                ruta_imagen = uc.getFoto();
                Utils.cargarImagen(EditarPerfilActivity.this, ruta_imagen, imgUsuario);
            }
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
