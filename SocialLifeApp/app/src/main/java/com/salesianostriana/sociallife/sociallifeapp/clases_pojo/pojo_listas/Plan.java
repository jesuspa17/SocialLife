package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_listas;

/**
 * Created by Jesus Pallares on 25/04/2016.
 */
public class Plan {

    private String titulo;
    private String ubicacion;
    private String categoria;
    private String fecha;
    private int  num_asistentes;
    private int foto;
    private String dia;


    public Plan(){

    }

    public Plan(String titulo, String ubicacion, String categoria, String fecha, int num_asistentes, int foto) {
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.categoria = categoria;
        this.fecha = fecha;
        this.num_asistentes = num_asistentes;
        this.foto = foto;
    }

    public Plan(String titulo, String ubicacion, String fecha, int num_asistentes) {
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.num_asistentes = num_asistentes;
    }

    public Plan(String titulo, String ubicacion, String fecha,String categoria, String dia){
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.categoria = categoria;
        this.dia = dia;
    }

    public Plan(String titulo, String ubicacion, String fecha){
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
    }

    public Plan(String titulo, String ubicacion, String fecha, int num_asistentes, int foto) {
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.num_asistentes = num_asistentes;
        this.foto = foto;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public int getNum_asistentes() {
        return num_asistentes;
    }

    public void setNum_asistentes(int num_asistentes) {
        this.num_asistentes = num_asistentes;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
