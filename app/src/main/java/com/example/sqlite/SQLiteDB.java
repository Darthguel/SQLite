package com.example.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SQLiteDB extends SQLiteOpenHelper {
    public SQLiteDB(Context c) {
        super(c, "dbUsuarios.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table Usuario ( " +
                "nombre varchar(100)," +
                "apellido varchar(100)," +
                "edad integer," +
                "sexo String(20) )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int a, int b) { }

    public void insertarDatos(String nom, String ape, int ed, String sex) {
        SQLiteDatabase db1 = getWritableDatabase();
        String sql = String.format(
            "insert into Usuario (nombre, apellido, edad, sexo) " +
            "values ('%s', '%s', %d, '%s');", nom, ape, ed, sex);
        db1.execSQL(sql);
        //db1.close();
    }

    public Cursor seleccionarUsuarios() {
        SQLiteDatabase db1 = getReadableDatabase();
        Cursor c = db1.rawQuery("select * from Usuario Where Nombre = 'Miguel'", null);
        //db1.close();
        return c;
    }

    public void modificarDatos(Usuario usuario) {
        SQLiteDatabase db1 = getWritableDatabase();
        String sql = String.format("UPDATE usuario SET nombre='%s', apellido='%s', edad=%d, sexo='%s' " +
            "WHERE nombre='%s';", usuario.getNombre(), usuario.getApellido(), usuario.getEdad(), usuario.getSexo(), usuario.getNombre());
        db1.execSQL(sql);
        //db1.close();
    }

    public void eliminarUsuario(Usuario usuario) {
        SQLiteDatabase db1 = getWritableDatabase();
        String sql = String.format("delete from usuario where nombre = '" + usuario.getNombre() + "' and apellido = '" + usuario.getApellido() + "'");
        db1.execSQL(sql);
        //db1.close();
    }

    public ArrayList<Usuario> cargaArray() {
        SQLiteDatabase db1 = getReadableDatabase();
        Cursor c;
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        c = db1.rawQuery("select * from Usuario", null);
        if (c.moveToFirst()) {
            do {
                usuarios.add(new Usuario(c.getString(0), c.getString(1), c.getInt(2), c.getString(3)));
            } while(c.moveToNext());
        }
        //db1.close();
        return usuarios;
    }

    public ArrayList llenaLista(){
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase db1 = getWritableDatabase();
        String sql = "SELECT * FROM USUARIO";
        Cursor registro = db1.rawQuery(sql, null);
        if (registro.moveToFirst()){
            do {
                lista.add(registro.getString(1));
            }while(registro.moveToNext());
        }
        //db1.close();
        return lista;
    }
}
