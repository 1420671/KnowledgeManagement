package pe.edu.utp.knowledgemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pe.edu.utp.knowledgemanagement.bean.UsuarioBean;
import pe.edu.utp.knowledgemanagement.dao.UsuarioDAO;

public class UsuarioNuevoActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtemail,edtpwd,edtnom,edttelef;
    Button btngraba;
    Button btnElimina;

    private void IniciaComponente(){
        edtemail=(EditText)findViewById(R.id.edtUsuarioEmail);
        edtpwd=(EditText)findViewById(R.id.edtUsuarioPwd);
        edtnom=(EditText)findViewById(R.id.edtUsuarioNombre);
        edttelef=(EditText)findViewById(R.id.edtUsuarioTelef);
        btngraba=(Button)findViewById(R.id.btnUsuarioGrabar);
        btnElimina=(Button)findViewById(R.id.btnEliminar);
        btnElimina.setOnClickListener(this);
        btngraba.setOnClickListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_nuevo);
        IniciaComponente();
        Intent rec = getIntent();
        if (rec.getSerializableExtra("obj")!=null){
            UsuarioBean bean = (UsuarioBean) rec.getSerializableExtra("obj");
            edtemail.setText(bean.getEmail());
            edtnom.setText(bean.getNombre());
            edtpwd.setText(bean.getPwd());
            edttelef.setText(bean.getTelef());
            edtemail.setEnabled(false);
            btngraba.setText("Modificar");
        }else{
            btngraba.setText("Grabar");
            btnElimina.setEnabled(false);
        }
    }
    @Override
    public void onClick(View view){
        if(view==btngraba){
            UsuarioBean bean=new UsuarioBean();
            UsuarioDAO dao=new UsuarioDAO(this);
            bean.setEmail(edtemail.getText().toString());
            bean.setPwd(edtpwd.getText().toString());
            bean.setNombre(edtnom.getText().toString());
            bean.setTelef(edttelef.getText().toString());
            String result;
            if(edtemail.isEnabled()){
                result=dao.Grabar(bean);
                Toast.makeText(this,result,Toast.LENGTH_LONG).show();
            }else
            {
                result = dao.Modificar(bean);
                Toast.makeText(this,result,Toast.LENGTH_LONG).show();

            }


            Intent ir=new Intent(this,UsuarioListaActivity.class);
            startActivity(ir);

        }
        if (view == btnElimina)
        {
            UsuarioDAO dao = new  UsuarioDAO(this);
            String result = dao.Eliminar(edtemail.getText().toString());

            Toast.makeText(this,result,Toast.LENGTH_LONG).show();
            Intent ir=new Intent(this,UsuarioListaActivity.class);
            startActivity(ir);
        }


    }
}
