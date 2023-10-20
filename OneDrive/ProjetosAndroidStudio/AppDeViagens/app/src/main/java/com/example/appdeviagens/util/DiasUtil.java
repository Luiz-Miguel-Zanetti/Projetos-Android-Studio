package com.example.appdeviagens.util;

import androidx.annotation.NonNull;

import com.example.appdeviagens.model.Pacote;

public class DiasUtil {

    public static final String PLURAL = "dias";
    public static final String SINGULAR = "dia";

    public static String formataEmTexto(int quantidadeDias) {

        if (quantidadeDias > 1) {

            return quantidadeDias + PLURAL;

        }
        return quantidadeDias + SINGULAR;
    }

}

