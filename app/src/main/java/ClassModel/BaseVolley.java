package ClassModel;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

/**
 * Created by Hypnos on 21/11/2017.
 */

public class BaseVolley {
    private Volleys volley;
    protected RequestQueue fRequestQueue;
    private Context context;

    public BaseVolley(Context context) {
        this.context = context;
        // getAplicacionContext () - Devuelve el contexto de todas las actividares que se ejecutan en la aplicacion
        // Vamos a pedir una instancia del singleton Volleys que hemos creado y a inicializar la RequestQueue
        volley = Volleys.getInstance(context.getApplicationContext());
        fRequestQueue = volley.getRequestQueue();

    }
    public void onStop(){
        if (fRequestQueue != null){
            fRequestQueue.cancelAll(this);
        }

    }
    public void addToqueue(Request request){
        if (request != null){
            // Creamos una etiqueta a la solicitud
            request.setTag(this);
            if (fRequestQueue == null){
                fRequestQueue = volley.getRequestQueue();
            }
            //setRetryPolicy implementar como quiere volver a intentar una solicitud al servidor
            //60000 el tiempo de espera predeterminado socket en milisegundos
            // 3 el numero predeterminado de reintentos
            // DEFAULT_BACKOFF_MULT El multiplicador de retardo de envio por defecto
            request.setRetryPolicy(new DefaultRetryPolicy(6000, 3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            // Ahora ya podriamos añadir todas nuestras Requests a la cola usando;
            fRequestQueue.add(request);
        }
    }
}
