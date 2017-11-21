package ClassModel;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hypnos on 11/11/2017.
 */

public class Networks extends AppCompatActivity{
    private Activity activity;
    public Networks(Activity activity) {
        this.activity = activity;
    }
    public boolean verificaConexion(){
        boolean red = false;
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null){
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                red = true;
            }else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                red = true;
            }
        }else {
            red = false;
        }
        return red;
    }
}
