package org.ieszaidinvergeles.dam.chaterbot.baseDatos;

import android.content.ContentValues;
import android.provider.BaseColumns;

import org.ieszaidinvergeles.dam.chaterbot.objetos.Chat;
import org.ieszaidinvergeles.dam.chaterbot.objetos.Conversacion;

public class Contrato {

    public static final String SQL_CREATE_TABLE_CHAT =
            "create table " + TablaChat.TABLE_NAME + " (" +
                    TablaChat._ID + " integer primary key autoincrement," +
                    TablaChat.COLUMN_ID_CONVERSACION + " integer," +
                    TablaChat.COLUMN_QUIEN +" text," +
                    TablaChat.COLUMN_TEXTO + " text)";


    private static final String SQL_DELETE_TABLE_CHAT =
            "drop table if exists " + TablaChat.TABLE_NAME;


    public static final String SQL_CREATE_TABLE_CONVERSACION =
            "create table " + TablaConversacion.TABLE_NAME + " (" +
                    TablaConversacion._ID + " integer primary key autoincrement," +
                    TablaConversacion.COLUMN_FECHA + " text)";


    private static final String SQL_DELETE_TABLE_CONVERSACION =
            "drop table if exists " + Contrato.TablaConversacion.TABLE_NAME;

    private Contrato(){}

    public static class TablaChat implements BaseColumns {
        public static final String TABLE_NAME = "chat";
        public static final String COLUMN_ID_CONVERSACION = "idConversacion";
        public static final String COLUMN_QUIEN = "quien";
        public static final String COLUMN_TEXTO = "texto";
    }

    public static class TablaConversacion implements BaseColumns {
        public static final String TABLE_NAME = "conversacion";
        public static final String COLUMN_FECHA = "fecha";
    }

    public static  ContentValues contentValuesChat (Chat chat){
        ContentValues cv = new ContentValues();
//        cv.put(TablaChat._ID, chat.getId());
        cv.put(TablaChat.COLUMN_ID_CONVERSACION, chat.getIdConversacion());
        cv.put(TablaChat.COLUMN_QUIEN, chat.getQuien());
        cv.put(TablaChat.COLUMN_TEXTO, chat.getTexto());
        return cv;
    }

    public static  ContentValues contentValuesConversacion (Conversacion conversacion){
        ContentValues cv = new ContentValues();
        cv.put(TablaConversacion.COLUMN_FECHA, String.valueOf(conversacion.getFecha()));
        return cv;
    }

}
