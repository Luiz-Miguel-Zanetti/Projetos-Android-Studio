package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.R
import com.example.orgs.dao.ProdutoDAO
import com.example.orgs.ui.recyclerView.adapter.ProdutoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaProdutosActivity : AppCompatActivity()  {

    private val dao = ProdutoDAO()
    private val adapter = ProdutoAdapter(this, dao.buscaTodos())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_produtos)
        configuraFab()

    }

    override fun onResume() {
        super.onResume()
        configuraRecyclerView()
        adapter.atualiza(dao.buscaTodos())

    }

    private fun configuraFab() {
        val buttonAddProduto = findViewById<FloatingActionButton>(R.id.fabAddProduto)
        buttonAddProduto.setOnClickListener {

            vaiParaCadastroProdutoActivity()

        }
    }

    private fun vaiParaCadastroProdutoActivity() {
        val intent = Intent(this, CadastroProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerProdutos = findViewById<RecyclerView>(R.id.recyclerProdutos);
        recyclerProdutos.adapter = adapter
    }
}