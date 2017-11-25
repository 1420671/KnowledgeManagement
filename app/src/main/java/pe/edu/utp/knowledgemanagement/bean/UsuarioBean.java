package com.example.usuario.login.bean;

import java.io.Serializable;

/**
 * Created by Usuario on 22/11/2017.
 */

public class UsuarioBean implements Serializable {

    private String email;
    private String pwd;
    private String nombre;
    private String telef;

    public String toString(){
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelef() {
        return telef;
    }

    public void setTelef(String telef) {
        this.telef = telef;
    }
}
