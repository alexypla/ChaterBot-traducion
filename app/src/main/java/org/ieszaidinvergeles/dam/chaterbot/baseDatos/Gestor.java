package org.ieszaidinvergeles.dam.chaterbot.baseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.ieszaidinvergeles.dam.chaterbot.baseDatos.baseDatosChat.AyudanteChat;
import org.ieszaidinvergeles.dam.chaterbot.baseDatos.baseDatosConversacion.AyudanteConversacion;
import org.ieszaidinvergeles.dam.chaterbot.objetos.Chat;
import org.ieszaidinvergeles.dam.chaterbot.objetos.Conversacion;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Gestor {

    private AyudanteChat ayudanteChat;
    private AyudanteConversacion ayudanteConversacion;
    private SQLiteDatabase bdChat, bdConversacion;

    public Gestor(Context context){
        this.ayudanteChat = new AyudanteChat(context);
        bdChat = ayudanteChat.getWritableDatabase();
        this.ayudanteConversacion = new AyudanteConversacion(context);
        bdConversacion = ayudanteConversacion.getWritableDatabase();
    }

    public void close(){
        ayudanteConversacion.close();
        ayudanteChat.close();
    }

    public long insertChat(Chat chat){
        return bdChat.insert(Contrato.TablaChat.TABLE_NAME, null, Contrato.contentValuesChat(chat));
    }

    public long insertConversacion(Conversacion conversacion){
        return bdConversacion.insert(Contrato.TablaConversacion.TABLE_NAME, null, Contrato.contentValuesConversacion(conversacion));
    }

    public int deleteChat(Chat chat){
        return deleteChat(chat.getId());
    }

    public int deleteChat(long id){
        String condicion = Contrato.TablaChat._ID + " = ?";
        String[] argumentos = { id + "" };
        return bdChat.delete(Contrato.TablaChat.TABLE_NAME, condicion, argumentos);
    }

    public int deleteConversacion(Conversacion conversacion){
        return deleteChat(conversacion.getId());
    }

    public int deleteConversacion(long id){
        String condicion = Contrato.TablaConversacion._ID + " = ?";
        String[] argumentos = { id + "" };
        return bdConversacion.delete(Contrato.TablaConversacion.TABLE_NAME, condicion, argumentos);
    }

    private Cursor getCursorChat(){
        return getCursorChat(null,null);
    }

    private Cursor getCursorChat(String condicion, String[] argumentos){
        return bdChat.query(
                Contrato.TablaChat.TABLE_NAME,
                null,
                condicion,
                argumentos,
                null,
                null,
                Contrato.TablaChat.COLUMN_ID_CONVERSACION + " desc",
                null
        );

    }

    private Cursor getCursorConversacion(){
        return getCursorConversacion(null,null);
    }

    private Cursor getCursorConversacion(String condicion, String[] argumentos){
        Cursor cursor = bdConversacion.query(
                Contrato.TablaConversacion.TABLE_NAME,
                null,
                condicion,
                argumentos,
                null,
                null,
                Contrato.TablaConversacion.COLUMN_FECHA + " desc",
                null
        );
        return cursor;
    }

    private Chat getRowChat(Cursor cursor){
        Chat chat = new Chat();
        int posicionColumna = cursor.getColumnIndex(Contrato.TablaChat._ID);
        int posicionIdConversacion = cursor.getColumnIndex(Contrato.TablaChat.COLUMN_ID_CONVERSACION);
        int posicionQuien = cursor.getColumnIndex(Contrato.TablaChat.COLUMN_QUIEN);
        int posicionTexto = cursor.getColumnIndex(Contrato.TablaChat.COLUMN_TEXTO);
        chat.setId(cursor.getLong(posicionColumna));
        chat.setIdConversacion(cursor.getLong(posicionIdConversacion));
        chat.setQuien(cursor.getString(posicionQuien));
        chat.setTexto(cursor.getString(posicionTexto));
        return chat;
    }

    private Conversacion getRowConversacion(Cursor cursor){
        Conversacion conversacion = new Conversacion();
        int posicionColumna = cursor.getColumnIndex(Contrato.TablaConversacion._ID);
        int posicionFecha = cursor.getColumnIndex(Contrato.TablaConversacion.COLUMN_FECHA);
        conversacion.setId(cursor.getLong(posicionColumna));
        conversacion.setFecha(cursor.getString(posicionFecha));
        return conversacion;
    }

    public List<Conversacion> getAllConversacion(){
        return getAllConversacion(null);
    }

    public List<Conversacion> getAllConversacion(String condicion){
        List<Conversacion> conversaciones = new ArrayList<>();
        Cursor cursor = getCursorConversacion(condicion,null);
        Conversacion conversacion;

        while(cursor.moveToNext()){
            conversacion = getRowConversacion(cursor);
            conversaciones.add(conversacion);
        }
        return conversaciones;
    }

    public List<Chat> getAllChat(String condicion, String[] argumentos){
        List<Chat> chats = new ArrayList<>();
        Cursor cursor = getCursorChat(condicion, argumentos);
        Chat chat;

        while(cursor.moveToNext()){
            chat = getRowChat(cursor);
            chats.add(chat);
        }
        return chats;
    }

    public List<Chat> getAllChat(){
        return getAllChat(null, null);
    }

    public Chat getChat(long id){
        List<Chat> chats = getAllChat();
        Chat chat = null;

        for(Chat c: chats){
            if(c.getId() == id){
                chat = c;
                break;
            }
        }
        return chat;
    }

}
