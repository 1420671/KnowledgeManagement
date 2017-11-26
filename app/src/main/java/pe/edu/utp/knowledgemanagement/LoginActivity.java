package pe.edu.utp.knowledgemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pe.edu.utp.knowledgemanagement.bean.UsuarioBean;
import pe.edu.utp.knowledgemanagement.dao.UsuarioDAO;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtEmail,edtPwd;
    Button btnLogin, btnRegistrar;


    private void iniciaComponente(){
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPwd = (EditText)findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegistrar = (Button) findViewById(R.id.btnNuevo);
        btnRegistrar.setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciaComponente();
    }

    @Override
    public void onClick(View view) {

        if (view == btnLogin){
            String email = edtEmail.getText().toString();
            String pwd = edtPwd.getText().toString();
            UsuarioDAO dao = new UsuarioDAO(this);
            UsuarioBean bean = dao.Login(email,pwd);

            if(bean == null){
                Toast.makeText(this,"No se encontro usuario", Toast.LENGTH_SHORT).show();
            }else{
                Intent ir = new Intent(this,MainActivity.class);
                startActivity(ir);
                /*Toast.makeText(this, "Si existe" , Toast.LENGTH_SHORT).show();*/
            }
        }
        if (view == btnRegistrar){

            Intent ir = new Intent(this , UsuarioNuevoActivity.class);
            startActivity(ir);

        }
    }
}
