package com.salesianostriana.sociallife.sociallifeapp.interfaces;

import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.rest_auth.Key;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.rest_auth.LoginUser;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.rest_auth.RegistroUser;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.ModificarUbicacion;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.PerfilUsuario;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.ResultUsuario;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.Users;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.UsuarioCompleto;
import com.squareup.okhttp.RequestBody;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Jesus Pallares on 08/05/2016.
 */
public interface UsuariosApi {

    /**
     * Permite el logueo de un usuario.
     * @param loginUser
     * @return
     */
    @Headers("{Content-Type: application/json}")
    @POST("/rest-auth/login/")
    Call<Key> loguearse (@Body LoginUser loginUser);

    /**
     * Permite registrarse en la aplicación
     * @param registroUser
     * @return
     */
    @Headers("{Content-Type: application/json}")
    @POST("/rest-auth/registration/")
    Call<Key> registrarse(@Body RegistroUser registroUser);

    /**
     *
     * @param email
     * @return
     */
    @GET("/api/users/")
    Call<Users> buscarUsuarioPorEmail(@Query(value="search") String email);


    /**
     * Obtiene todos los datos de un usuario, incluyendo los datos guardados en la tabla User
     * @return
     */
    @GET("/api/usuarios/me/ ")
    Call<List<UsuarioCompleto>> obtenerTodosMisDatos();


    /**
     *
     * @param usuario
     * @param poblacion
     * @param ciudad
     * @param biografia
     * @param file
     * @return
     */
    @Headers("{Content-Type: multipart/form-data}")
    @Multipart
    @POST("/api/usuariosadd/")
    Call<String> nuevoUsuario(@Part("usuario") RequestBody usuario,
                              @Part("poblacion") RequestBody poblacion,
                              @Part("ciudad")RequestBody ciudad,
                              @Part("biografia")RequestBody biografia,
                              @Part("ubicacion_actual") RequestBody ubicacion_actual,
                              @Part("foto\"; filename=\"image.jpg\"") RequestBody file);

    /**
     * Edita un usuario de la tabla Users que viene por defecto
     * @param id
     * @param json
     * @return
     */
    @Headers("{Content-Type: application/json}")
    @PATCH("/api/users/{id}/")
    Call<ResultUsuario> editUser(@Path("id") String id, @Body ResultUsuario json);


    /**
     *
     * @param id_user
     * @param file
     * @return
     */
    @Headers("{Content-Type: multipart/form-data}")
    @Multipart
    @PUT("/api/usuariosadd/{id_usuario}/")
    Call<PerfilUsuario> editarFoto(@Path("id_usuario") String id_user, @Part("foto\"; filename=\"image.jpg\"") RequestBody file);

    /**
     *
     * @param id_user
     * @param us
     * @return
     */
    @Headers("{Content-Type: application/json}")
    @PATCH("/api/usuariosadd/{id_usuario}/")
    Call<PerfilUsuario> editarDatosPersonales(@Path("id_usuario") String id_user, @Body PerfilUsuario us);

    /**
     *
     * @return
     */
    @GET("/api/usuariosadd/me/")
    Call<List<PerfilUsuario>> obtenerDatosSimples();

    /**
     *
     * @param id
     * @param mod
     * @return
     */
    @Headers("{Content-Type: application/json}")
    @PATCH("/api/usuarios/{id}/")
    Call<UsuarioCompleto> modificarCiudadActual(@Path("id") int id, @Body ModificarUbicacion mod);


    @GET("/api/usuarios/{id}/")
    Call<UsuarioCompleto> obtenerDatosUsuario(@Path("id") Integer id);




}
