package com.salesianostriana.sociallife.sociallifeapp.interfaces;

import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_mensajes.Mensaje;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_mensajes.NuevoMensaje;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Jesus Pallares on 29/05/2016.
 */

public interface MensajesApi {

    @Headers("{Content-Type: application/json}")
    @POST("/api/mensajesadd/")
    Call<NuevoMensaje> enviarMensajeA(@Body NuevoMensaje nuevoMensaje);


    @GET("/api/mensajes/recibidos/")
    Call<List<Mensaje>> obtenerRecibidos();

    @GET("/api/mensajes/enviados/")
    Call<List<Mensaje>> obtenerEnviados();


    @GET("/api/mensajes/mensajesrelacionados/")
    Call<List<Mensaje>> obtenerMensajesRelacionados(@Query("usuario_emisor__id") Integer id_principal, @Query("usuario_receptor__id") Integer id_amigo);

}
