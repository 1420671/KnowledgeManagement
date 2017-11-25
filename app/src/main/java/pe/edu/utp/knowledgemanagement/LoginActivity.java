package pe.edu.utp.knowledgemanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    TextView emailTextView;
    TextView passwordTextView;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTextView=(EditText)findViewById(R.id.emailEditText);
        passwordTextView=(EditText)findViewById(R.id.passwordEditText);
        loginButton=(Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public String sentPost(String email, String password){
        String parameter="email="+email+"&password="+password;
        HttpURLConnection connection=null;
        String answer="";

        URL url = null;
        try {
            url = new URL("");
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Lenght",""+Integer.toString(parameter.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(parameter);
            dataOutputStream.close();
        } catch (Exception e) {
        }

        return parameter;
    }

}
