package ClassModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hypnos on 11/11/2017.
 */

public class MemoryData extends AppCompatActivity {
    private static MemoryData yourPreference;
    private SharedPreferences sharedPreferences;

    public MemoryData(Context context) {
        sharedPreferences = context.getSharedPreferences("CuestionarioGC",Context.MODE_PRIVATE);
    }

    public static MemoryData getInstance(Context context){
        if (yourPreference == null){
            yourPreference = new MemoryData(context);
        }
        return yourPreference;
    }
    public void saveData(String key, String value){
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }
    public String getData(String key){
        if (sharedPreferences != null){
            return sharedPreferences.getString(key, "");
        }
        return "";
    }
}
