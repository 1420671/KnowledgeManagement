package ClassModel;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Hypnos on 21/11/2017.
 */

public class Volleys {
    private static Volleys mVolley = null;
    // este objeto es la cola que usara la aplicacion
    private RequestQueue mRequestQueue;

    public Volleys(Context context) {
        // crea una nueva solicitud de petecion de la clave que solicita la conexion
        mRequestQueue = Volley.newRequestQueue(context);
    }
    public static Volleys getInstance(Context context){
        if (mVolley == null){
            mVolley = new Volleys(context);

        }
        return mVolley;
    }
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}
