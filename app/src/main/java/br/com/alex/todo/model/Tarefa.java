package br.com.alex.todo.model;

import java.io.Serializable;

/**
 * Created by ALEX-NOTE on 20/10/2015.
 */
public class Tarefa implements Serializable{
    public long id;
    public String titulo;
    public String descricao;
    public long dataAlarme;

    public Tarefa(){
    }

    public Tarefa(String pTitulo, String pDescricao, long pDataAlarme){
        this.titulo = pTitulo;
        this.descricao = pDescricao;
        this.dataAlarme = pDataAlarme;
    }
}
