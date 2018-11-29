package org.ieszaidinvergeles.dam.chaterbot;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ieszaidinvergeles.dam.chaterbot.objetos.Chat;
import org.ieszaidinvergeles.dam.chaterbot.objetos.Conversacion;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter <Adaptador.MyViewHolder> {

    Context content;
    List<Conversacion> conversaciones;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Conversacion item);
    }

    public Adaptador(Context content, List<Conversacion> conversaciones, OnItemClickListener listener) {
        this.content = content;
        this.conversaciones = conversaciones;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Adaptador.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.MyViewHolder holder, int position) {
        holder.texto.setText(conversaciones.get(position).getFecha());
        holder.bind(conversaciones.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return conversaciones.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView texto;

        public MyViewHolder(View itemView) {
            super(itemView);
            texto = itemView.findViewById(R.id.fecha);
        }

        public void bind(final Conversacion conversacion, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(conversacion);
                }
            });
        }
    }
}
