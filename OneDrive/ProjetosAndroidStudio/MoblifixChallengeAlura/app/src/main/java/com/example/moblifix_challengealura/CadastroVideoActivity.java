package com.example.moblifix_challengealura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CadastroVideoActivity extends AppCompatActivity {

    private EditText campoUrl, campoCategoria;
    private ImageView imagePreviewVideo;
    private Button buttonCategoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_video);
    }
}