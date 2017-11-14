package pe.edu.utp.knowledgemanagement;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.google.gson.Gson;

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
    private final int PICKER = 1;
    DataConnection conexion;
    String function, encodedImage = "", history;
    EditText encuesta;
    List<DataResponse> items;
    ImageView img;
    Gson gson = new Gson();
    MemoryData memoryData;
    MenuItem itemMenuItem;
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
        switch (view.getId()){
            case R.id.imageView_Img:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    permission();
                }else {
                    getImage();
                }
                break;
        }

    }


    private void getImage(){
       Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra("crop", "true");
        photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
        photoPickerIntent.putExtra("outputFormat", Bitmap.CompressFormat.PNG);
        startActivityForResult(photoPickerIntent, REQUEST_CODE_CROP_IMAGE);


    }


    public Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File file = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE);
            try{
                file.createNewFile();
                }catch (IOException e){}
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
                    selectedImage.compress(Bitmap.CompressFormat.PNG,100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
                }
            }
    }
    }

private void permission(){
    if ((ContextCompat.checkSelfPermission(CreatePoll.this,
            Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
    ||(ContextCompat.checkSelfPermission(CreatePoll.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){

        ActivityCompat.requestPermissions(CreatePoll.this, new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        },1);
    }
}
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode){
        case 1:
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getImage();
            }else {
                Toast.makeText(this ,"Es necesario conceder permiso", Toast.LENGTH_SHORT).show();
            }
            break;
    }
    }


}

