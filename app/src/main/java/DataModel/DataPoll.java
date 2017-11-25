package DataModel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by Hypnos on 21/11/2017.
 */

public class DataPoll {
    private String Id;
    private String Titulo;
    private String Respuestas;
    private String Votos;
    private String Fecha;
    private String Imagen;
    private Bitmap Photo;

    public DataPoll(String id, String titulo, String respuestas, String votos, String fecha, String imagen) {
        Id = id;
        Titulo = titulo;
        Respuestas = respuestas;
        Votos = votos;
        Fecha = fecha;
        Imagen = imagen;

        try {
            byte[] byteData = Base64.decode(Imagen, Base64.DEFAULT);
            Photo = BitmapFactory.decodeByteArray(byteData, 0, byteData.length);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getId() {
        return Id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public String getRespuestas() {
        return Respuestas;
    }

    public String getVotos() {
        return Votos;
    }

    public String getFecha() {
        return Fecha;
    }

    public String getImage() {
        return Imagen;
    }

    public Bitmap getPhoto() {
        return Photo;
    }
}
