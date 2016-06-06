package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_mensajes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.UsuarioCompleto;

/**
 * Created by Jesus Pallares on 29/05/2016.
 */
public class Mensaje {

    @SerializedName("usuario_emisor")
    @Expose
    private UsuarioCompleto usuarioEmisor;
    @SerializedName("usuario_receptor")
    @Expose
    private UsuarioCompleto usuarioReceptor;
    @SerializedName("contenido")
    @Expose
    private String contenido;
    @SerializedName("fecha")
    @Expose
    private String fecha;

    /**
     * No args constructor for use in serialization
     */
    public Mensaje() {
    }

    /**
     * @param fecha
     * @param contenido
     * @param usuarioEmisor
     * @param usuarioReceptor
     */
    public Mensaje(UsuarioCompleto usuarioEmisor, UsuarioCompleto usuarioReceptor, String contenido, String fecha) {
        this.usuarioEmisor = usuarioEmisor;
        this.usuarioReceptor = usuarioReceptor;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    /**
     * @return The usuarioEmisor
     */
    public UsuarioCompleto getUsuarioEmisor() {
        return usuarioEmisor;
    }

    /**
     * @param usuarioEmisor The usuario_emisor
     */
    public void setUsuarioEmisor(UsuarioCompleto usuarioEmisor) {
        this.usuarioEmisor = usuarioEmisor;
    }

    /**
     * @return The usuarioReceptor
     */
    public UsuarioCompleto getUsuarioReceptor() {
        return usuarioReceptor;
    }

    /**
     * @param usuarioReceptor The usuario_receptor
     */
    public void setUsuarioReceptor(UsuarioCompleto usuarioReceptor) {
        this.usuarioReceptor = usuarioReceptor;
    }

    /**
     * @return The contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * @param contenido The contenido
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * @return The fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha The fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}