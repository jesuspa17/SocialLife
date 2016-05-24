package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesus Pallares on 11/05/2016.
 */
public class UsuarioCompleto {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("usuario")
    @Expose
    private ResultUsuario usuario;
    @SerializedName("poblacion")
    @Expose
    private String poblacion;
    @SerializedName("ciudad")
    @Expose
    private String ciudad;
    @SerializedName("biografia")
    @Expose
    private String biografia;
    @SerializedName("ubicacion_actual")
    @Expose
    private String ubicacion_actual;
    @SerializedName("foto")
    @Expose
    private String foto;

    /**
     * No args constructor for use in serialization
     *
     */
    public UsuarioCompleto() {
    }

    /**
     *
     * @param id
     * @param ciudad
     * @param poblacion
     * @param usuario
     * @param foto
     * @param biografia
     */
    public UsuarioCompleto(Integer id, ResultUsuario usuario, String poblacion, String ciudad, String biografia, String foto) {
        this.id = id;
        this.usuario = usuario;
        this.poblacion = poblacion;
        this.ciudad = ciudad;
        this.biografia = biografia;
        this.foto = foto;
    }

    public UsuarioCompleto(Integer id, ResultUsuario usuario, String poblacion, String ciudad, String biografia, String ubicacion_actual, String foto) {
        this.id = id;
        this.usuario = usuario;
        this.poblacion = poblacion;
        this.ciudad = ciudad;
        this.biografia = biografia;
        this.ubicacion_actual = ubicacion_actual;
        this.foto = foto;
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
    public ResultUsuario getUsuario() {
        return usuario;
    }

    /**
     *
     * @param usuario
     * The usuario
     */
    public void setUsuario(ResultUsuario usuario) {
        this.usuario = usuario;
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
     * The biografia
     */
    public String getBiografia() {
        return biografia;
    }

    /**
     *
     * @param biografia
     * The biografia
     */
    public void setBiografia(String biografia) {
        this.biografia = biografia;
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


    public String getUbicacion_actual() {
        return ubicacion_actual;
    }

    public void setUbicacion_actual(String ubicacion_actual) {
        this.ubicacion_actual = ubicacion_actual;
    }
}