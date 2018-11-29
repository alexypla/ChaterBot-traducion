package org.ieszaidinvergeles.dam.chaterbot.baseDatos.baseDatosChat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.ieszaidinvergeles.dam.chaterbot.baseDatos.Contrato;

public class AyudanteChat extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BD_CHAT.json";

    public AyudanteChat(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contrato.SQL_CREATE_TABLE_CHAT);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
