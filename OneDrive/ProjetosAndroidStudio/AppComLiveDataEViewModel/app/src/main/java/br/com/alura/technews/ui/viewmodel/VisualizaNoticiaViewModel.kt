package br.com.alura.technews.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.repository.Resource

class VisualizaNoticiaViewModel(
    val id: Long,
    private val repository: NoticiaRepository
) : ViewModel() {

    val noticiaEncontrada = repository.buscaPorId(id)

    fun remove(): LiveData<Resource<Void?>> {
        return noticiaEncontrada.value?.run {
            repository.remove(this)
        } ?: MutableLiveData<Resource<Void?>>().also {
            it.value = Resource(null, "Notícia não encontrada")
        }
    }

}