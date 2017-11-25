package com.example.usuario.login.practica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.usuario.login.R;
import com.example.usuario.login.bean.UsuarioBean;
import com.example.usuario.login.dao.UsuarioDAO;

import java.util.ArrayList;

public class UsuarioListaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    ListView lsv;
    Button btnNuevo;

    private void IniciaComponente(){
        lsv=(ListView)findViewById(R.id.lsvUsuarios);
        btnNuevo = (Button)findViewById(R.id.btnNuevo) ;
        btnNuevo.setOnClickListener(this);
        lsv.setOnItemClickListener(this);
    }

    private void CargaUsuario(){
        UsuarioDAO dao=new UsuarioDAO(this);
        ArrayList<UsuarioBean>usuarios=dao.Listado();
        ArrayAdapter<UsuarioBean>adapter=
                new ArrayAdapter<UsuarioBean>(this,
                        android.R.layout.simple_list_item_1,
                        usuarios);
        lsv.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_lista);
        IniciaComponente();
        CargaUsuario();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent ir = new Intent(this, UsuarioNuevoActivity.class);
        UsuarioBean bean = (UsuarioBean) lsv.getItemAtPosition(i);
        ir.putExtra("obj",bean);
        startActivity(ir);
    }

    @Override
    public void onClick(View view) {
        Intent ir = new Intent(this , UsuarioNuevoActivity.class);
        startActivity(ir);

    }
}

