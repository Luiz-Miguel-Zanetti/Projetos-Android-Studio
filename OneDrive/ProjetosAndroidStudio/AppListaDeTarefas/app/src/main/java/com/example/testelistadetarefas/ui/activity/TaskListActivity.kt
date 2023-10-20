package com.example.testelistadetarefas.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testelistadetarefas.R
import com.example.testelistadetarefas.recyclerView.adapter.TaskListAdapter
import com.example.testelistadetarefas.databinding.ActivityTaskListBinding
import com.example.testelistadetarefas.databinding.CustomAlertDialogHelpBinding
import com.example.testelistadetarefas.model.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class TaskListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskListAdapter
    private val taskList = mutableListOf<Task>()

    private val firestoreInstance by lazy {
        FirebaseFirestore.getInstance()
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    companion object {
        private const val TITLE_APP_BAR = "Lista de Tarefas"

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        setupRecyclerView()
        listAllTasks()

        supportActionBar?.title = TITLE_APP_BAR


    }

    private fun initializeViews() {
        recyclerView = binding.recyclerTasks

    }

    private fun setupRecyclerView() {
        adapter = TaskListAdapter(this, taskList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }

    private fun listAllTasks() {

        val userId = auth.currentUser?.uid

        if (userId != null) {
            firestoreInstance.collection("users")
                .document(userId)
                .collection("userTasks")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot) {
                        val id = document.getString("id")
                        val title = document.getString("title")
                        val description = document.getString("description")
                        val date = document.getString("date")
                        val time = document.getString("time")

                        if (id != null && title != null && description != null && date != null && time != null) {
                            val task = Task(id, title, description, date, time)
                            taskList.add(task)
                            Log.d("FirestoreData", "Task added: $task")
                        }
                    }
                    Log.d("FirestoreData", "Total tasks: ${taskList.size}")
                    adapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.e("FirestoreData", "Error fetching tasks: ${exception.message}")
                    Toast.makeText(
                        this,
                        "Error fetching tasks: ${exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_task) {
            return goToRegisterTaskActivity()
        }

        if (item.itemId == R.id.logout) {

            return logOutUser()

        }

        if (item.itemId == R.id.iconHelp) {

            return configAlertDialog()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun configAlertDialog(): Boolean {

        val dialogBinding = CustomAlertDialogHelpBinding.inflate(layoutInflater)
        val dialogView = dialogBinding.root

        val buttonOk = dialogBinding.buttonOk

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)

        val alertDialog = builder.create()
        alertDialog.show()

        buttonOk.setOnClickListener {

            alertDialog.dismiss()

        }

        return true

    }

    private fun logOutUser(): Boolean {
        val intent = Intent(this, IntroductionActivity::class.java)
        auth.signOut()
        startActivity(intent)
        finish()
        showSnackBar(binding.root, "Usuario desconectado!")
        return true
    }

    private fun goToRegisterTaskActivity(): Boolean {
        val intent = Intent(this, RegisterTaskActivity::class.java)
        startActivity(intent)
        return true
    }

    fun showSnackBar(view: View, message: String) {


        val snackbar = Snackbar.make(view, message, 2000)

        snackbar.view.background = ContextCompat.getDrawable(this, R.drawable.custom_snack_bar)

        snackbar.setTextColor(ColorStateList.valueOf(Color.WHITE))
        snackbar.show()


    }


}