package com.example.appcomrecyclerview.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcomrecyclerview.R;
import com.example.appcomrecyclerview.model.Nota;
import com.example.appcomrecyclerview.ui.recyclerview.adapter.listener.OnItemClickListener;

import java.util.Collections;
import java.util.List;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotasViewHolder> {

    private Context context;
    private List<Nota> notas;
    private OnItemClickListener onItemClickListener;

    public ListaNotasAdapter(Context context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListaNotasAdapter.NotasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int posição) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false);
        return new NotasViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaNotasAdapter.NotasViewHolder holder, int posição) {
        Nota nota = notas.get(posição);
        holder.vincula(nota);

    }


    @Override
    public int getItemCount() {
        return notas.size();

    }

    public void altera(int posicao, Nota nota) {
        notas.set(posicao, nota);
        notifyDataSetChanged();

    }

    public void remove(int posicao) {
        notas.remove(posicao);
        notifyItemRemoved(posicao);

    }

    public void trocar(int posicaoInicial, int posicaoFinal) {
        Collections.swap(notas, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoFinal, posicaoInicial);

    }

    public class NotasViewHolder extends RecyclerView.ViewHolder {

        private final TextView descrição;
        private final TextView titulo;
        private Nota nota;


        public NotasViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textItemTitulo);
            descrição = itemView.findViewById(R.id.textItemDescrição);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onItemClickListener.OnItemClick(nota, getAdapterPosition());


                }
            });
        }

        public void vincula(Nota nota) {
           this.nota = nota;
            preencheCampos(nota);

        }

        private void preencheCampos(Nota nota) {
            titulo.setText(nota.getTitulo());
            descrição.setText(nota.getDescrição());
        }


    }

    public void adiciona(Nota nota) {
        notas.add(nota);
        notifyDataSetChanged();

    }
}
