package com.example.appdeviagens.ui.activity;

import static com.example.appdeviagens.ui.activity.PacoteActitivityConstantes.CHAVE_PACOTE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdeviagens.R;
import com.example.appdeviagens.model.Pacote;
import com.example.appdeviagens.util.DataUtil;
import com.example.appdeviagens.util.DiasUtil;
import com.example.appdeviagens.util.MoedaUtil;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ResumoPacoteActivity extends AppCompatActivity {

    public static final String TITULO_APP_BAR = "Resumo do Pacote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pacote);

        setTitle(TITULO_APP_BAR);
        carregaPacoteRecebido();


    }

    private void carregaPacoteRecebido() {
        Intent intent = getIntent();
        if (intent.hasExtra(CHAVE_PACOTE)) {
           final Pacote pacote = (Pacote) intent.getSerializableExtra(CHAVE_PACOTE);

            inicializaCampos(pacote);

            configuraBotão(pacote);


        }
    }

    private void configuraBotão(Pacote pacote) {
        Button buttonRealizaPagamento = findViewById(R.id.buttonResumoPagamento);
        buttonRealizaPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaPagamentoActivity(pacote);
            }
        });
    }

    private void vaiParaPagamentoActivity(Pacote pacote) {
        Intent intent = new Intent(ResumoPacoteActivity.this, PagamentoActivity.class);
        intent.putExtra(CHAVE_PACOTE, pacote);
        startActivity(intent);
    }

    private void inicializaCampos(Pacote pacote) {
        mostraLocal(pacote);
        mostraImagem(R.id.imageResumoLocal);
        mostraDias(pacote);
        mostraPreço(pacote);
        mostraData(pacote);
    }

    private void mostraData(Pacote pacote) {
        TextView data = findViewById(R.id.textResumoData);
        String dataFormatadaDaViagem = DataUtil.periodoEmTexto(pacote.getDias());
        data.setText(dataFormatadaDaViagem);
    }



    private void mostraPreço(Pacote pacote) {
        TextView preço = findViewById(R.id.textPreçoResumo);
        String moedaBrasileira = MoedaUtil.formataParaMoedaBrasileira(pacote.getPreco());
        preço.setText(moedaBrasileira);
    }

    private void mostraDias(Pacote pacote) {
        TextView dias = findViewById(R.id.textResumoDias);
        String diasEmTexto = DiasUtil.formataEmTexto(pacote.getDias());
        dias.setText(diasEmTexto);
    }

    private void mostraImagem(int p) {
        ImageView imagem = findViewById(p);
    }

    private void mostraLocal(Pacote pacote) {
        TextView local = findViewById(R.id.textNomeResumoLocal);
        local.setText(pacote.getLocal());
    }
}