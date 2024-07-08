package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class MyBD extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "registro_usuario";

    private static final String TABLE_USUARIO = "usuario";
    private static final String TABLE_REGISTRO = "registro";
    private static final String KEY_NOMBRE = "Nombre";
    private static final String KEY_CLAVE = "Clave";
    private static final String KEY_FECHA = "Fecha_de_ingreso";

    public MyBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS registro (id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Fecha_de_ingreso TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Clave TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRO);
        onCreate(db);
    }

    public void insertarUsuario(String nombre, String clave) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, nombre);
        values.put(KEY_CLAVE, clave);
        db.insert(TABLE_USUARIO, null, values);
    }

    public void ingresarRegistro(String nombre, String fecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, nombre);
        values.put(KEY_FECHA, fecha);
        db.insert(TABLE_REGISTRO, null, values);
    }

    public boolean verificarUsuario(String nombre, String clave) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USUARIO, new String[]{KEY_NOMBRE}, KEY_NOMBRE + "=? AND " + KEY_CLAVE + "=?",
                new String[]{nombre, clave}, null, null, null);
        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;
    }

    public Cursor mostrarRegistros() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("registro", new String[]{"id AS _id", "Nombre", "Fecha_de_ingreso"}, null, null, null, null, null);
    }
}
