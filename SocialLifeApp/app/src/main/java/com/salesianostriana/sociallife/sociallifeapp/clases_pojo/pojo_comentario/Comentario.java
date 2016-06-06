package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_comentario;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.UsuarioCompleto;

/**
 * Created by Jesus Pallares on 27/05/2016.
 */
public class Comentario {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("usuario")
    @Expose
    private UsuarioCompleto usuario;
    @SerializedName("plan")
    @Expose
    private Integer plan;
    @SerializedName("contenido")
    @Expose
    private String contenido;
    @SerializedName("fecha")
    @Expose
    private String fecha;

    /**
     * No args constructor for use in serialization
     *
     */
    public Comentario() {
    }

    /**
     *
     * @param id
     * @param fecha
     * @param plan
     * @param contenido
     * @param usuario
     */
    public Comentario(Integer id, UsuarioCompleto usuario, Integer plan, String contenido, String fecha) {
        this.id = id;
        this.usuario = usuario;
        this.plan = plan;
        this.contenido = contenido;
        this.fecha = fecha;
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
     * The usuario
     */
    public UsuarioCompleto getUsuario() {
        return usuario;
    }

    /**
     *
     * @param usuario
     * The usuario
     */
    public void setUsuario(UsuarioCompleto usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @return
     * The plan
     */
    public Integer getPlan() {
        return plan;
    }

    /**
     *
     * @param plan
     * The plan
     */
    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    /**
     *
     * @return
     * The contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     *
     * @param contenido
     * The contenido
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     *
     * @return
     * The fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     * The fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}