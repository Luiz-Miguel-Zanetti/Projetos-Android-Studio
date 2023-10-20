package com.example.moblifix_challengealura;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAddVideo;
    private Button buttonAcesseAgora, buttonCategoriaFrontEnd, buttonCategoriaProgramação, buttonCategoriaMobile, buttonCadastrar;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAddVideo = findViewById(R.id.fabAddVideo);
        fabAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CadastroVideoActivity.class);
                startActivity(intent  );

            }
        });

    }
}