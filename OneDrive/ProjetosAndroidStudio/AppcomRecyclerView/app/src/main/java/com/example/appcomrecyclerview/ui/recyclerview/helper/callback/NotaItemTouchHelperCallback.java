package com.example.appcomrecyclerview.ui.recyclerview.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcomrecyclerview.dao.NotaDAO;
import com.example.appcomrecyclerview.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ListaNotasAdapter adapter;

    public NotaItemTouchHelperCallback(ListaNotasAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        int marcaçõesDeDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        int marcaçõesDeArrastar =
                ItemTouchHelper.DOWN |
                        ItemTouchHelper.UP |
                        ItemTouchHelper.RIGHT |
                        ItemTouchHelper.LEFT;
        return makeMovementFlags(marcaçõesDeArrastar, marcaçõesDeDeslize);

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();
        trocaNotas(posicaoInicial, posicaoFinal);
        return true;


    }

    private void trocaNotas(int posicaoInicial, int posicaoFinal) {
        new NotaDAO().troca(posicaoInicial, posicaoFinal);
        adapter.trocar(posicaoInicial, posicaoFinal);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int posiçãoNotaDeslizada = viewHolder.getAdapterPosition();
        removeNota(posiçãoNotaDeslizada);

    }

    private void removeNota(int posição) {
        new NotaDAO().remove(posição);
        adapter.remove(posição);
    }
}
