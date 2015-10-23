package br.com.alex.todo.sqlhelper;

/**
 * Created by ALEX-NOTE on 10/10/2015.
 */
public class SqlConstants {
    public static String[] SQLCreates = {
            "CREATE TABLE IF NOT EXISTS tarefas (id INTEGER PRIMARY KEY ASC, titulo TEXT, descricao TEXT, data TEXT)"
    };

    public static String SQLDelete = "DROP TABLE tarefas";

    public static String Database = "todo";
}
