package ClassModel;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DataModel.DataPoll;

/**
 * Created by Hypnos on 11/11/2017.
 */

public class DataConnection extends BaseVolley {
    private Context context;
    private String data1, data2, data3, data4,jsonData;
    JSONObject json_data;
    DataPoll dataPoll;
    private int run = 0;
    ArrayList<DataPoll> listPoll = new ArrayList();
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
        StringRequest putRequest = new StringRequest(Request.Method.POST, url, (s)-> {

                try {
                    json_data = new JSONObject(s);
                    switch (data1){
                        case "newPoll":
                                jsonData = json_data.getString("Insert");
                                if (jsonData.equals("insert")){
                                    run = 1;
                                }else {
                                    run = 2;
                                    Toast.makeText(context, "El titulo ya existe",Toast.LENGTH_SHORT).show();
                                }
                            break;
                        case "getPoll":
                            JSONArray resultJSON = json_data.getJSONArray("results");
                            int count = resultJSON.length();
                            for (int i = 0; i < count; i++){
                                JSONObject jsonNode = resultJSON.getJSONObject(i);
                                String image = jsonNode.optString("Imagen").toString();
                                String titulo = jsonNode.optString("Titulo").toString();
                                String resp = jsonNode.getString("Respuestas").toString();
                                String votos = jsonNode.getString("Votos").toString();
                                String fecha = jsonNode.getString("Fecha").toString();
                                String id = jsonNode.optString("Id").toString();
                                dataPoll = new DataPoll(id, titulo, resp, votos, fecha, image);
                                listPoll.add(dataPoll);
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


        }, (volleyError)-> {

                Toast.makeText(context, volleyError.toString(), Toast.LENGTH_SHORT).show();


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
                        // aun no lo utilizo :( xk aun no termino de interectuar por postman
                   case "getPoll":
                        params.put("function", data1);
                        params.put("date", "");
                        break;
                        /*
                    case "getResponse":
                        params.put("function",data1);
                        params.put("idPoll",data2);
                        break;
                        */
                }
                return params;
            }

        };
        addToqueue(putRequest);
    }
    public int getData(){
        return run;
    }
    public ArrayList<DataPoll> getPoll(){
        return listPoll;
    }
}
