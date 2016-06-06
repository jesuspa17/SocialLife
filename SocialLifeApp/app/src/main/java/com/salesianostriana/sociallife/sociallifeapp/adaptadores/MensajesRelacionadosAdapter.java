package com.salesianostriana.sociallife.sociallifeapp.adaptadores;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.Servicio;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Preferencias;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.pojo_mensajes.Mensaje;
import com.salesianostriana.sociallife.sociallifeapp.clases_pojo.users.UsuarioCompleto;
import com.salesianostriana.sociallife.sociallifeapp.interfaces.UsuariosApi;
import com.salesianostriana.sociallife.sociallifeapp.utiles.Utils;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

/**
 * Created by Jesus Pallares on 25/04/2016.
 */
public class MensajesRelacionadosAdapter extends RecyclerView.Adapter<MensajesRelacionadosAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNomUser, txtContenido, txtFechaRecibido;
        ImageView fotoUser;
        RelativeLayout relativeLayout;
        CardView cardView;
        View v;

        public ViewHolder(View v) {
            super(v);

            txtNomUser = (TextView) v.findViewById(R.id.textViewUsuarioMensajesRelacionados);
            txtContenido = (TextView) v.findViewById(R.id.textViewContenidoMensajesRelacionados);
            txtFechaRecibido = (TextView) v.findViewById(R.id.textViewFechaMensajesRelacionados);
            //fotoUser = (ImageView) v.findViewById(R.id.imageViewUserMensajeRelacionados);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeRelacionados);
            cardView = (CardView) v.findViewById(R.id.card_viewRelacionados);
            this.v = v;
        }
    }

    private Context context;
    public static List<Mensaje> lista_recibidos;


    public MensajesRelacionadosAdapter(List<Mensaje> lista_recibidos) {
        this.lista_recibidos = lista_recibidos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensajes_relacionados, parent, false);
        this.context = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.cardView.setVisibility(View.VISIBLE);

        final Mensaje msgActual = lista_recibidos.get(position);

        new ObtenerMisDatosTask() {
            @Override
            protected void onPostExecute(List<UsuarioCompleto> usuarioCompletos) {
                super.onPostExecute(usuarioCompletos);

                String username = usuarioCompletos.get(0).getUsuario().getUsername();

                Log.i("USERNAME_OBTENIDO", username);

                if (username.equals(msgActual.getUsuarioEmisor().getUsuario().getUsername())) {
                    holder.relativeLayout.setGravity(Gravity.LEFT);
                    holder.txtNomUser.setText(msgActual.getUsuarioReceptor().getUsuario().getUsername());

                }else if(!username.equals(msgActual.getUsuarioEmisor().getUsuario().getUsername())){
                    holder.relativeLayout.setGravity(Gravity.RIGHT);
                    holder.txtNomUser.setText(context.getResources().getString(R.string.yo));
                }

                if (username.equals(msgActual.getUsuarioReceptor().getUsuario().getUsername())) {

                    holder.relativeLayout.setGravity(Gravity.LEFT);
                    holder.txtNomUser.setText(msgActual.getUsuarioEmisor().getUsuario().getUsername());

                }else if(!username.equals(msgActual.getUsuarioReceptor().getUsuario().getUsername())){
                    holder.relativeLayout.setGravity(Gravity.RIGHT);
                    holder.txtNomUser.setText(context.getResources().getString(R.string.yo));
                }

            }
        }.execute();

        holder.txtContenido.setText(msgActual.getContenido());
        holder.txtFechaRecibido.setText(Utils.formatearFechaComentarios(Utils.FORMATO_FECHA, msgActual.getFecha()));

    }

    @Override
    public int getItemCount() {
        return lista_recibidos.size();
    }


    public class ObtenerMisDatosTask extends AsyncTask<String, Void, List<UsuarioCompleto>> {

        @Override
        protected List<UsuarioCompleto> doInBackground(String... params) {
            Call<List<UsuarioCompleto>> call = Servicio.instanciarServicio(UsuariosApi.class, Preferencias.preferencias.getString("token", null)).obtenerTodosMisDatos();
            Response<List<UsuarioCompleto>> res = null;
            try {
                res = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert res != null;
            return res.body();
        }

    }
}
