package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_seguidos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.UsuarioCompleto;

/**
 * Created by Jesus Pallares on 22/05/2016.
 */
public class Seguido {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("usuario_principal")
    @Expose
    private Integer usuarioPrincipal;
    @SerializedName("usuario_amigo")
    @Expose
    private UsuarioCompleto usuarioAmigo;
    @SerializedName("fecha")
    @Expose
    private String fecha;

    /**
     * No args constructor for use in serialization
     *
     */
    public Seguido() {
    }

    /**
     *
     * @param id
     * @param fecha
     * @param usuarioAmigo
     * @param usuarioPrincipal
     */
    public Seguido(Integer id, Integer usuarioPrincipal, UsuarioCompleto usuarioAmigo, String fecha) {
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
    public UsuarioCompleto getUsuarioAmigo() {
        return usuarioAmigo;
    }

    /**
     *
     * @param usuarioAmigo
     * The usuario_amigo
     */
    public void setUsuarioAmigo(UsuarioCompleto usuarioAmigo) {
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