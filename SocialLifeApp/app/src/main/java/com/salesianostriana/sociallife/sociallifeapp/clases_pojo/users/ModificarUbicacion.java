package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesus Pallares on 19/05/2016.
 */
public class ModificarUbicacion {

    @SerializedName("ubicacion_actual")
    @Expose
    private String ubicacionActual;

    /**
     * No args constructor for use in serialization
     *
     */
    public ModificarUbicacion() {
    }

    /**
     *
     * @param ubicacionActual
     */
    public ModificarUbicacion(String ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    /**
     *
     * @return
     * The ubicacionActual
     */
    public String getUbicacionActual() {
        return ubicacionActual;
    }

    /**
     *
     * @param ubicacionActual
     * The ubicacion_actual
     */
    public void setUbicacionActual(String ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

}