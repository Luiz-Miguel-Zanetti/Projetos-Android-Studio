package br.com.alura.technews.ui.activity.extensions

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

fun Activity.mostraErro(mensagem:String){

    Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()

}

fun AppCompatActivity.transaçãoFragment(executa: FragmentTransaction.() -> Unit) {
    val transação = supportFragmentManager.beginTransaction()
    executa(transação)
    transação.commit()


}
