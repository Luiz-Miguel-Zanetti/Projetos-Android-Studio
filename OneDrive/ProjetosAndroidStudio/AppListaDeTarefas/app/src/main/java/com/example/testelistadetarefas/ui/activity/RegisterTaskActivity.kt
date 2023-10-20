package com.example.testelistadetarefas.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.postDelayed
import com.example.testelistadetarefas.R
import com.example.testelistadetarefas.databinding.ActivityRegisterTaskBinding
import com.example.testelistadetarefas.model.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class RegisterTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterTaskBinding
    private lateinit var fieldTitle: EditText
    private lateinit var fieldDescription: EditText
    private lateinit var fieldDate: EditText
    private lateinit var fieldTime: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonAddTask: Button
    private val auth by lazy {
        FirebaseAuth.getInstance()

    }
    companion object {
        private const val TITLE_APP_BAR = "Adicione nova tarefa"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = TITLE_APP_BAR
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initializeFields()

        buttonAddTask.setOnClickListener {
            registerTask()
        }
    }

    private fun initializeFields() {
        fieldTitle = binding.editTitleTask
        fieldDescription = binding.editDescriptionTask
        fieldDate = binding.editDateTask
        fieldTime = binding.editTimeTask
        buttonAddTask = binding.buttonRegisterTask
        progressBar = binding.progressBarRegisterTask
    }

    private fun registerTask() {
        val title = fieldTitle.text.toString()
        val description = fieldDescription.text.toString()
        val date = fieldDate.text.toString()
        val time = fieldTime.text.toString()

        // Verifica se pelo menos um campo não está vazio
        if (title.isEmpty() && description.isEmpty() && date.isEmpty() && time.isEmpty()) {
           showSnackBar(binding.root, "Preencha ao menos um campo antes de prosseguir!")
        } else {
            // Chama o registro no Firestore
            registerTaskFirebaseFirestore(title, description, date, time)
            showSnackBar(binding.root, "Tarefa salva com sucesso!")
        }
    }

    private fun registerTaskFirebaseFirestore(
        title: String,
        description: String,
        date: String,
        time: String
    ) {

        progressBar.visibility = View.VISIBLE

        val taskId = UUID.randomUUID().toString().replace("-", "")
        val taskData = hashMapOf(
            "id" to taskId,
            "title" to title,
            "description" to description,
            "date" to date,
            "time" to time
        )

        val userId = auth.currentUser?.uid
        if (userId != null) {
            // Cria uma coleção de tarefas associada a este usuário no Firestore
            val userTasksCollection = FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .collection("userTasks")

            // Usa o ID da tarefa como nome do documento
            userTasksCollection.document(taskId).set(taskData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this, TaskListActivity::class.java)
                            startActivity(intent)
                        }, 2300)
                    } else {
                        progressBar.visibility = View.INVISIBLE
                        val exception = task.exception
                        showSnackBar(binding.root, "Erro ao salvar tarefa : " +
                                "${exception?.message}")
                    }
                }
        } else {
            showSnackBar(binding.root, "Usuário não autenticado!")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            android.R.id.home -> {

                val intent = Intent(this, TaskListActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {


                super.onOptionsItemSelected(item)

            }

        }

    }

    fun showSnackBar(view: View, message: String) {


        val snackbar = Snackbar.make(view, message, 2000)

        snackbar.view.background = ContextCompat.getDrawable(this, R.drawable.custom_snack_bar)

        snackbar.setTextColor(ColorStateList.valueOf(Color.WHITE))
        snackbar.show()


    }



}
