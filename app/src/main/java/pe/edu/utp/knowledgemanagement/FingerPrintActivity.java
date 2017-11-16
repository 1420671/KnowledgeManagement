package pe.edu.utp.knowledgemanagement;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.security.KeyStore;

import javax.crypto.Cipher;

public class FingerPrintActivity extends AppCompatActivity {
    private KeyStore keyStore;
    private static final String KEY_NAME = "androidHive";
    private Cipher cipher;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

    }

}
