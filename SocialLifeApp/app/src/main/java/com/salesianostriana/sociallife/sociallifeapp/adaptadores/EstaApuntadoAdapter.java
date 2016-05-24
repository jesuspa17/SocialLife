package com.salesianostriana.sociallife.sociallifeapp.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.activities.DetallesPlanActivity;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.planes.PlanCompleto;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_estoy_apuntado.EstoyApuntadoList;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;

/**
 * Created by Jesus Pallares on 25/04/2016.
 */
public class EstaApuntadoAdapter extends RecyclerView.Adapter<EstaApuntadoAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitulo,txtFecha,txtUbicacion;
        View v;

        public ViewHolder(View v) {
            super(v);

            txtTitulo = (TextView) v.findViewById(R.id.txtTituloEstaApuntado);
            txtFecha = (TextView) v.findViewById(R.id.txtFechaEstaApuntado);
            txtUbicacion = (TextView) v.findViewById(R.id.txtLocalizacionEstaApuntado);

            this.v = v;
        }
    }

    private Context context;
    private EstoyApuntadoList lista_planes;


    public EstaApuntadoAdapter(EstoyApuntadoList lista_planes){
        this.lista_planes = lista_planes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_esta_apuntado,parent,false);
        this.context = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final PlanCompleto planActual = lista_planes.getResults().get(position).getPlan();

        holder.txtUbicacion.setText(planActual.getLocalizacion());
        String[] fec = Utils.formatearFechaString(Utils.FORMATO_FECHA, planActual.getFecha()).split("/");
        holder.txtFecha.setText(Utils.getDiaSemana(planActual.getFecha()) + " " + fec[0] + " de " + fec[1].substring(0,1).toUpperCase()+fec[1].substring(1,fec[1].length()));
        holder.txtTitulo.setText(planActual.getTitulo().toUpperCase());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetallesPlanActivity.class);
                i.putExtra("id_plan",planActual.getId());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return lista_planes.getResults().size();
    }
}
