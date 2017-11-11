package pe.edu.utp.knowledgemanagement;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ClassModel.DataConnection;
import ClassModel.MemoryData;
import ClassModel.Networks;
import DataModel.DataResponse;


/**
 * Created by Hypnos on 11/11/2017.
 */

public class CreatePoll extends AppCompatActivity {
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private static final String TEMP_PHOTO_FILE = "temporary_img.png";
    Button numberPicker;
    Bitmap selectedImage;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    final int[] resInt= new int[1];
    final int[] valor = new int[1];
    private final int PICKEAR = 1;
    DataConnection conexion;
    String function, encodedImage = "", history;
    EditText encuesta;
    List<DataResponse> items;
    ImageView img;
    Gson gson = new Gson();
    MemoryData memoryData;
    MenuItem itemMenuitem;
    Menu menu2;
    Networks networks;
    boolean network;
    Handler handler = new Handler();
    ArrayList<String> val;
    FloatingActionButton fabn;


    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_poll);

    }
}

