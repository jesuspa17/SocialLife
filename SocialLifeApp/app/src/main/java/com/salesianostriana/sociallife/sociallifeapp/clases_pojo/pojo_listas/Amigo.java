package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_listas;

/**
 * Created by Jesus Pallares on 03/05/2016.
 */
public class Amigo {

    private String nombre;
    private int foto;

    public Amigo(String nombre){
        this.nombre = nombre;
    }

    public Amigo(String nombre, int foto) {
        this.nombre = nombre;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
