package com.example.moblifix_challengealura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.moblifix_challengealura.R;

public class EditarVideoActivity extends AppCompatActivity {

    private EditText campoEditarUrl, campoEditarCategoria;
    private Button buttonAlterar, buttonRemover;
    private ImageView imagePreviewImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_video);
    }
}  