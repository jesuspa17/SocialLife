package com.salesianostriana.sociallife.sociallifeapp.interfaces;

import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_seguidos.Seguido;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_seguidos.SeguidosSimpleList;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Jesus Pallares on 16/05/2016.
 */
public interface SeguidosApi {

    @GET("/api/amistades/obtenerseguidos")
    Call<List<Seguido>> obtenerMisUsuariosSeguidos();


    @GET("/api/seguido")
    Call<SeguidosSimpleList> obtenerRelacionEntre2Usuarios(@Query("usuario_principal") Integer id_usuario_principal, @Query("usuario_amigo") Integer id_user_amigo);




}
