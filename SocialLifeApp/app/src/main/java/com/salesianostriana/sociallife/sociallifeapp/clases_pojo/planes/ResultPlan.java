package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.planes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesus Pallares on 18/05/2016.
 */
public class ResultPlan {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("localizacion")
    @Expose
    private String localizacion;
    @SerializedName("ciudad")
    @Expose
    private String ciudad;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("poblacion")
    @Expose
    private String poblacion;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("categoria")
    @Expose
    private Integer categoria;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("coordenadas")
    @Expose
    private String coordenadas;
    @SerializedName("usuario")
    @Expose
    private Integer usuario;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResultPlan() {
    }

    /**
     *
     * @param id
     * @param categoria
     * @param ciudad
     * @param titulo
     * @param fecha
     * @param poblacion
     * @param usuario
     * @param coordenadas
     * @param descripcion
     * @param localizacion
     * @param foto
     */
    public ResultPlan(Integer id, String titulo, String localizacion, String ciudad, String foto, String poblacion, String fecha, Integer categoria, String descripcion, String coordenadas, Integer usuario) {
        this.id = id;
        this.titulo = titulo;
        this.localizacion = localizacion;
        this.ciudad = ciudad;
        this.foto = foto;
        this.poblacion = poblacion;
        this.fecha = fecha;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.coordenadas = coordenadas;
        this.usuario = usuario;
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
     * The titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     *
     * @param titulo
     * The titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     *
     * @return
     * The localizacion
     */
    public String getLocalizacion() {
        return localizacion;
    }

    /**
     *
     * @param localizacion
     * The localizacion
     */
    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    /**
     *
     * @return
     * The ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     *
     * @param ciudad
     * The ciudad
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     *
     * @return
     * The foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     *
     * @param foto
     * The foto
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     *
     * @return
     * The poblacion
     */
    public String getPoblacion() {
        return poblacion;
    }

    /**
     *
     * @param poblacion
     * The poblacion
     */
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
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

    /**
     *
     * @return
     * The categoria
     */
    public Integer getCategoria() {
        return categoria;
    }

    /**
     *
     * @param categoria
     * The categoria
     */
    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    /**
     *
     * @return
     * The descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param descripcion
     * The descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     * The coordenadas
     */
    public String getCoordenadas() {
        return coordenadas;
    }

    /**
     *
     * @param coordenadas
     * The coordenadas
     */
    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    /**
     *
     * @return
     * The usuario
     */
    public Integer getUsuario() {
        return usuario;
    }

    /**
     *
     * @param usuario
     * The usuario
     */
    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

}
