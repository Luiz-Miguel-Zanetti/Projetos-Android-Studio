package com.example.appcomrecyclerview.ui.activity;

import static com.example.appcomrecyclerview.ui.activity.NotaActivityConstanties.CHAVE_NOTA;
import static com.example.appcomrecyclerview.ui.activity.NotaActivityConstanties.CHAVE_POSICAO;
import static com.example.appcomrecyclerview.ui.activity.NotaActivityConstanties.CÓDIGO_REQUISIÇÃO_ALTERA_NOTA;
import static com.example.appcomrecyclerview.ui.activity.NotaActivityConstanties.CÓDIGO_REQUISIÇÃO_INSERE_NOTA;
import static com.example.appcomrecyclerview.ui.activity.NotaActivityConstanties.POSICAO_INVALIDA;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcomrecyclerview.R;
import com.example.appcomrecyclerview.dao.NotaDAO;
import com.example.appcomrecyclerview.model.Nota;
import com.example.appcomrecyclerview.ui.recyclerview.adapter.ListaNotasAdapter;
import com.example.appcomrecyclerview.ui.recyclerview.adapter.listener.OnItemClickListener;
import com.example.appcomrecyclerview.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback;

import java.util.List;

public class ListaNotasActivity extends AppCompatActivity {


    private ListaNotasAdapter adapter;
    private List<Nota> todasNotas;
    private final String TITULO_APP_BAR = "Ceep";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle(TITULO_APP_BAR);
        List<Nota> todasNotas = pegaTodasNotas();
        configuraRecyclerView(todasNotas);

        configuraBotãoInsereNota();


    }

    private void configuraBotãoInsereNota() {
        TextView buttonInserirNota = findViewById(R.id.botãoInsereNota);
        buttonInserirNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormularioNotaActivityInsere();
            }
        });
    }

    private void vaiParaFormularioNotaActivityInsere() {
        Intent intent = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        startActivityForResult(intent, CÓDIGO_REQUISIÇÃO_INSERE_NOTA);
    }

    private List<Nota> pegaTodasNotas() {
        NotaDAO dao = new NotaDAO();
        List<Nota> todasNotas = dao.todos();
        return todasNotas;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (ehResultadoInsereNota(requestCode, data)) {
            if (resultadoOk(resultCode)){
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                adiciona(notaRecebida);
            }


        }

        if (ehResultadoAlteraNota(requestCode, data)) {

            if (resultadoOk(resultCode)){

                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                Toast.makeText(this, notaRecebida.getTitulo(), Toast.LENGTH_SHORT).show();
                int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);

                if (ehPosiçãoValida(posicaoRecebida)) {

                    altera(notaRecebida, posicaoRecebida);
                }
            }
            
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void altera(Nota nota, int posicao) {
        new NotaDAO().altera(posicao, nota);
        adapter.altera(posicao, nota);
    }

    private boolean ehPosiçãoValida(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean ehResultadoInsereNota(int requestCode,  @Nullable Intent data) {
        return ehCódigoRequisiçãoInsereNota(requestCode)  && data.hasExtra(CHAVE_NOTA);
    }

    private boolean ehResultadoAlteraNota(int requestCode,  @Nullable Intent data) {
        return ehCógidoRequisiçãoAlteraNota(requestCode)  && temNota(data);
    }

    private boolean ehCógidoRequisiçãoAlteraNota(int requestCode) {
        return requestCode == CÓDIGO_REQUISIÇÃO_ALTERA_NOTA;
    }

    private void adiciona(Nota nota) {
        new NotaDAO().insere(nota);
        adapter.adiciona(nota);
    }

    private boolean temNota(@NonNull Intent data) {
        return data != null && data.hasExtra(CHAVE_NOTA);
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCódigoRequisiçãoInsereNota(int requestCode) {
        return requestCode == CÓDIGO_REQUISIÇÃO_INSERE_NOTA;
    }


    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.recyclerNotas);
        configuraAdapter(todasNotas, listaNotas);
        configuraItemTouchHelper(listaNotas);

    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(Nota nota, int posicao) {
                vaiParaFormularioNotaActivityAltera(nota, posicao);

            }
        });
    }

    private void vaiParaFormularioNotaActivityAltera(Nota nota, int posicao) {
        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        abreFormularioComNota.putExtra(CHAVE_POSICAO, posicao);
        startActivityForResult(abreFormularioComNota, CÓDIGO_REQUISIÇÃO_ALTERA_NOTA);
    }
}