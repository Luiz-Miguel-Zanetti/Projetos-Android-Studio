package com.example.orgs.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.orgs.R
import com.example.orgs.dao.ProdutoDAO
import com.example.orgs.model.Produto
import java.math.BigDecimal

class CadastroProdutoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_produto)


        configuraBotaoSalvar()


    }

    private fun configuraBotaoSalvar() {
        var dao = ProdutoDAO()
        val campoNome: EditText = findViewById(R.id.editNomeProduto)
        val campoDescrição: EditText = findViewById(R.id.editDescriçãoProduto)
        val campoValor: EditText = findViewById(R.id.editValorProduto)
        val buttonSalvarProduto: Button = findViewById(R.id.buttonSalvarProduto)

        buttonSalvarProduto.setOnClickListener {

            val produtoNovo = criaProduto(campoNome, campoDescrição, campoValor)
            dao = ProdutoDAO()
            dao.addProduto(produtoNovo)
            Toast.makeText(this, "Produto salvo com sucesso", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ListaProdutosActivity::class.java)
            startActivity(intent)
            finish()


        }
    }

    private fun criaProduto(
        campoNome: EditText,
        campoDescrição: EditText,
        campoValor: EditText
    ): Produto {
        val nome = campoNome.text.toString()
        val descrição = campoDescrição.text.toString()
        val valor = campoValor.text.toString()
        val valorEmTexto = if (valor.isBlank()) {
            BigDecimal.ZERO
        } else {

            BigDecimal(valor)

        }
        return Produto(nome = nome, descrição = descrição, valor = valorEmTexto)
    }
}