package com.salesianostriana.sociallife.sociallifeapp.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.activities.DetallesPlanActivity;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_estoy_apuntado.EstoyApuntado;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Jesus Pallares on 25/04/2016.
 */
public class EstoyApuntadoAdapter extends RecyclerView.Adapter<EstoyApuntadoAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitulo,txtFecha,txtUbicacion,txtCategoria,txtDia,txtCiudad;
        View v;

        public ViewHolder(View v) {
            super(v);

            txtTitulo = (TextView) v.findViewById(R.id.textViewTituloApuntado);
            txtFecha = (TextView) v.findViewById(R.id.textViewFecApuntado);
            txtUbicacion = (TextView) v.findViewById(R.id.textViewLocApuntado);
            txtCategoria = (TextView) v.findViewById(R.id.textViewCatApuntado);
            txtDia = (TextView) v.findViewById(R.id.textViewDiaApuntado);
            txtCiudad = (TextView) v.findViewById(R.id.textViewCiudadApuntado);

            this.v = v;
        }
    }

    private Context context;
    private List<EstoyApuntado> lista_planes;


    public EstoyApuntadoAdapter(List<EstoyApuntado> lista_planes){
        this.lista_planes = lista_planes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_estoy_apuntado,parent,false);
        this.context = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final EstoyApuntado planActual = lista_planes.get(position);


        String[] fec = Utils.formatearFechaString(Utils.FORMATO_FECHA,planActual.getPlan().getFecha()).split("/");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fecha_plan = sdf.parse(planActual.getPlan().getFecha());
            Date fecha_hoy = new Date();
            String fecha_hoy_formateada = sdf.format(fecha_hoy);

            Date fec_hoy_final = sdf.parse(fecha_hoy_formateada);

            if(fec_hoy_final.compareTo(fecha_plan)>0){
                holder.txtFecha.setPaintFlags(holder.txtFecha.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.txtUbicacion.setPaintFlags(holder.txtUbicacion.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.txtTitulo.setPaintFlags(holder.txtTitulo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.txtDia.setPaintFlags(holder.txtDia.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.txtFecha.setText(Utils.getDiaSemana(planActual.getPlan().getFecha()) + " " + fec[0] + " de " + fec[1].substring(0,1).toUpperCase()+fec[1].substring(1,fec[1].length()));
        holder.txtUbicacion.setText(planActual.getPlan().getLocalizacion());
        holder.txtTitulo.setText(planActual.getPlan().getTitulo());
        holder.txtCategoria.setText(planActual.getPlan().getCategoria().getNombreCategoria());
        holder.txtDia.setText(planActual.getPlan().getFecha().split("-")[2]);
        holder.txtCiudad.setText(planActual.getPlan().getCiudad());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetallesPlanActivity.class);
                i.putExtra("id_plan",planActual.getPlan().getId());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista_planes.size();
    }
}
