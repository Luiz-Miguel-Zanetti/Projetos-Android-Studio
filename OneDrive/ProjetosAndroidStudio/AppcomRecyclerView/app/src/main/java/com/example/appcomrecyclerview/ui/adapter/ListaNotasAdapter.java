package com.example.appcomrecyclerview.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appcomrecyclerview.R;
import com.example.appcomrecyclerview.model.Nota;

import java.util.List;

public class ListaNotasAdapter extends BaseAdapter {

    Context context;
    List<Nota> notas;

    public ListaNotasAdapter(Context context, List<Nota> notas) {

        this.context = context;
        this.notas = notas;
    }


    @Override
    public int getCount() {
        return notas.size();
    }

    @Override
    public Object getItem(int posicao) {

         notas.get(posicao);

        return notas.get(posicao);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int posição, View view, ViewGroup viewGroup) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.item_nota, viewGroup, false);
        Nota nota = notas.get(posição);

        TextView titulo = itemView.findViewById(R.id.textItemTitulo);
        titulo.setText(nota.getTitulo());

        TextView descrição = itemView.findViewById(R.id.textItemDescrição);
        descrição.setText(nota.getDescrição());

        return itemView;
    }
}
