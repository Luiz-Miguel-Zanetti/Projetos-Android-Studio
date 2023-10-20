package com.example.appcomrecyclerview.dao;

import com.example.appcomrecyclerview.model.Nota;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NotaDAO {

    private final static ArrayList<Nota> notas = new ArrayList<>();

    public List<Nota> todos(){


        return (List<Nota>) notas.clone();
    }

    public void insere(Nota... notas){

        NotaDAO.notas.addAll(Arrays.asList(notas));

    }

    public void altera(int posição, Nota nota){

        notas.set(posição, nota);

    }

    public void remove(int posição){

        notas.remove(posição);

    }

    public void troca(int posiçãoInicio, int posiçãoFim){

        Collections.swap(notas, posiçãoInicio, posiçãoFim);

    }

    public void removeTodos(){

        notas.clear();
    }

}
