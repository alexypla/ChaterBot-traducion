package org.ieszaidinvergeles.dam.chaterbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.ieszaidinvergeles.dam.chaterbot.baseDatos.Gestor;
import org.ieszaidinvergeles.dam.chaterbot.objetos.Chat;
import org.ieszaidinvergeles.dam.chaterbot.objetos.Conversacion;

import java.util.List;

public class Principal extends AppCompatActivity {

    Gestor gestor;
    RecyclerView recyclerConversaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gestor = new Gestor(this);
        nuevoChat();

    }

    @Override
    protected void onStart() {
        super.onStart();
        iniciarRecycler();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void nuevoChat(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this, VerChat.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarRecycler(){
        recyclerConversaciones = findViewById(R.id.recyclerConversaciones);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerConversaciones.setLayoutManager(mLayoutManager);
        Adaptador adaptador = new Adaptador(this, getConversaciones(), new Adaptador.OnItemClickListener() {
            @Override
            public void onItemClick(Conversacion item) {
                Intent intent = new Intent(Principal.this, VerChat.class);
                intent.putExtra("conversacion", item);
                startActivity(intent);
            }
        });
        recyclerConversaciones.setAdapter(adaptador);
    }

    private List<Conversacion> getConversaciones(){
        return gestor.getAllConversacion();
    }

}
