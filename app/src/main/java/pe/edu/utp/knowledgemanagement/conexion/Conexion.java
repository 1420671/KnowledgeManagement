package com.example.usuario.login.conexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Usuario on 22/11/2017.
 */

public class Conexion extends SQLiteOpenHelper {

    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,"bdLogin",factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String TABLA_USUARIO="create table usuario(email text primary key,pwd text,nombre text,telef text);";
        sqLiteDatabase.execSQL(TABLA_USUARIO);

        sqLiteDatabase.execSQL("insert into usuario values('aaa','aaa','Luis Rios','968355243');");

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

    }
}
