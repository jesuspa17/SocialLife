package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_seguidos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.UsuarioCompleto;

/**
 * Created by Jesus Pallares on 24/05/2016.
 */
public class NoSeguidos {


    @SerializedName("usuario_amigo")
    @Expose
    private UsuarioCompleto usuarioAmigo;


    /**
     * No args constructor for use in serialization
     */
    public NoSeguidos() {
    }


    public NoSeguidos(UsuarioCompleto usuarioAmigo) {
        this.usuarioAmigo = usuarioAmigo;

    }

    /**
     * @return The usuarioAmigo
     */
    public UsuarioCompleto getUsuarioAmigo() {
        return usuarioAmigo;
    }

    /**
     * @param usuarioAmigo The usuario_amigo
     */
    public void setUsuarioAmigo(UsuarioCompleto usuarioAmigo) {
        this.usuarioAmigo = usuarioAmigo;
    }


}
