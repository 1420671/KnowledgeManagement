package com.example.usuario.login.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.login.bean.UsuarioBean;
import com.example.usuario.login.conexion.Conexion;

import java.util.ArrayList;

public class UsuarioDAO {
    Context contexto;

    public UsuarioDAO(Context ctx){contexto=ctx;}
    public ArrayList<UsuarioBean>Listado(){
        ArrayList<UsuarioBean>usuarios=new ArrayList<UsuarioBean>();
        try{
            Conexion cn=new Conexion(contexto,null,null,1);
            SQLiteDatabase sql=cn.getReadableDatabase();
            Cursor cur=sql.rawQuery("select * from usuario",null);
            UsuarioBean bean;
            while(cur.moveToNext()){
                bean=new UsuarioBean();
                bean.setEmail(cur.getString(0));
                bean.setPwd(cur.getString(1));
                bean.setNombre(cur.getString(2));
                bean.setTelef(cur.getString(3));
                usuarios.add(bean);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return usuarios;
    }
    public String Grabar(UsuarioBean bean){
        String result="";
        try{
            Conexion cn=new Conexion(contexto,null,null,1);
            SQLiteDatabase sql=cn.getWritableDatabase();
            sql.execSQL("insert into usuario values('?','?','?','?')",
                    new Object[]{bean.getEmail(),bean.getPwd(),bean.getNombre(),bean.getTelef()});
            result="Registro Grabado";
        }catch(Exception e){
            result=e.getMessage();
        }
        return result;
    }

    public String Modificar(UsuarioBean bean){
        String result="";
        try{
            Conexion cn=new Conexion(contexto,null,null,1);
            SQLiteDatabase sql=cn.getWritableDatabase();
            sql.execSQL("update usuario set  pwd='?',nombre='?',telef='?' where email='?'",
                    new Object[]{

                            bean.getPwd(),
                            bean.getNombre(),
                            bean.getTelef(),
                            bean.getEmail()
                    });
            result="Registro Modificado";
        }catch(Exception e){
            result=e.getMessage();
        }
        return result;
    }

    public String Eliminar(String email){
        String result="";
        try{
            Conexion cn=new Conexion(contexto,null,null,1);
            SQLiteDatabase sql=cn.getWritableDatabase();
            sql.execSQL("delete from usuario where email='?'",
                    new Object[]{

                            email
                    });
            result="Registro Eliminado";
        }catch(Exception e){
            result=e.getMessage();
        }
        return result;
    }
    public UsuarioBean Login(String email, String pwd){
        UsuarioBean bean=null;
        try{
            Conexion cn = new Conexion(contexto,null,null,1);
            SQLiteDatabase sql = cn.getReadableDatabase();
            Cursor cur = sql.rawQuery("select * from usuario where email='?' and pwd='?'",new String[] {email,pwd});

            if(cur.moveToNext()){
                bean= new UsuarioBean();
                bean.setEmail(cur.getString(0));
                bean.setPwd(cur.getString(1));
                bean.setNombre(cur.getString(2));
                bean.setTelef(cur.getString(3));

            }

        }catch(Exception ex){
            ex.printStackTrace();
        }



        return bean;
    }

}
