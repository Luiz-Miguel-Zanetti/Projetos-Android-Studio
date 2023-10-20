package com.example.appdeviagens.ui.activity;

import static com.example.appdeviagens.ui.activity.PacoteActitivityConstantes.CHAVE_PACOTE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appdeviagens.R;
import com.example.appdeviagens.model.Pacote;
import com.example.appdeviagens.util.MoedaUtil;

import java.math.BigDecimal;

public class PagamentoActivity extends AppCompatActivity {

    public static final String TITULO_APP_BAR = "Pagamento";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        setTitle(TITULO_APP_BAR);

        Intent intent = getIntent();
        if (intent.hasExtra(CHAVE_PACOTE)) {

            Pacote pacote = (Pacote) intent.getSerializableExtra(CHAVE_PACOTE);

            mostraPreço(pacote);

            configuraBotao(pacote);

        }



    }

    private void configuraBotao(Pacote pacote) {
        final Button buttonFinalizaCompra = findViewById(R.id.buttonFinalizaCompra);
        buttonFinalizaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaResumoCompra(pacote);
            }
        });
    }

    private void vaiParaResumoCompra(Pacote pacote) {
        Intent intent = new Intent(PagamentoActivity.this, ResumoCompraActivity.class);
        intent.putExtra(CHAVE_PACOTE, pacote);
        startActivity(intent);
    }

    private void mostraPreço(Pacote pacote) {
        TextView preço = findViewById(R.id.textPrecoPagamento);
        String moedaBrasileira = MoedaUtil.formataParaMoedaBrasileira(pacote.getPreco());
        preço.setText(moedaBrasileira);
    }
}