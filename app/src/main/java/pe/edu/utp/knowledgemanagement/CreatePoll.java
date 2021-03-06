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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ClassModel.AdapteResponse;
import ClassModel.DataConnection;
import ClassModel.MemoryData;
import ClassModel.Networks;
import ClassModel.TextChanged;
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
    private ProgressBar progressBar;


    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_poll);

        encuesta = (EditText) findViewById(R.id.editText_Encuesta);
        encuesta.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        encuesta.addTextChangedListener(new TextChanged(this, encuesta));

        img = (ImageView) findViewById(R.id.imageView_Img);
        img.setOnClickListener(this);

        fabn = (FloatingActionButton) findViewById(R.id.fab);
        fabn.setOnClickListener(this);

        recycler = (RecyclerView) findViewById(R.id.view);
        recycler.setHasFixedSize(true);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        if (savedInstanceState != null){
            selectedImage = savedInstanceState.getParcelable("image");
            if (selectedImage != null){
                img.setImageBitmap(selectedImage);
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100,baos);
                byte[] imageByte = baos.toByteArray();
                encodedImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
            }else {
                img.setImageResource(R.drawable.ic_photo_camera_white);
            }
        }
        valor[0] = 1;
        function = "newPoll";
        items = new ArrayList();
        val = new ArrayList();
        networks = new Networks(this);
        memoryData = MemoryData.getInstance(this);
        history = memoryData.getData("createHistoryPoll");
        if (!history.equalsIgnoreCase("")){
            Type typeItem = new TypeToken<List<DataResponse>>(){}.getType();
            items = gson.fromJson(history, typeItem);
            //Toast.makeText(this, " "+items.get(0).getResponse() , Toast.LENGTH_LONG).show();
        }
        recyclerView();
    }
    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState){
        saveInstanceState.putParcelable("image", selectedImage);
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu2 = menu;
        getMenuInflater().inflate(R.menu.main, menu);
        if (selectedImage == null){
            itemMenuItem = menu.findItem(R.id.action_clear);
            itemMenuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            img.setImageResource(R.drawable.ic_photo_camera_white);
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            itemMenuItem = menu2.findItem(R.id.action_clear);
            itemMenuItem.setVisible(false);
            encodedImage = "";
            selectedImage = null;
            return true;
        }
        if (id == R.id.action_send){
            sendPoll();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            case R.id.fab:
                alert("addNew",0);
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
                    itemMenuItem = menu2.findItem(R.id.action_clear);
                    itemMenuItem.setVisible(true);
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
    private void alert(final String response, final int position){
        if (0 == items.size()){
            numberPickerDialog();

        }else{
            AlertDialog.Builder alerta;
            alerta = new AlertDialog.Builder(this);
            alerta.setIcon(R.drawable.ic_info_outline_black);
            alerta.setTitle("¿Que accion desea realizar?");
            if (!response.equalsIgnoreCase("")){
                alerta.setNegativeButton("Eliminar", (dialog,  which)-> {

                        items.remove(position);
                        history = gson.toJson(items);
                        memoryData.saveData("createHistoryPoll", history);
                        recyclerView();

                });
                alerta.setNeutralButton("Cancelar", (dialog,  which)->{
                });
                alerta.setPositiveButton("Editar",  (dialog,  which)->{
                        response(response, position, "edit");

                });

            }
            if (response.equals("addNew")){
                alerta.setNegativeButton("Agregar",  (dialog,  which)->{
                        response("", 0, "");

                });
                alerta.setNeutralButton("Cancelar",  (dialog,  which)->{


                });
                                alerta.setPositiveButton("Nuevo",  (dialog,  which)->{
                        memoryData.saveData("createHistoryPoll", "");
                        int count = items.size();
                        if (count > 0){
                            do {
                                items.remove(0);
                            }while (0 < items.size());
                            valor[0] = 0;
                            recyclerView();
                        }

                });
            }
            alerta.show();
        }
    }
    private void numberPickerDialog(){
        NumberPicker myNumberPicker = new NumberPicker(this);
        myNumberPicker.setMaxValue(5);
        myNumberPicker.setMinValue(1);
        NumberPicker.OnValueChangeListener myValChangedListener = (picker, oldVal, newVal)-> {

                resInt[0] = newVal;

        };
        myNumberPicker.setOnValueChangedListener(myValChangedListener);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(myNumberPicker);
        builder.setTitle("Cantidad de Answer ")
                .setIcon(R.drawable.ic_question_answer_black);
        builder.setPositiveButton(android.R.string.ok, (dialog,  which)->{

                //int res = res[0];
                ejecutar();

        });
        builder.setNegativeButton(android.R.string.cancel, (dialog,  which)->{
                //recyclerView();
                resInt[0] = 0;


        });
        builder.show();
    }
    public void ejecutar(){
        if (resInt[0] != 0){
            for (int i = 0; i < resInt[0]; i++){
                 response("",0,"");
            }
            resInt[0]=0;
        }
    }
    private void response(final String res, final int position, final String edit){
        final String[] dato = new String[1];
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        editText.addTextChangedListener(new TextChanged(this, editText));
        if (!res.equalsIgnoreCase("")){
            editText.setText(res);
        }
        editText.setHint("Respuesta");
        // Establece la lista de filtros de entrada que se usara si el bufer es Editable
        editText.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(20)});
        final AlertDialog d = new AlertDialog.Builder(this)
                .setView(editText)
                .setIcon(R.drawable.ic_question_answer_black)
                .setTitle("Ingrese las respuestas")
                .setPositiveButton(android.R.string.ok, (dialog,  which)->{
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        d.setOnShowListener((dialog)->{

                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener((v)->{

                        dato[0] = editText.getText().toString();
                        if (dato[0].equals("")){
                            Toast.makeText(CreatePoll.this, "Ingrese la respuesta", Toast.LENGTH_LONG).show();
                            editText.requestFocus();
                        }else
                            d.dismiss();
                        if (edit.equals("edit")){
                            valor[0] = items.get(position).getNumber();
                            items.remove(position);
                            items.add(position, new DataResponse(dato[0],"", valor[0]));
                            history = gson.toJson(items);
                            memoryData.saveData("createHistoryPoll", history);

                        }else{
                            if (history.equals("")){
                                items.add(new DataResponse(dato[0], "", valor[0]));
                                history = gson.toJson(items);
                                memoryData.saveData("createHistoryPoll", history);

                            }else{
                                if (5 > items.size()){
                                    items.add(new DataResponse(dato[0], "", valor[0]));
                                    history = gson.toJson(items);
                                    memoryData.saveData("createHistoryPoll", history);
                                }else{
                                    Toast.makeText(CreatePoll.this, "Solo se admiten 5 respuestas", Toast.LENGTH_LONG).show();
                                }

                            }
                            valor[0]++;
                        }
                        recyclerView();


                });

        });
                d.show();
    }
    private void recyclerView(){
        history = memoryData.getData("createHistoryPoll");
        if (history.equals("")){
            items = new ArrayList();
        }else {
            Type typeItem = new TypeToken<List<DataResponse>>(){
                }.getType();
            items = gson.fromJson(history, typeItem);
        }
        recycler.setAdapter(new AdapteResponse(items, (v,  position, checkBox)->{

                String res = items.get(position).getResponse();
                alert(res, position);


        }));
    }
    private void sendPoll(){
        String encuest = encuesta.getText().toString();
        if (encodedImage.equals("")){
            Toast.makeText(this, "Seleccione una imagen para el cuestionario", Toast.LENGTH_SHORT).show();
        }else {
            if (encuest.equals("")){
                Toast.makeText(this, "Ingrese el titulo para el cuestionario", Toast.LENGTH_LONG).show();
                encuesta.requestFocus();
            }else {
                if (0 == items.size()){
                    Toast.makeText(this, "Ingrese las respuestas", Toast.LENGTH_SHORT).show();
                }else {
                    history = memoryData.getData("createHistoryPoll");
                    if (!history.equalsIgnoreCase("")){
                        network = networks.verificaConexion();
                        if (network == true){
                            conexion = new DataConnection(this, function, encuest, history, encodedImage);
                            ejecutarClear();
                            progressBar.setVisibility(ProgressBar.VISIBLE);

                        }else {
                            Toast.makeText(this, "Comprueba tu conexion a Internet.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        }
    }
    private void ejecutarClear(){
        final boolean[] run = {true};
        final int[] pStatus = {0};
        new Thread(()-> {

                while (run[0]){
                    handler.post(()-> {

                            pStatus[0] =  conexion.getData();
                            if (pStatus[0] == 1){
                                clear();
                                run[0] = false;
                            }
                            if (pStatus[0] == 2){
                                progressBar.setVisibility(ProgressBar.INVISIBLE);

                            }

                    });
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        }).start();
    }
    private void clear(){
        img.setImageResource(R.drawable.ic_photo_camera_white);
        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        itemMenuItem = menu2.findItem(R.id.action_clear);
        itemMenuItem.setVisible(false);
        encodedImage = null;
        encuesta.setText("");
        memoryData.saveData("createHistoryPoll","");
        int count = items.size();
        if (count > 0){
            do {
                items.remove(0);
            }while (0 < items.size());
            valor[0]=0;
            recyclerView();
        }
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}

