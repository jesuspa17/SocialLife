package com.salesianostriana.sociallife.sociallifeapp.utiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.salesianostriana.sociallife.sociallifeapp.R;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jesus Pallares on 11/05/2016.
 */
public class Utils {

    public static String FORMATO_FECHA = "yyyy-MM-dd";

    public static String formatearFechaString(String formato, String fecha) {
        //formato de entrada
        SimpleDateFormat f = new SimpleDateFormat(formato);
        //formato de salida
        SimpleDateFormat f1 = new SimpleDateFormat("dd/MMMM/yyyy");
        String fecha_formateada = "";
        try {
            Date date = f.parse(fecha);
            fecha_formateada = f1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fecha_formateada;
    }


    public static String fechaHoyDjango(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }


    public static String fechaFormateada(String fecha){
        String fec = Utils.formatearFechaString(Utils.FORMATO_FECHA,fecha);
        String[] f_split = fec.split("/");
        return f_split[0] + " de "+f_split[1].substring(0,1).toUpperCase()+f_split[1].substring(1,f_split[1].length())+" de "+f_split[2];
    }


    /**
     * Devuelve el día de la semana según la fecha que se le pase.
     * @param fecha
     * @return
     */
    public static String getDiaSemana(String fecha) {
        String valor_dia = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaActual = null;
        try {
            fechaActual = df.parse(fecha);
        } catch (ParseException e) {
            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(fechaActual);
        int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK);

        Log.i("DIA_SEMANA",""+diaSemana);

        if (diaSemana == 1) {
            valor_dia = "Domingo";
        } else if (diaSemana == 2) {
            valor_dia = "Lunes";
        } else if (diaSemana == 3) {
            valor_dia = "Martes";
        } else if (diaSemana == 4) {
            valor_dia = "Miercoles";
        } else if (diaSemana == 5) {
            valor_dia = "Jueves";
        } else if (diaSemana == 6) {
            valor_dia = "Viernes";
        } else if (diaSemana == 7) {
            valor_dia = "Sabado";
        }
        return valor_dia;
    }


    public static void cargarImagen(Context context, String path, ImageView img){
        Picasso.with(context).load(path).resize(120,120).error(R.drawable.logo)
                .transform(new RoundedTransformation(70, 0))
                .placeholder(R.drawable.logo).into(img);

    }


    public static int REQUEST_GENERIC_CODE = 1;
    public static AutocompleteFilter filtro_regiones = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS).build();
    public static AutocompleteFilter filtro_direcciones = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS).build();

    //Este método ahora mismo solo vale para filtrar por region, se le debe pasar el tipo de filtro, que están definidos arriba.
    public static void abrirBuscador(int code, Activity ac, AutocompleteFilter af){
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(af)
                    .build(ac);
            ac.startActivityForResult(intent, code);
        } catch (GooglePlayServicesRepairableException e) {

            GoogleApiAvailability.getInstance().getErrorDialog(ac, e.getConnectionStatusCode(),
                    0).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            String message = "En estos momentos, no están disponibles los servicios de GooglePlay" +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
            Toast.makeText(ac, message, Toast.LENGTH_SHORT).show();
        }

    }

    public static void abrirBuscadorEnFragment(int code, Fragment f, AutocompleteFilter af){
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(af)
                    .build(f.getActivity());
            f.startActivityForResult(intent, code);
        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance().getErrorDialog(f.getActivity(), e.getConnectionStatusCode(),
                    0).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            String message = "En estos momentos, no están disponibles los servicios de GooglePlay" +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
            Toast.makeText(f.getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    public static RequestBody createStringRequest(String dato) {
        RequestBody request = RequestBody.create(MediaType.parse("multipart/form-data"), dato);
        return request;
    }



}
