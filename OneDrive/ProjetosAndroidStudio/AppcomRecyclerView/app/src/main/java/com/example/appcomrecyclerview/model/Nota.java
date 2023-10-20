package com.example.appcomrecyclerview.model;

import java.io.Serializable;

public class Nota implements Serializable {

    private final String titulo;
    private final String descrição;


    public String getTitulo() {
        return titulo;
    }

    public String getDescrição() {
        return descrição;
    }

    public Nota(String titulo, String descrição) {
        this.titulo = titulo;
        this.descrição = descrição;


    }
}
