package com.salesianostriana.sociallife.sociallifeapp.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.activities.DetallesPlanActivity;
import com.salesianostriana.sociallife.sociallifeapp.activities.Preferencias;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.planes.PlanCompleto;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_estoy_apuntado.EstoyApuntadoList;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.PlanesApi;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

/**
 * Created by Jesus Pallares on 25/04/2016.
 */
public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitulo,txtFecha,txtUbicacion,txtAsistentes,txtCategoria,txtCreadoPor;
        ImageView imgUser,imgFondoPlan,marker,calendar;
        RelativeLayout fondoPlan;
        CardView cardViewPlanes;
        LinearLayout linear_inferior,linearFondoTransparente;
        View v;

        public ViewHolder(View v) {
            super(v);

            txtTitulo = (TextView) v.findViewById(R.id.textViewTitulo);
            txtFecha = (TextView) v.findViewById(R.id.textViewFecha);
            txtUbicacion = (TextView) v.findViewById(R.id.textViewUbicacion);
            txtAsistentes = (TextView) v.findViewById(R.id.textViewAsistentes);
            fondoPlan = (RelativeLayout) v.findViewById(R.id.fondoPlan);
            imgFondoPlan = (ImageView) v.findViewById(R.id.imageViewFondoPlan);
            txtCategoria = (TextView) v.findViewById(R.id.textViewCategoria);
            imgUser = (ImageView) v.findViewById(R.id.imageUserListaPlanes);
            txtCreadoPor = (TextView) v.findViewById(R.id.textViewCreadoPor);
            cardViewPlanes = (CardView) v.findViewById(R.id.card_view_planes);
            linear_inferior = (LinearLayout) v.findViewById(R.id.linear_inferior);
            marker =(ImageView) v.findViewById(R.id.imageViewMarkerListPlan);
            calendar = (ImageView) v.findViewById(R.id.imageViewCalendarListPlan);
            linearFondoTransparente = (LinearLayout) v.findViewById(R.id.linearFondoTransparente);
            this.v = v;
        }
    }

    private Context context;
    private List<PlanCompleto> lista_planes;
    private int tipo;


    public PlanAdapter(List<PlanCompleto> lista,  int tipo){
        this.lista_planes = lista;
        this.tipo = tipo;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(tipo==0 || tipo==2){
             v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_plan,parent,false);
        }else if(tipo==1){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_planes,parent,false);
        }
        this.context = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final PlanCompleto planActual = lista_planes.get(position);

        if(tipo == 2){

            holder.cardViewPlanes.getLayoutParams().height = 280;
            holder.linear_inferior.getLayoutParams().height=90;
            holder.txtTitulo.setTextSize(15);
            holder.txtCategoria.setVisibility(View.INVISIBLE);
            holder.txtUbicacion.setVisibility(View.INVISIBLE);
            holder.txtAsistentes.setTextSize(10);

            holder.txtCreadoPor.setTextSize(10);
            holder.txtCreadoPor.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);


            holder.txtAsistentes.getLayoutParams().width = 700;
            holder.txtAsistentes.setHeight(holder.linear_inferior.getLayoutParams().height);
            holder.txtAsistentes.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            holder.txtFecha.setVisibility(View.INVISIBLE);
            holder.marker.setVisibility(View.INVISIBLE);
            holder.calendar.setVisibility(View.INVISIBLE);
            holder.linearFondoTransparente.setBackgroundColor(Color.TRANSPARENT);

            holder.imgUser.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            holder.imgUser.getLayoutParams().width = 80;
            holder.imgUser.getLayoutParams().height=80;
            Picasso.with(context).load(R.drawable.marker_map).fit().error(R.drawable.marker_map)
                    .placeholder(R.drawable.marker).into(holder.imgUser);
            holder.txtCreadoPor.setText(planActual.getCiudad());
        }

        Picasso.with(context).load(planActual.getFoto()).placeholder(R.color.material_blue_grey_800).fit().into(holder.imgFondoPlan);

        if(holder.txtAsistentes!=null && holder.imgUser!=null && holder.txtCreadoPor!=null){

            new ObtenerApuntados() {
                @Override
                protected void onPostExecute(EstoyApuntadoList estoyApuntadoList) {
                    super.onPostExecute(estoyApuntadoList);
                    int num = estoyApuntadoList.getResults().size();
                    if(num == 0) holder.txtAsistentes.setText("Asitirán "+ String.valueOf(estoyApuntadoList.getResults().size()+" personas"));
                    if(num == 1) holder.txtAsistentes.setText("Asitirá "+ String.valueOf(estoyApuntadoList.getResults().size()+" persona"));
                    if(num > 1) holder.txtAsistentes.setText("Asitirán "+ String.valueOf(estoyApuntadoList.getResults().size()+" personas"));

                }
            }.execute(planActual.getId());

            if(tipo != 2){
                holder.txtCreadoPor.setText("Creado por " + planActual.getUsuario().getUsuario().getFirstName());
                Utils.cargarImagen(context, planActual.getUsuario().getFoto(), holder.imgUser);
            }


        }




        String[] fec = Utils.formatearFechaString(Utils.FORMATO_FECHA,planActual.getFecha()).split("/");
        holder.txtFecha.setText(Utils.getDiaSemana(planActual.getFecha()) + " " + fec[0] + " de " + fec[1].substring(0,1).toUpperCase()+fec[1].substring(1,fec[1].length()));

        holder.txtUbicacion.setText(planActual.getLocalizacion());
        holder.txtTitulo.setText(planActual.getTitulo().toUpperCase());
        holder.txtCategoria.setText(planActual.getCategoria().getNombreCategoria().toUpperCase());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetallesPlanActivity.class);
                i.putExtra("id_plan", planActual.getId());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return lista_planes.size();
    }

    private class ObtenerApuntados extends AsyncTask<Integer, Void, EstoyApuntadoList>{

        @Override
        protected EstoyApuntadoList doInBackground(Integer... params) {
            Call<EstoyApuntadoList> call = Servicio.instanciarServicio(PlanesApi.class, Preferencias.preferencias.getString("token",null)).obtenerUsuariosApuntados(params[0]);
            Response<EstoyApuntadoList> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res.body();
        }

    }
}
