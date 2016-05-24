package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_listas;

/**
 * Created by Jesus Pallares on 30/04/2016.
 */
public class Comentario {

    private String contenido, usuario, fecha;

    public Comentario(){

    }

    public Comentario(String contenido, String usuario, String fecha) {
        this.contenido = contenido;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
