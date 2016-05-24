package com.salesianostriana.sociallife.sociallifeapp.interfaces;

import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.apuntarse.Apuntarse;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.planes.PlanCompleto;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_estoy_apuntado.EstoyApuntado;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_estoy_apuntado.EstoyApuntadoList;
import com.squareup.okhttp.RequestBody;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Jesus Pallares on 16/05/2016.
 */
public interface PlanesApi {

    /**
     * Obtiene todos mis planes para hoy
     * @return
     */
    @GET("/api/planes/planeshoy/")
    Call<List<PlanCompleto>> obtenerPlanesParaHoy();

    @GET("/api/planes/planesproximos/")
    Call<List<PlanCompleto>> obtenerPlanesProximosDias();


    @GET("/api/planes/misplanes/")
    Call<List<PlanCompleto>> obtenerMisPlanes();

    @GET("/api/planes/compruebaplanes/")
    Call<List<PlanCompleto>> comprobarPlanesCreados();


    @GET("/api/planes/{id}/")
    Call<PlanCompleto> obtenerUnPlan(@Path("id") Integer id);


    @GET("/api/planes/planesVigentesDeUnUsuario")
    Call<List<PlanCompleto>> obtenerPlanesVigentesDeUnUsuario(@Query("usuario__id") Integer id_usuario);


    @GET("/api/estaapuntado/estoyapuntado/")
    Call<List<EstoyApuntado>> estoyApuntadoA();

    @GET("/api/estaapuntado")
    Call<EstoyApuntadoList> estaApuntadoA(@Query("usuario__id") Integer id);


    @GET("/api/estaapuntado/")
    Call<EstoyApuntadoList> comprobarSiEstaApuntado(@Query("usuario__id") Integer id_usuario, @Query("plan__id") Integer id_plan);


    @GET("/api/estaapuntado/")
    Call<EstoyApuntadoList> obtenerUsuariosApuntados(@Query("plan__id") Integer id_plan);


    @Headers("{Content-Type: multipart/form-data}")
    @Multipart
    @POST("/api/planesadd/")
    Call<String> publicarPlan(@Part("titulo") RequestBody titulo,
                              @Part("localizacion") RequestBody localizacion,
                              @Part("ciudad")RequestBody ciudad,
                              @Part("foto\"; filename=\"image.jpg\"") RequestBody foto,
                              @Part("poblacion") RequestBody poblacion,
                              @Part("fecha") RequestBody fecha,
                              @Part("categoria") RequestBody categoria,
                              @Part("descripcion")RequestBody descripcion,
                              @Part("coordenadas")RequestBody coordenadas,
                              @Part("usuario")RequestBody usuario);


    @Headers("{Content-Type: application/json}")
    @POST("/api/plan/apuntarse/")
    Call<Apuntarse> apuntarse(@Body Apuntarse ap);


    @DELETE("/api/plan/borrarse/{id}/")
    Call<Apuntarse> borrarseDelPlan(@Path("id") Integer id);


}
