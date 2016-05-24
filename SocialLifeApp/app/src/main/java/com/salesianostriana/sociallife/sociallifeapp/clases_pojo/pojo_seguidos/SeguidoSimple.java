package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_seguidos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesus Pallares on 22/05/2016.
 */
public class SeguidoSimple {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("usuario_principal")
    @Expose
    private Integer usuarioPrincipal;
    @SerializedName("usuario_amigo")
    @Expose
    private Integer usuarioAmigo;
    @SerializedName("fecha")
    @Expose
    private String fecha;

    /**
     * No args constructor for use in serialization
     *
     */
    public SeguidoSimple() {
    }

    /**
     *
     * @param id
     * @param fecha
     * @param usuarioAmigo
     * @param usuarioPrincipal
     */
    public SeguidoSimple(Integer id, Integer usuarioPrincipal, Integer usuarioAmigo, String fecha) {
        this.id = id;
        this.usuarioPrincipal = usuarioPrincipal;
        this.usuarioAmigo = usuarioAmigo;
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
     * The usuarioPrincipal
     */
    public Integer getUsuarioPrincipal() {
        return usuarioPrincipal;
    }

    /**
     *
     * @param usuarioPrincipal
     * The usuario_principal
     */
    public void setUsuarioPrincipal(Integer usuarioPrincipal) {
        this.usuarioPrincipal = usuarioPrincipal;
    }

    /**
     *
     * @return
     * The usuarioAmigo
     */
    public Integer getUsuarioAmigo() {
        return usuarioAmigo;
    }

    /**
     *
     * @param usuarioAmigo
     * The usuario_amigo
     */
    public void setUsuarioAmigo(Integer usuarioAmigo) {
        this.usuarioAmigo = usuarioAmigo;
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