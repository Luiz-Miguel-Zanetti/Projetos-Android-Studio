package br.com.alura.orgs.ui.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.extensions.vaiPara
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import com.example.app_kotlincomroom.R
import com.example.app_kotlincomroom.database.AppDatabase
import com.example.app_kotlincomroom.databinding.ActivityListaProdutosActivityBinding
import com.example.app_kotlincomroom.preferences.datastore.datastore
import com.example.app_kotlincomroom.preferences.datastore.usuarioLogadoPreferences
import com.example.app_kotlincomroom.ui.activity.UsuarioBaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ListaProdutosActivity : UsuarioBaseActivity() {

    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }


    private val produtoDao by lazy {
        val db = AppDatabase.instacia(this)
        db.produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()

        lifecycleScope.launch {
            launch {
                delay(1000)
                usuario.filterNotNull().collect{usuario ->
                    buscaProdutoUsuario(usuario.id)

                }
            }
        }
    }





    private suspend fun buscaProdutoUsuario(usuarioId : String) {
        produtoDao.buscaTodosDoUsuario(usuarioId).collect() { produtos ->
            adapter.atualiza(produtos)
        }
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        when (item.itemId) {

            R.id.icon_sair_do_app -> lifecycleScope.launch {
                deslogaUsuario()
            }


        }
    }



    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }

}