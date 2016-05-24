package com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_estoy_apuntado;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.planes.PlanCompleto;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.UsuarioCompleto;

/**
 * Created by Jesus Pallares on 17/05/2016.
 */
public class EstoyApuntado {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("usuario")
        @Expose
        private UsuarioCompleto usuario;
        @SerializedName("plan")
        @Expose
        private PlanCompleto plan;
        @SerializedName("fecha")
        @Expose
        private String fecha;

        /**
         * No args constructor for use in serialization
         *
         */
        public EstoyApuntado() {
        }

        /**
         *
         * @param id
         * @param fecha
         * @param plan
         * @param usuario
         */
        public EstoyApuntado(Integer id, UsuarioCompleto usuario, PlanCompleto plan, String fecha) {
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
        public UsuarioCompleto getUsuario() {
            return usuario;
        }

        /**
         *
         * @param usuario
         * The usuario
         */
        public void setUsuario(UsuarioCompleto usuario) {
            this.usuario = usuario;
        }

        /**
         *
         * @return
         * The plan
         */
        public PlanCompleto getPlan() {
            return plan;
        }

        /**
         *
         * @param plan
         * The plan
         */
        public void setPlan(PlanCompleto plan) {
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
