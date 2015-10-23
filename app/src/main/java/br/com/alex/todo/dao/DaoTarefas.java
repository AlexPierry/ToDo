package br.com.alex.todo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.sql.Date;
import java.util.ArrayList;

import br.com.alex.todo.adapter.TarefasAdapter;
import br.com.alex.todo.model.Tarefa;
import br.com.alex.todo.sqlhelper.SqlConstants;
import br.com.alex.todo.sqlhelper.SqlHelper;

/**
 * Created by ALEX-NOTE on 20/10/2015.
 */
public class DaoTarefas {
    private SqlHelper dbHelper;
    private SQLiteDatabase db;

    public DaoTarefas(Context ctx){
        dbHelper = new SqlHelper(ctx, SqlConstants.Database, 2, SqlConstants.SQLCreates, SqlConstants.SQLDelete);
    }

    public long Insert(Tarefa tarefa){
        db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("titulo", tarefa.titulo);
        valores.put("descricao", tarefa.descricao);
        valores.put("data", tarefa.dataAlarme);

        long itemId = db.insert("tarefas", null, valores);
        db.close();
        return itemId;
    }

    public boolean Delete(long tarefaId){
        db = dbHelper.getWritableDatabase();
        int rowsAffected = db.delete("tarefas", "id = ?", new String[]{String.valueOf(tarefaId)});
        db.close();
        return rowsAffected > 0;
    }

    public ArrayAdapter<Tarefa> GetLista(Context ctx){
        db = dbHelper.getWritableDatabase();
        ArrayList<Tarefa> arrayOfTarefas = new ArrayList<Tarefa>();
        TarefasAdapter adapter = new TarefasAdapter(ctx, arrayOfTarefas);

        Cursor c = db.query("tarefas", new String[]{"id, titulo, descricao, data"}, "", null, null, null, null);

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++){
            Tarefa tarefa = new Tarefa();
            tarefa.id = c.getLong(0);
            tarefa.titulo = c.getString(1);
            tarefa.descricao = c.getString(2);
            tarefa.dataAlarme = Long.valueOf(c.getString(3));
            adapter.add(tarefa);
            c.moveToNext();
        }
        db.close();
        return adapter;
    }
}
