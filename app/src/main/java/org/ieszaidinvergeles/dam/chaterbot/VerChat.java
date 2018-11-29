package org.ieszaidinvergeles.dam.chaterbot;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;


import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBot;
import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBotFactory;
import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBotSession;
import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBotType;
import org.ieszaidinvergeles.dam.chaterbot.baseDatos.Contrato;
import org.ieszaidinvergeles.dam.chaterbot.baseDatos.Gestor;
import org.ieszaidinvergeles.dam.chaterbot.objetos.Chat;
import org.ieszaidinvergeles.dam.chaterbot.objetos.Conversacion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerChat extends AppCompatActivity {

    private Conversacion conversacion;
    private Button btSend;
    private EditText etTexto;
    private ScrollView svScroll;
    private TextView tvTexto;
    private FirebaseDatabase baseDatos;
    private DatabaseReference references;
    private ChatterBot bot;
    private ChatterBotFactory factory;
    private ChatterBotSession botSession;
    private Gestor gestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_chat);

        Toolbar toolbar = findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        gestor = new Gestor(this);
        init();
        recogerIntent();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void recogerIntent(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            conversacion = intent.getParcelableExtra("conversacion");
            Log.v("TAGXYZ", conversacion.getId()+"");
            for(Chat c: getChats()){
                tvTexto.append(c.getTexto() + "\n");
                    guardarContactoFirebase(c);

            }
        }else{
            conversacion = new Conversacion();
            long id = gestor.insertConversacion(conversacion);
            conversacion.setId(id);
        }
    }
    private void putInformation(String nombre, String apellidos, int numero){


    }

    private void init() {
        btSend = findViewById(R.id.btSend);
        etTexto = findViewById(R.id.etTexto);
        svScroll = findViewById(R.id.svScroll);
        tvTexto = findViewById(R.id.tvTexto);
        baseDatos = FirebaseDatabase.getInstance();
        references = baseDatos.getReference();
        if(startBot()) {
            setEvents();
        }
    }
    private void guardarContactoFirebase(Chat chat){
        Map<String, Object> saveItem = new HashMap<>();
        String key = references.child("chat").push().getKey();
        saveItem.put("/chatKey/" + key + "/", chat.toMap());
        references.updateChildren(saveItem);
        saveItem.put("/chat/" + chat.getId() + "/", chat.toMap());
        references.updateChildren(saveItem);
    }
    private void setEvents() {
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String text = getString(R.string.you) + " " + etTexto.getText().toString().trim();
                btSend.setEnabled(false);
                etTexto.setText("");
                tvTexto.append(text + "\n");

                Chat chat = new Chat(0, conversacion.getId(), getString(R.string.you), text);
                gestor.insertChat(chat);

                VerChat.IniciaChat inicia = new VerChat.IniciaChat(text);
                inicia.execute();
            }
        });
    }

    private boolean startBot() {
        boolean result = true;
        String initialMessage;
        factory = new ChatterBotFactory();
        try {
            bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            botSession = bot.createSession();
            initialMessage = getString(R.string.messageConnected) + "\n";
        } catch(Exception e) {
            initialMessage = getString(R.string.messageException) + "\n" + getString(R.string.exception) + " " + e.toString();
            result = false;
        }
        tvTexto.setText(initialMessage);
        return result;
    }


    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private List<Chat> getChats(){
        String where = Contrato.TablaChat.COLUMN_ID_CONVERSACION + "= ?";
        String[] condicion = {String.valueOf(conversacion.getId())};
        return gestor.getAllChat(where, condicion);
        //return gestor.getAllChat();
    }


    private class IniciaChat extends AsyncTask<String, String, String > {

        private String text;
        private String respuesta;

        public IniciaChat(String text){
            this.text = text;
        }

        // Lo que se hace aquí, se puede hacer en el constructor
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // No es Hilo, se usa antes de ejecutar antes de empezar
        }

        @Override
        protected String doInBackground(String... string) {
            // Método similar a run de thread
            try {
                respuesta = getString(R.string.bot) + " " + botSession.think(text);
            } catch (final Exception e) {
                respuesta = getString(R.string.exception) + " " + e.toString();
            }
            return respuesta;
        }

        // No es de la clase Hilo.
        // Se ejecuta durante la ejecución del hilo
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        /*
         * No es de Hilo, se ejecuta al finalizar
         * Recibe como parámetro el resultado de doInBackground
         */
        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);

            Chat chat = new Chat(0, conversacion.getId(), getString(R.string.bot), string);
            gestor.insertChat(chat);

            etTexto.requestFocus();
            tvTexto.append(string + "\n");
            svScroll.fullScroll(View.FOCUS_DOWN);
            btSend.setEnabled(true);
            hideKeyboard();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }



}
