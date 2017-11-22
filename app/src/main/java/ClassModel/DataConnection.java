package ClassModel;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DataModel.Datapoll;

/**
 * Created by Hypnos on 11/11/2017.
 */

public class DataConnection extends BaseVolley {
    private Context context;
    private String data1, data2, data3, data4,jsonData;
    JSONObject json_data;
    private Boolean run;
    Datapoll datapoll;
    ArrayList<Datapoll> listPoll = new ArrayList();
    public DataConnection(Context context, String data1, String data2, String data3, String data4) {
        super(context);
        this.context = context;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.data4 = data4;
        makeRequest();
    }
    private void makeRequest(){
        String url = "http://joelitopqmz.pe.hu/WebService.php";
        StringRequest putRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    json_data = new JSONObject(s);
                    switch (data1){
                        case "newPoll":
                                jsonData = json_data.getString("Insert");
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                switch (data1){
                    case "newPoll":
                        params.put("function", data1);
                        params.put("title", data2);
                        params.put("response", data3);
                        params.put("image", data4);
                        break;
                }
                return params;
            }

        };
        addToqueue(putRequest);
    }
}
