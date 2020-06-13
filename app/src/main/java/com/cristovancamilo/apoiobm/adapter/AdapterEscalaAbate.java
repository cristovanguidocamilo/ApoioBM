package com.cristovancamilo.apoiobm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cristovancamilo.apoiobm.R;
import com.cristovancamilo.apoiobm.model.Camaras;
import com.cristovancamilo.apoiobm.model.EscalaAbate;

import java.util.List;

public class AdapterEscalaAbate extends RecyclerView.Adapter<AdapterEscalaAbate.MyViewHolder> {

    List<EscalaAbate> listaEscalaAbate;
    Context context;

    public AdapterEscalaAbate(List<EscalaAbate> listaEscalaAbate, Context context) {
        this.listaEscalaAbate = listaEscalaAbate;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterEscalaAbate.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemEscalaAbate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_escala_abate, parent, false);
        return new AdapterEscalaAbate.MyViewHolder(itemEscalaAbate);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEscalaAbate.MyViewHolder holder, int position) {
        EscalaAbate escalaAbate = listaEscalaAbate.get(position);

        holder.textoLote.setText("Lote: " + escalaAbate.getLote());
        holder.textoSubLote.setText("SubLote: " + escalaAbate.getSubLote());
        holder.textoQuantidade.setText("Quantidade: " + escalaAbate.getQuantLote());
        holder.textoCurrais.setText("Currais: " + escalaAbate.getCurrais());
        holder.textoProprietario.setText("Proprietário: " + escalaAbate.getNome());
        holder.textoFazenda.setText("Fazenda: " + escalaAbate.getNomeFazenda());
        holder.textoHabilitacao.setText("Habilitação: " + escalaAbate.getHabilitacao());

        if(Integer.parseInt(escalaAbate.getStatusLote()) == 1) {
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.cor_amarelo_fundo_lote_em_abate));
        }else if(Integer.parseInt(escalaAbate.getStatusLote()) == 2) {
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.verde_camara_fechada));
        }
    }

    @Override
    public int getItemCount() {
        return listaEscalaAbate.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textoLote, textoSubLote, textoQuantidade, textoCurrais, textoProprietario, textoFazenda, textoHabilitacao;
        LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            textoLote = itemView.findViewById(R.id.textViewLote);
            textoSubLote = itemView.findViewById(R.id.textViewSubLote);
            textoQuantidade = itemView.findViewById(R.id.textViewQuantidadeEscala);
            textoCurrais = itemView.findViewById(R.id.textViewCurrais);
            textoProprietario = itemView.findViewById(R.id.textViewProprietario);
            textoFazenda = itemView.findViewById(R.id.textViewFazenda);
            textoHabilitacao = itemView.findViewById(R.id.textViewHabilitacaoEscala);
            linearLayout = itemView.findViewById(R.id.linearLayoutFundoEscalaAbate);

        }
    }

}
