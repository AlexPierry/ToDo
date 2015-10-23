package br.com.alex.todo.sqlhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ALEX-NOTE on 10/10/2015.
 */
public class SqlHelper extends SQLiteOpenHelper {
    private String[] scriptSQLCreate;
    private String scriptSQLDelete;

    public SqlHelper(Context context, String nomeBanco, int versaoBanco, String[] scriptCreate, String scriptDelete){
        super(context, nomeBanco, null, versaoBanco);

        this.scriptSQLCreate = scriptCreate;
        this.scriptSQLDelete = scriptDelete;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        int qtdScript = scriptSQLCreate.length;

        for (int i = 0; i < qtdScript; i++){
            String sql = scriptSQLCreate[i];
            db.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(scriptSQLDelete);
        onCreate(db);
    }
}
