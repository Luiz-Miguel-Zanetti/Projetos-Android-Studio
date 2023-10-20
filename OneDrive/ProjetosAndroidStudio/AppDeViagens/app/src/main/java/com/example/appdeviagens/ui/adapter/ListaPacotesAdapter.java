package com.example.appdeviagens.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdeviagens.util.DiasUtil;
import com.example.appdeviagens.util.MoedaUtil;
import com.example.appdeviagens.R;
import com.example.appdeviagens.model.Pacote;

import java.util.List;

public class ListaPacotesAdapter extends BaseAdapter {

    private final List<Pacote> pacotes;
    private final Context context;

    public ListaPacotesAdapter(List<Pacote> pacotes, Context context) {

        this.pacotes = pacotes;
        this.context = context;
    }

    @Override
    public int getCount() {

        return pacotes.size();
    }

    @Override
    public Pacote getItem(int posicao) {

        return pacotes.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return 0;
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {

        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_pacote, viewGroup, false);

        Pacote pacote = pacotes.get(posicao);

        mostraLocal(viewCriada, pacote);
        mostraImagem(viewCriada, pacote);
        mostraDias(viewCriada, pacote);
        mostraPreço(viewCriada, pacote);

        return viewCriada;
    }

    private void mostraPreço(View viewCriada, Pacote pacote) {
        TextView preço = viewCriada.findViewById(R.id.item_pacote_preco);
        String moedaBrasileira = MoedaUtil.formataParaMoedaBrasileira(pacote.getPreco());
        preço.setText(moedaBrasileira);
    }


    private void mostraDias(View viewCriada, Pacote pacote) {
        TextView dias = viewCriada.findViewById(R.id.item_pacote_dias);
        String diasEmTexto = DiasUtil.formataEmTexto(pacote.getDias());
        dias.setText(diasEmTexto);
    }


    private void mostraImagem(View viewCriada, Pacote pacote) {
        ImageView imagem = viewCriada.findViewById(R.id.item_pacote_imagem);
        String uri = pacote.getImagem();
        int idDoDrawable = context.getResources().getIdentifier(uri, "drawable", context.getPackageName());
    }

    private void mostraLocal(View viewCriada, Pacote pacote) {
        TextView local = viewCriada.findViewById(R.id.item_pacote_local);
        local.setText(pacote.getLocal());

    }
}
