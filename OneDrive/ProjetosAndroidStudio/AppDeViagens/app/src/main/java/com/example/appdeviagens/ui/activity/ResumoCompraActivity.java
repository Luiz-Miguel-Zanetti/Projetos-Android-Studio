package com.example.appdeviagens.ui.activity;

import static com.example.appdeviagens.ui.activity.PacoteActitivityConstantes.CHAVE_PACOTE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.appdeviagens.R;
import com.example.appdeviagens.model.Pacote;
import com.example.appdeviagens.util.DataUtil;
import com.example.appdeviagens.util.DiasUtil;
import com.example.appdeviagens.util.MoedaUtil;

import java.math.BigDecimal;

public class ResumoCompraActivity extends AppCompatActivity {

    public static final String TITULO_APP_BAR = "Resumo da compra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_compra);

        setTitle(TITULO_APP_BAR);

        inicializaCampos();


    }

    private void inicializaCampos() {
        Intent intent = getIntent();
        if (intent.hasExtra(CHAVE_PACOTE)) {

            Pacote pacote = (Pacote) intent.getSerializableExtra(CHAVE_PACOTE);

            mostraLocal(pacote);
            mostraData(pacote);
            mostraPreço(pacote);


        }
    }

    private void mostraPreço(Pacote pacote) {
        TextView preço = findViewById(R.id.textResumoCompraPreco);
        String moeda = MoedaUtil.formataParaMoedaBrasileira(pacote.getPreco());
        preço.setText(moeda);
    }

    private void mostraData(Pacote pacote) {
        TextView data = findViewById(R.id.textResumoCompraData);
        String periodoEmTexto = DataUtil.periodoEmTexto(pacote.getDias());
        data.setText(periodoEmTexto);
    }

    private void mostraLocal(Pacote pacote) {
        TextView local = findViewById(R.id.textResumoCompraLocal);
        local.setText(pacote.getLocal());
    }
}