package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.activity.extensions.transaçãoFragment
import br.com.alura.technews.ui.fragment.ListaNoticiaFragment
import br.com.alura.technews.ui.fragment.VisualizaNoticiaFragment

private const val TITULO_APPBAR = "Notícias"


class NoticiasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        title = TITULO_APPBAR

        if (savedInstanceState == null) {
            abreListaNoticia()
        }

    }

    private fun abreListaNoticia() {
        transaçãoFragment {
            replace(R.id.activity_noticias_container, ListaNoticiaFragment())
        }
    }


    override fun onResume() {

        super.onResume()
    }

    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)

        when (fragment) {

            is ListaNoticiaFragment -> {

                configuraListaNoticias(fragment)
            }

            is VisualizaNoticiaFragment -> {

                configuraVisualizaNoticia(fragment)
            }
        }

    }

    private fun configuraVisualizaNoticia(fragment: VisualizaNoticiaFragment) {
        fragment.quandoFinalizaTela = this::finish
        fragment.quandoSelecionaMenuEdicao = this::abreVisualizadorNoticia
    }

    private fun configuraListaNoticias(fragment: ListaNoticiaFragment) {
        fragment.quandoNoticiaSelecionada = this::abreFormularioEdicao
        fragment.quandoFabSalvaNoticiaClicado = this::abreFormularioModoCriacao
    }

    private fun abreVisualizadorNoticia(noticia: Noticia) {

        val fragment = VisualizaNoticiaFragment()
        val dados = Bundle()
        dados.putLong(NOTICIA_ID_CHAVE, noticia.id)
        fragment.arguments = dados

        transaçãoFragment {
            addToBackStack(null)
            replace(R.id.activity_noticias_container, fragment)
        }
    }

    private fun abreFormularioEdicao(noticia: Noticia) {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticia.id)
        startActivity(intent)
    }

}


