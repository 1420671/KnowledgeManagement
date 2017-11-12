package pe.edu.utp.knowledgemanagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ClassModel.DataConnection;
import ClassModel.MemoryData;
import ClassModel.Networks;
import DataModel.DataResponse;


/**
 * Created by Hypnos on 11/11/2017.
 */

public class CreatePoll extends AppCompatActivity implements View.OnClickListener {
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

        encuesta = (EditText) findViewById(R.id.editText_Encuesta);

        img = (ImageView) findViewById(R.id.imageView_Img);

        img.setOnClickListener(this);
        fabn = (FloatingActionButton) findViewById(R.id.fab);
        fabn.setOnClickListener(this);

        recycler = (RecyclerView) findViewById(R.id.view);
        recycler.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

    }

    @Override
    public void onClick(View view) {

    }

    /**
    private void getImage(){
       Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra("crop","true");
        photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT,getTempUri());
        photoPickerIntent.putExtra("outputFormat",Bitmap.CompressFormat.PNG);
        startActivityForResult(photoPickerIntent, REQUEST_CODE_CROP_IMAGE);


    }


    public Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {
        if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
            File file = new File(Environment.getExternalStorageDirectory(),TEMP_PHOTO_FILE);
            try{
                file.createNewFile();
                }catch (IOException e){
        }
        return file;
    }else{
            return null;
        }
}

protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent){
    super.onActivityResult(requestCode, resultCode,imageReturnedIntent);
    switch (requestCode){
        case REQUEST_CODE_CROP_IMAGE:
            if (resultCode == RESULT_OK){
                if (imageReturnedIntent!=null){
                    File tempFile = getTempFile();
                    String filePath = Environment.getExternalStorageDirectory()
                            +"/"+TEMP_PHOTO_FILE;
                    selectedImage = BitmapFactory.decodeFile(filePath);
                    img.setImageBitmap(selectedImage);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    if (tempFile.exists()) tempFile.delete();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG,100,baos);
                    byte[] imageBytes = baos.toByteArray();
                    encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
                }
            }
    }
    }

    private class ACTION_PICK {
    }
 **/
}




