package com.example.testelistadetarefas.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.testelistadetarefas.databinding.ActivityUpdateTaskBinding
import com.example.testelistadetarefas.model.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var fieldTitle: EditText
    private lateinit var fieldDescription: EditText
    private lateinit var fieldDate: EditText
    private lateinit var fieldTime: EditText
    private lateinit var buttonUpdateTask: Button
    private lateinit var binding: ActivityUpdateTaskBinding
    private lateinit var progressBar: ProgressBar

    private val auth by lazy {
        FirebaseAuth.getInstance()

    }

    companion object {
        private const val TITLE_APP_BAR = "Atualize sua tarefa"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = TITLE_APP_BAR
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initializeFields()
        getExtrasAndSetFields()

        buttonUpdateTask.setOnClickListener {
            updateTask()

        }

    }

    private fun initializeFields() {
        fieldTitle = binding.editTitleTask
        fieldDescription = binding.editDescriptionTask
        fieldDate = binding.editDateTask
        fieldTime = binding.editTimeTask
        buttonUpdateTask = binding.buttonUpdateTask
        progressBar = binding.progressBarUpdateTask
    }

    private fun updateTask() {
        val updatedTitle = fieldTitle.text.toString()
        val updatedDescription = fieldDescription.text.toString()
        val updatedDate = fieldDate.text.toString()
        val updatedTime = fieldTime.text.toString()

        val userId = auth.currentUser?.uid

        // Obtém o ID da tarefa da intent
        val taskId = intent.getStringExtra("id")

        if (userId != null && taskId != null) {
            // Caminho para a coleção de tarefas do usuário
            val userTasksCollection = FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .collection("userTasks")

            // Atualiza os campos da tarefa usando o ID da tarefa como nome do documento
            val taskData = hashMapOf(
                "title" to updatedTitle,
                "description" to updatedDescription,
                "date" to updatedDate,
                "time" to updatedTime
            )

            val taskDataAny: Map<String, Any> = taskData.toMap()


            // Atualiza os campos da tarefa no Firestore
            userTasksCollection.document(taskId).update(taskDataAny)
                .addOnSuccessListener {
                    // Primeiro, atualize os campos da tarefa no Firestore
                    showSnackBar(binding.root, "Tarefa atualizada com sucesso!")
                    progressBar.visibility = View.INVISIBLE

                    goToTaskActivity()
                }
                .addOnFailureListener { exception ->
                    // Tratamento de erro em caso de falha na atualização
                    showSnackBar(binding.root, "Erro ao atualizar tarefa!")
                    progressBar.visibility = View.INVISIBLE
                }
        } else {
            showSnackBar(binding.root, "Usuário não autenticado ou ID de tarefa ausente!")
        }
    }


    private fun goToTaskActivity() {

        progressBar.visibility = View.VISIBLE


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, TaskListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            progressBar.visibility = View.INVISIBLE
            finish()
        }, 2600)
    }

    private fun getExtrasAndSetFields(): String? {
        val taskId = intent.getStringExtra("id")
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")
        fieldTitle.setText(title)
        fieldDescription.setText(description)
        fieldDate.setText(date)
        fieldTime.setText(time)
        return taskId
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            android.R.id.home -> {
                finish()
                true
            }
            else ->

                return super.onOptionsItemSelected(item)


        }

    }

    fun showSnackBar(view: View, message: String) {


        val snackbar = Snackbar.make(view, message, 2000)

        snackbar.view.background = ContextCompat.getDrawable(this, R.drawable.custom_snack_bar)

        snackbar.setTextColor(ColorStateList.valueOf(Color.WHITE))
        snackbar.show()


    }

}