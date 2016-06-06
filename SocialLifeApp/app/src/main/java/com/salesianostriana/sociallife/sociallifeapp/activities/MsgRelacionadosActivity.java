package com.salesianostriana.sociallife.sociallifeapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.MensajesRelacionadosAdapter;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_mensajes.Mensaje;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_mensajes.NuevoMensaje;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.UsuarioCompleto;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.MensajesApi;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.UsuariosApi;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Preferencias;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

/**
 * Activity en el que se muestran los mensajes relacionados entre 2 usuarios.
 */
public class MsgRelacionadosActivity extends AppCompatActivity {

    //Elementos de la UI
    RecyclerView recyclerViewMsgRelacionados;
    MensajesRelacionadosAdapter adapter;
    ImageView imgEnviar;
    EditText editContenido;



    //Almacenarán los ID de los usuarios para realizar la consulta que devuelve todos los mensajes entre ellos.
    Integer id_principal, id_amigo;
    //Almacenarán los datos que vendrán como extras.
    String username_amigo,foto;

    //Regulará el estado del Thread "hilo"
    Boolean empezar;
    //Hilo que se ejecuta para relanzar la consulta de obtener todos los mensajes entre 2 usuarios.
    Thread hilo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_relacionados);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        //Se coloca inicialmente la variable a true.
        empezar = true;

        //Se inician los elementos de la UI
        imgEnviar = (ImageView) findViewById(R.id.imgViewEnviarActMsgRelacionado);
        editContenido = (EditText) findViewById(R.id.editTextContenidoActMsgRelacionado);
        recyclerViewMsgRelacionados = (RecyclerView) findViewById(R.id.recyclerViewMensajesRelacionados);
        recyclerViewMsgRelacionados.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewMsgRelacionados.setLayoutManager(layoutManager);


        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            //Se reciben los extras obtenidos del intent:
            id_amigo = extras.getInt("id_usuario");
            username_amigo = extras.getString("nom_user");
            foto = extras.getString("foto_user");

            ImageView img = (ImageView) toolbar.findViewById(R.id.imageViewActRelacionados);
            TextView titulo = (TextView) toolbar.findViewById(R.id.txtViewTituloMsgRelacionados);

            titulo.setText(getResources().getString(R.string.mensajescon)+" " + username_amigo);
            Utils.cargarImagen(this, foto, img);

            //en el OnPostExecute se realiza la consulta que obtiene los mensajes relacionados
            Log.i("ID_AMIGO", "" + id_amigo);
            new ObtenerMisDatosTask().execute();
        }

        //Envia un nuevo mensaje
        imgEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editContenido.getText().toString().isEmpty()) {
                    //Se crea el objeto necesario que debe llevar como cuerpo de la petición.
                    NuevoMensaje nuevoMensaje = new NuevoMensaje();
                    nuevoMensaje.setUsuarioEmisor(id_principal);
                    nuevoMensaje.setUsuarioReceptor(id_amigo);
                    nuevoMensaje.setContenido(editContenido.getText().toString());
                    nuevoMensaje.setFecha(Utils.fechaHoyDjango());

                    //Se lanza la petición y se limpia el campo del contenido del mensaje.
                    new EnviarMensajeTask().execute(nuevoMensaje);
                    editContenido.setText("");
                }
            }
        });

        //Se lanza el hilo que obtiene automaticamente cada 5 segundos los mensajes entre 2 usuarios.
        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                while (empezar) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new ObtenerMensajesRelacionados().execute(id_principal, id_amigo);
                }
            }
        });
        hilo.start();
        setSupportActionBar(toolbar);
    }


    /**
     * Realiza la petición que obtiene los mensajes entre 2 usuarios.
     */
    private class ObtenerMensajesRelacionados extends AsyncTask<Integer,Void, List<Mensaje>> {

        @Override
        protected List<Mensaje> doInBackground(Integer... params) {
            Call<List<Mensaje>> call = Servicio.instanciarServicio(MensajesApi.class, Preferencias.preferencias.getString("token", null)).obtenerMensajesRelacionados(params[0],params[1]);
            Response<List<Mensaje>> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.body();
        }

        @Override
        protected void onPostExecute(List<Mensaje> mensajes) {
            super.onPostExecute(mensajes);

            //Actualiza el recyclerView a la vista.
            adapter = new MensajesRelacionadosAdapter(mensajes);
            recyclerViewMsgRelacionados.setAdapter(adapter);
            recyclerViewMsgRelacionados.post(new Runnable() {
                @Override
                public void run() {
                    recyclerViewMsgRelacionados.smoothScrollToPosition(adapter.getItemCount());
                }
            });
        }
    }

    /**
     * Realiza la petición que obtiene los datos de un usuario.
     */
    public class ObtenerMisDatosTask extends AsyncTask<String, Void, List<UsuarioCompleto>> {

        @Override
        protected List<UsuarioCompleto> doInBackground(String... params) {
            Call<List<UsuarioCompleto>> call = Servicio.instanciarServicio(UsuariosApi.class, Preferencias.preferencias.getString("token", null)).obtenerTodosMisDatos();
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
        protected void onPostExecute(List<UsuarioCompleto> usuarioCompletos) {
            super.onPostExecute(usuarioCompletos);
            //Almacena la id del usuario actual.
            id_principal = usuarioCompletos.get(0).getId();
            new ObtenerMensajesRelacionados().execute(id_principal, id_amigo);
        }
    }

    /**
     * Realiza la petición que permite enviar un nuevo mensaje.
     */
    private class EnviarMensajeTask extends AsyncTask<NuevoMensaje,Void,NuevoMensaje>{
        @Override
        protected NuevoMensaje doInBackground(NuevoMensaje... params) {
            Call<NuevoMensaje> call = Servicio.instanciarServicio(MensajesApi.class,Preferencias.preferencias.getString("token",null)).enviarMensajeA(params[0]);
            Response<NuevoMensaje> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.body();
        }
        @Override
        protected void onPostExecute(NuevoMensaje nuevoMensaje) {
            super.onPostExecute(nuevoMensaje);
            new ObtenerMensajesRelacionados().execute(id_principal, id_amigo);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        empezar = false;
        hilo.interrupt();
    }
}
