package org.ieszaidinvergeles.dam.chaterbot;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

//https://github.com/pierredavidbelanger/chatter-bot-api

public class MainActivity extends AppCompatActivity {

    private Button btSend;
    private EditText etTexto;
    private ScrollView svScroll;
    private TextView tvTexto;
    public String salida;

    private ChatterBot bot;
    private ChatterBotFactory factory;
    private ChatterBotSession botSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btSend = findViewById(R.id.btSend);
        etTexto = findViewById(R.id.etTexto);
        svScroll = findViewById(R.id.svScroll);
        tvTexto = findViewById(R.id.tvTexto);
        if(startBot()) {
            setEvents();
        }
    }

    private void setEvents() {
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String text = getString(R.string.you) + " " + etTexto.getText().toString().trim();
                btSend.setEnabled(false);
                etTexto.setText("");
                salida=text;
                tvTexto.append(navegar() + "\n");
                IniciaChat inicia = new IniciaChat(text);
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
            salida=respuesta;
            return navegar();
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
            etTexto.requestFocus();
            salida=string;
            tvTexto.append(navegar() + "\n");
            svScroll.fullScroll(View.FOCUS_DOWN);
            btSend.setEnabled(true);
            hideKeyboard();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
    private String  navegar(  ){
         String salida2;
        AsyncTask task=new AsyncTask(){

        private String texto = "";
        private String[] txt;
            private String txt2;



        @Override
        protected Object doInBackground (Object[]objects){
            try {

                salida.replace(" ", "%20");
                URL url = new URL("https://www.bing.com/ttranslate?&category=&IG=DDEE8A5D36204999AB52A78F48E30A4E&IID=translator.5034.13");
                URLConnection conexion = url.openConnection();
                conexion.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(
                        conexion.getOutputStream());
                out.write("&text=" + salida + "&from=en&to=es");
                out.close();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conexion.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    Log.v("ZZT", linea);
                    texto = texto + linea;
                }
                in.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            txt = texto.split("translationResponse\":\"");

            return null;
        }

        @Override
        protected void onPostExecute (Object o){
            super.onPostExecute(o);
            txt2 = txt[1].toString().replace("\"}", " ");
            Log.v("ZZT", salida);
            salida=txt2;

        }

        };
        task.execute();
        salida2=salida;
        return salida2;
    }
    
}