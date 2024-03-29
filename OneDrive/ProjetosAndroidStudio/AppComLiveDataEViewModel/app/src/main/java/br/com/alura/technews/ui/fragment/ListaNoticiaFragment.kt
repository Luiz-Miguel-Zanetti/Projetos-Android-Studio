package br.com.alura.technews.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.technews.R
import br.com.alura.technews.database.AppDatabase
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.ui.fragment.extesions.mostraErro
import br.com.alura.technews.ui.recyclerview.adapter.ListaNoticiasAdapter
import br.com.alura.technews.ui.viewmodel.ListaNoticiasViewModel
import br.com.alura.technews.ui.viewmodel.factory.ListaNoticiasViewModelFactory
import kotlinx.android.synthetic.main.lista_noticias.*

private const val MENSAGEM_FALHA_CARREGAR_NOTICIAS = "Não foi possível carregar as novas notícias"
private const val TITULO_APPBAR = "Notícias"

class ListaNoticiaFragment : Fragment() {


    var quandoFabSalvaNoticiaClicado : () ->Unit = {}
    var quandoNoticiaSelecionada :(noticia : Noticia) -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaNoticias()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lista_noticias, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraRecyclerView()
        configuraFabAdicionaNoticia()
        activity?.title = TITULO_APPBAR
    }

    private val adapter by lazy {

        context?.let {
            ListaNoticiasAdapter(context = it)
        } ?: throw IllegalArgumentException("Contexto ínvalido")
    }



    private val viewModel by lazy {
        context?.let {
            val repository = NoticiaRepository(AppDatabase.getInstance(it).noticiaDAO)
            val factory = ListaNoticiasViewModelFactory(repository)
            val provedor = ViewModelProvider(this, factory)
            provedor.get(ListaNoticiasViewModel::class.java)
        }
    }

    private fun configuraFabAdicionaNoticia() {
        lista_noticias_fab_salva_noticia.setOnClickListener {
            quandoFabSalvaNoticiaClicado
        }
    }

    private fun configuraAdapter() {
        adapter.quandoItemClicado = quandoNoticiaSelecionada
    }

    private fun configuraRecyclerView() {
        val divisor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        lista_noticias_recyclerview.addItemDecoration(divisor)
        lista_noticias_recyclerview.adapter = adapter
        configuraAdapter()
    }

    private fun buscaNoticias() {
        viewModel!!.buscaTodos().observe(this, Observer { resource ->

            resource.dado?.let { adapter.atualiza(it) }
            resource.erro?.let { mostraErro(MENSAGEM_FALHA_CARREGAR_NOTICIAS) }
        })

    }



}