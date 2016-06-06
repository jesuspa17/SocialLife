package com.salesianostriana.sociallife.sociallifeapp.interfaces;

import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_categoria.Categoria;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Jesus Pallares on 27/05/2016.
 */
public interface CategoriasApi {

    @GET("/api/categorias/todas/")
    Call<List<Categoria>> obtenerCategorias();
    
}
