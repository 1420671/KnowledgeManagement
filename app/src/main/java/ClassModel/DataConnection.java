package ClassModel;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;

import DataModel.Datapoll;

/**
 * Created by Hypnos on 11/11/2017.
 */

public class DataConnection extends BaseVolley {
    private Context context;
    private String data1,data2,data3,data4,jsonData;
    JSONObject json_data;
    private Boolean run;
    Datapoll datapoll;
    ArrayList<Datapoll> listPoll = new ArrayList();
    public DataConnection(Context context) {
        super(context);
    }
}
