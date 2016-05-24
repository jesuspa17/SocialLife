package com.salesianostriana.sociallife.sociallifeapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.UsuariosApi;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.rest_auth.Key;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.rest_auth.RegistroUser;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

public class RegistroActivity extends AppCompatActivity {


    EditText editUsuario,editEmail,editPassword;
    Button btn_siguiente;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editUsuario = (EditText) findViewById(R.id.editTextUsuarioRegistro);
        editEmail = (EditText) findViewById(R.id.editTextEmailRegistro);
        editPassword = (EditText)findViewById(R.id.editTextPassRegistro);
        btn_siguiente = (Button) findViewById(R.id.btn_siguiente);

        Preferencias.iniciarPreferencias(this);


        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usr = editUsuario.getText().toString();
                String pass1 = editPassword.getText().toString();
                String pass2 = editPassword.getText().toString();
                String em = editEmail.getText().toString();

                if(usr.isEmpty() || pass1.isEmpty() || pass2.isEmpty() || em.isEmpty()){
                    Toast.makeText(RegistroActivity.this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }else{

                    RegistroUser nuevoUsuario = new RegistroUser();
                    nuevoUsuario.setUsername(usr);
                    nuevoUsuario.setPassword1(pass1);
                    nuevoUsuario.setPassword2(pass2);
                    nuevoUsuario.setEmail(em);
                    new RegistroTask().execute(nuevoUsuario);
                }
            }
        });
    }

    //Asyntask que realiza el proceso de registro de un usuario nuevo en el servidor.
    private class RegistroTask extends AsyncTask<RegistroUser, Void, Key>{
        @Override
        protected Key doInBackground(RegistroUser... params) {
            if(params!=null){
                Call<Key> keyCall = Servicio.instanciarServicio(UsuariosApi.class, "").registrarse(params[0]);
                Response<Key> keyResponse = null;
                try {
                    keyResponse = keyCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert keyResponse != null;
                if(keyResponse.code() == 201){
                    return keyResponse.body();
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }

        @Override
        protected void onPostExecute(Key key) {
            super.onPostExecute(key);
            if(key!=null){

                Intent i = new Intent(RegistroActivity.this, NextRegistroActivity.class) ;
                Preferencias.editor.putString("token", "Token " + key.getKey());
                Log.i("TOKEN","TOKEN "+key.getKey());
                Preferencias.editor.putString("email",editEmail.getText().toString());
                Preferencias.editor.commit();
                startActivity(i);
                RegistroActivity.this.finish();

            }else{
                Toast.makeText(RegistroActivity.this, "Este usuario o email ya están en uso.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,LoginActivity.class));
        this.finish();
    }
}
