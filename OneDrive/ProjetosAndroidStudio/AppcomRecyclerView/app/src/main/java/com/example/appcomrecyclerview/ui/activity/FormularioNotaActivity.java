package com.example.appcomrecyclerview.ui.activity;

import static com.example.appcomrecyclerview.ui.activity.NotaActivityConstanties.CHAVE_NOTA;
import static com.example.appcomrecyclerview.ui.activity.NotaActivityConstanties.CHAVE_POSICAO;
import static com.example.appcomrecyclerview.ui.activity.NotaActivityConstanties.POSICAO_INVALIDA;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appcomrecyclerview.R;
import com.example.appcomrecyclerview.model.Nota;

public class FormularioNotaActivity extends AppCompatActivity {



    private int posicaoRecebida = POSICAO_INVALIDA;
    private TextView titulo;
    private TextView descrição;
    private final String TITULO_APP_BAR_ALTERA = "Altera Nota";
    private final String TITULO_APP_BAR_INSERE = "Altera Nota";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APP_BAR_INSERE);
        inicializaCampos();



       Intent dadosRecebidos =  getIntent();
       if (dadosRecebidos.hasExtra(CHAVE_NOTA)){
           setTitle(TITULO_APP_BAR_ALTERA);
           Nota notaRecebida = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
           posicaoRecebida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
           preencheCampos(notaRecebida);

       }

    }

    private void preencheCampos(Nota nota) {
        titulo.setText(nota.getTitulo());
        descrição.setText(nota.getDescrição());
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.textTituloFormulario);
        descrição = findViewById(R.id.textFormularioDescrição);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (ehMenuSalvaNota(item)) {

            Nota notaCriada = criaNota();
            retornaNota(notaCriada);
            finish();

        }

        return super.onOptionsItemSelected(item);

    }

    private void retornaNota(Nota nota) {
        Intent resultadoInserção = new Intent();
        resultadoInserção.putExtra(CHAVE_NOTA, nota);
        resultadoInserção.putExtra(CHAVE_POSICAO, posicaoRecebida);
        setResult(Activity.RESULT_OK, resultadoInserção);
    }

    @NonNull
    private Nota criaNota() {
        Nota notaCriada = new Nota(titulo.getText().toString(),
                descrição.getText().toString());
        return notaCriada;
    }

    private boolean ehMenuSalvaNota(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menuButtonSalvarNota;
    }

}