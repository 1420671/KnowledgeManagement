package pe.edu.utp.knowledgemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

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
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        final String res = sentPost(emailTextView.getText().toString(),
                                passwordTextView.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int r = objJSON(res);
                                if (r>0){
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getApplicationContext(),"Usuario o password incorrecto",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                };
                thread.start();
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

            Scanner scanner = new Scanner(connection.getInputStream());

            while (scanner.hasNextLine())
                answer+=(scanner.nextLine());
        } catch (Exception e) {
        }

        return answer.toString();
    }

    public int objJSON(String rspt){
        int res=0;
        try {
            JSONArray jsonArray = new JSONArray(rspt);
            if (jsonArray.length()>0){
                res=1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

}
