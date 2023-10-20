package com.example.testelistadetarefas.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.postDelayed
import com.example.testelistadetarefas.R
import com.example.testelistadetarefas.databinding.ActivityLoginBinding
import com.example.testelistadetarefas.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var fieldName: EditText
    private lateinit var fieldEmail: EditText
    private lateinit var fieldPassword: EditText
    private lateinit var fieldConfirmPassoword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var checkBox: CheckBox
    private lateinit var progressBar: ProgressBar

    companion object {
        private const val TITLE_APP_BAR = "Novo usuário"
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        configCheckBox()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = TITLE_APP_BAR

        buttonRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun initializeViews() {
        fieldName = binding.editNameRegister
        fieldEmail = binding.editEmailRegister
        fieldPassword = binding.editPasswordRegister
        fieldConfirmPassoword = binding.editConfirmPasswordRegister
        checkBox = binding.checkBoxShowPasswordRegister
        buttonRegister = binding.buttonRegisterPerson
        progressBar = binding.progressBarRegisterPerson
    }

    private fun configCheckBox() {

        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {

                fieldPassword.transformationMethod = null
                fieldConfirmPassoword.transformationMethod = null

            } else {

                fieldPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                fieldConfirmPassoword.transformationMethod =
                    PasswordTransformationMethod.getInstance()

            }


        }


    }

    private fun registerUser() {
        val name = fieldName.text.toString()
        val email = fieldEmail.text.toString()
        val password = fieldPassword.text.toString()
        val confirmPassoword = fieldConfirmPassoword.text.toString()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showSnackBar(binding.root, "Preencha todos os campos corretamente!")

        } else if (password != confirmPassoword) {
            showSnackBar(binding.root, "Senhas distintas!")
        } else {
            createUserInFirebase(name, email, password)
            showSnackBar(binding.root, "Usuário cadastrado com sucesso!")

        }
    }

    fun showSnackBar(view: View, message: String) {

        val snackbar = Snackbar.make(view, message, 2300)

        snackbar.view.background = ContextCompat.getDrawable(this, R.drawable.custom_snack_bar)
        snackbar.setTextColor(ColorStateList.valueOf(Color.WHITE))
        snackbar.show()
    }

    private fun createUserInFirebase(name: String, email: String, password: String) {

        progressBar.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Handler(Looper.getMainLooper()).postDelayed({
                        val intent = Intent(this, LoginActivity::class.java)
                        Log.d("RegisterActivity", "Usuário cadastrado com sucesso")
                        startActivity(intent)

                    }, 2300)
                } else {
                    progressBar.visibility = View.INVISIBLE
                    handleRegistrationError(task.exception)
                }

            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return when (item.itemId) {

            android.R.id.home -> {

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {

                super.onOptionsItemSelected(item)

            }

        }

    }


    private fun handleRegistrationError(exception: Exception?) {
        when (exception) {
            is FirebaseAuthUserCollisionException -> showSnackBar(
                binding.root,
                "Email já cadastrado!"
            )
            is FirebaseAuthInvalidUserException -> showSnackBar(
                binding.root,
                "Email ou senha ínvalidos!"
            )
            is FirebaseAuthWeakPasswordException -> showSnackBar(
                binding.root,
                "A senha deve conter no mínimo seis caracteres"
            )
            else -> showSnackBar(binding.root, "Erro ao cadastrar novo usuário")
        }
    }
}

