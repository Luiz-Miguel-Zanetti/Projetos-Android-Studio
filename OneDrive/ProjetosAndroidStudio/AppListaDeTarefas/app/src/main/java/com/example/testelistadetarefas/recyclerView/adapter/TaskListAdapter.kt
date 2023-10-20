package com.example.testelistadetarefas.recyclerView.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.testelistadetarefas.R
import com.example.testelistadetarefas.model.Task
import com.example.testelistadetarefas.ui.activity.UpdateTaskActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class TaskListAdapter(
    private val context: Context,
    private val taskList: MutableList<Task>
) : RecyclerView.Adapter<TaskListAdapter.MyViewHolder>() {

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_layout_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task: Task = taskList[position]

        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.textTitleTaskAdapter)
        private val description: TextView = itemView.findViewById(R.id.textDescriptionAdapter)
        private val date: TextView = itemView.findViewById(R.id.textDateAdapter)
        private val time: TextView = itemView.findViewById(R.id.textTimeAdapter)

        fun bind(task: Task) {
            title.text = task.title
            description.text = task.description
            date.text = task.date
            time.text = task.time

            itemView.setOnLongClickListener {
                configAlertDialog(task, itemView)
                true
            }
        }
    }

    private fun configAlertDialog(task: Task, itemView: View) {
        val options = arrayOf("Editar Tarefa", "Remover Tarefa")
        val layout = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_tasks, null)
        val listView = layout.findViewById<ListView>(R.id.dialog_list_view)
        val buttonCancel = layout.findViewById<Button>(R.id.buttonCancel)

        val adapter = ArrayAdapter(
            context, android.R.layout.select_dialog_item, options
        )

        listView.adapter = adapter

        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.MyAlertDialogStyle))
        builder.setView(layout)

        val alertDialog = builder.create()

        listView.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    // Editar tarefa
                    editTask(task)
                }
                1 -> {
                    // Remover tarefa
                    removeTask(task, itemView)
               showSnackBar(itemView, "Tarefa removida com sucesso!")
                }
            }
            alertDialog.dismiss()
        }

        buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun editTask(task: Task) {
        val intent = Intent(context, UpdateTaskActivity::class.java)
        intent.putExtra("id", task.id)
        intent.putExtra("title", task.title)
        intent.putExtra("description", task.description)
        intent.putExtra("date", task.date)
        intent.putExtra("time", task.time)
        context.startActivity(intent)
    }

    private fun removeTask(task: Task, itemView: View) {
        val userId = auth.currentUser?.uid
        val taskId = task.id

        if (taskId != null && userId != null) {
            val userTasksCollection = firestore
                .collection("users")
                .document(userId)
                .collection("userTasks")

            userTasksCollection.document(taskId)
                .delete()
                .addOnSuccessListener {
                    taskList.remove(task)
                    notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    showSnackBar(itemView, "Erro ao remover tarefa!")
                }
        }
    }

    fun showSnackBar(view: View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(Color.parseColor("#26C6DA")) // Define a cor de fundo
        snackbar.setTextColor(ColorStateList.valueOf(Color.WHITE)) // Define a cor do texto
        snackbar.show()
    }
}
