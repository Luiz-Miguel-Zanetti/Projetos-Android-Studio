package com.example.app_kotlincomroom.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.extensions.vaiPara
import br.com.alura.orgs.ui.activity.LoginActivity
import com.example.app_kotlincomroom.database.AppDatabase
import com.example.app_kotlincomroom.model.Usuario
import com.example.app_kotlincomroom.preferences.datastore.datastore
import com.example.app_kotlincomroom.preferences.datastore.usuarioLogadoPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

abstract class UsuarioBaseActivity : AppCompatActivity() {

    private val usuarioDao by lazy {
        AppDatabase.instacia(this).usuarioDao()
    }

    private val _usuario: MutableStateFlow<Usuario?> = MutableStateFlow(null)
    protected val usuario: StateFlow<Usuario?> = _usuario

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        lifecycleScope.launch {
            verificaUsuarioLogado()
        }
    }

    private suspend fun verificaUsuarioLogado() {

        datastore.data.collect { preferences ->
            preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                buscaUsuario(usuarioId)
            }
        } ?: vaiParaLoginActivity()

    }

    private suspend fun buscaUsuario(usuarioId: String): Usuario? {

        _usuario.value = usuarioDao.buscaPorId(usuarioId).firstOrNull().also {
            _usuario.value = it
        }
return null
    }


    protected suspend fun deslogaUsuario() {
        datastore.edit { preferences ->
            preferences.remove(usuarioLogadoPreferences)

        }
    }

    private fun vaiParaLoginActivity() {
        vaiPara(LoginActivity::class.java){

            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()
    }

}

