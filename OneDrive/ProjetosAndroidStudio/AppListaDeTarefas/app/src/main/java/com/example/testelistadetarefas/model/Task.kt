package com.example.testelistadetarefas.model

import java.io.Serializable


data class Task(
    val id : String = "",
    val title: String = "",
    val description: String= "",
    val date: String= "",
    val time: String = ""
)
