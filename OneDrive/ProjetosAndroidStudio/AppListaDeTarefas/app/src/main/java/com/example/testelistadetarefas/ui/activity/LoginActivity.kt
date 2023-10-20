package com.example.testelistadetarefas.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.postDelayed
import com.example.testelistadetarefas.R
import com.example.testelistadetarefas.databinding.ActivityLoginBinding
import com.example.testelistadetarefas.model.User
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var fildEmail: EditText
    private lateinit var fieldPassword: EditText
    private lateinit var buttonEnter: Button
    private lateinit var buttonRegisterNewPerson: TextView
    private lateinit var checkBox: CheckBox
    private lateinit var progressBar: ProgressBar
    private lateinit var user: User
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        initializeComponents()
        setUpButtonLogin()
        configCheckBox()
        verifyLoggedUser()


        buttonRegisterNewPerson.setOnClickListener {
            goToRegisterActivity()
        }


    }

    private fun setUpButtonLogin() {
        buttonEnter.setOnClickListener {
            configUser()
        }
    }

    private fun goToRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)

    }

    private fun initializeComponents() {
        fildEmail = binding.editEmailLogin
        fieldPassword = binding.editPasswordLogin
        buttonEnter = binding.buttonEnter
        buttonRegisterNewPerson = binding.textButtonNewPerson
        checkBox = binding.checkBoxShowPasswordLogin
        progressBar = binding.progressBarLogin

    }

    private fun configCheckBox() {
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                fieldPassword.transformationMethod = null

            } else {

                fieldPassword.transformationMethod = PasswordTransformationMethod.getInstance()

            }


        }
    }

    private fun configUser() {

        val email = fildEmail.text.toString()
        val password = fieldPassword.text.toString()
        user = User(email = email, password = password)


        if (email.isEmpty() && password.isEmpty()) {

            showSnackBar(binding.root, "Preencha os campos corretamente!")

        } else {

            configUserFirebase(email, password)
            showSnackBar(binding.root, "Usuário logado com sucesso!")

        }


    }

    private fun configUserFirebase(email: String, password: String) {
        progressBar.visibility = View.VISIBLE // Tornar a barra de progresso visível


        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                // Handler para adicionar um atraso antes de ir para a TaskListActivity
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, TaskListActivity::class.java)
                    startActivity(intent)
                    finish() // Isso encerrará a LoginActivity
                }, 2300) // Define o tempo de atraso
            } else {
                progressBar.visibility =
                    View.INVISIBLE // Ocultar a barra de progresso em caso de erro

                handleRegistrationError(task.exception)
            }
        }
    }

    private fun handleRegistrationError(exception: Exception?) {
        when (exception) {
            is FirebaseAuthInvalidUserException -> {
                showSnackBar(binding.root, "Usuário não encontrado!")
            }
            is FirebaseAuthInvalidCredentialsException -> {
                showSnackBar(binding.root, "Credenciais ínvalidas!")
            }
            else -> {
                showSnackBar(binding.root, "Erro ao fazer login!")
            }
        }
    }

    fun showSnackBar(view: View, message: String) {


        val snackbar = Snackbar.make(view, message, 4000)

        snackbar.view.background = ContextCompat.getDrawable(this, R.drawable.custom_snack_bar)

        snackbar.setTextColor(ColorStateList.valueOf(Color.WHITE))
        snackbar.show()


    }

    fun verifyLoggedUser(){

        val currentUser = auth.currentUser

        if (currentUser != null){

            val intent = Intent(this, TaskListActivity::class.java)
            startActivity(intent)
            finish()

        }

    }

}



