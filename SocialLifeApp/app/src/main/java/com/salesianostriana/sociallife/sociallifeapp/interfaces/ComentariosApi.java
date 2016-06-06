package com.salesianostriana.sociallife.sociallifeapp.interfaces;

import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_comentario.Comentario;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_comentario.CuerpoComentario;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Jesus Pallares on 27/05/2016.
 */
public interface ComentariosApi {

    @GET("/api/comentarios/obtenercomentarios")
    Call<List<Comentario>> obtenerComentariosPlan(@Query("plan") Integer id_plan);

    @POST("/api/comentariosadd/")
    Call<CuerpoComentario> anyadirComentario (@Body CuerpoComentario cuerpoComentario);
}
