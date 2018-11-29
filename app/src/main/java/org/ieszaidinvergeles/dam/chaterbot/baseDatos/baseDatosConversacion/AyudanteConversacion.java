package org.ieszaidinvergeles.dam.chaterbot.baseDatos.baseDatosConversacion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.ieszaidinvergeles.dam.chaterbot.baseDatos.Contrato;

public class AyudanteConversacion extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BD_CONVERSACION.json";

    public AyudanteConversacion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contrato.SQL_CREATE_TABLE_CONVERSACION);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
