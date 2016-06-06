package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_categoria;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesus Pallares on 16/05/2016.
 */
public class Categoria {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombre_categoria")
    @Expose
    private String nombreCategoria;

    /**
     * No args constructor for use in serialization
     *
     */
    public Categoria() {
    }

    /**
     *
     * @param id
     * @param nombreCategoria
     */
    public Categoria(Integer id, String nombreCategoria) {
        this.id = id;
        this.nombreCategoria = nombreCategoria;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The nombreCategoria
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    /**
     *
     * @param nombreCategoria
     * The nombre_categoria
     */
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

}