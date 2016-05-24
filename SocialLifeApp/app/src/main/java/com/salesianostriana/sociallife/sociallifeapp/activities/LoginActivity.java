package com.salesianostriana.sociallife.sociallifeapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.rest_auth.Key;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.rest_auth.LoginUser;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.UsuariosApi;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;


public class LoginActivity extends AppCompatActivity{

    EditText editUser, editPassword;
    TextView txtRegistrarse;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        editUser = (EditText) findViewById(R.id.editUserOrEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btn_login = (Button) findViewById(R.id.btn_login);
        txtRegistrarse = (TextView) findViewById(R.id.txtRegistrarse);

        Preferencias.iniciarPreferencias(this);
        /*Preferencias.editor.clear();
        Preferencias.editor.commit();*/

        if(Preferencias.preferencias.getString("token",null)!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            this.finish();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String us = editUser.getText().toString();
                String pass = editPassword.getText().toString();
                if(us.isEmpty() || pass.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Rellene los campos vacíos",Toast.LENGTH_SHORT).show();
                }else{
                    LoginUser loginUser = new LoginUser();
                    loginUser.setUsername(us);
                    loginUser.setPassword(pass);
                    new LoginTask().execute(loginUser);
                }
            }
        });

        txtRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistroActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                LoginActivity.this.finish();
            }
        });

    }

    private class LoginTask extends AsyncTask<LoginUser, Void, Key> {

        @Override
        protected Key doInBackground(LoginUser... params) {
            if(params!=null){

                Call<Key> keyCall = Servicio.instanciarServicio(UsuariosApi.class, "").loguearse(params[0]);
                Response<Key> keyResponse = null;
                try {
                    keyResponse = keyCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert keyResponse != null;
                if(keyResponse.code() == 200){
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

                //Guardamos la key obtenida en preferencias para luego usarla dentro de la App
                String key_obtenida = key.getKey();
                Preferencias.editor.putString("token", "Token " + key_obtenida);
                Preferencias.editor.apply();
                Preferencias.editor.commit();

                //Inicio el activity principal una vez logueado.
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                LoginActivity.this.finish();

            }else{
                Toast.makeText(LoginActivity.this, "Datos incorrectos, pruebe otra vez!", Toast.LENGTH_SHORT).show();
            }

        }
    }



}

