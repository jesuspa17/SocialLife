package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.apuntarse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesus Pallares on 21/05/2016.
 */
public class Apuntarse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("usuario")
    @Expose
    private Integer usuario;
    @SerializedName("plan")
    @Expose
    private Integer plan;
    @SerializedName("fecha")
    @Expose
    private String fecha;

    /**
     * No args constructor for use in serialization
     *
     */
    public Apuntarse() {
    }

    /**
     *
     * @param id
     * @param fecha
     * @param plan
     * @param usuario
     */
    public Apuntarse(Integer id, Integer usuario, Integer plan, String fecha) {
        this.id = id;
        this.usuario = usuario;
        this.plan = plan;
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
