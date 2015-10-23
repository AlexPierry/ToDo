package br.com.alex.todo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import br.com.alex.todo.dao.DaoTarefas;
import br.com.alex.todo.model.Tarefa;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DaoTarefas dao = new DaoTarefas(context);
                ArrayAdapter<Tarefa> listaTarefas = dao.GetLista(context);

                for (int i = 0; i < listaTarefas.getCount(); i++)
                {
                    Tarefa tarefa = listaTarefas.getItem(i);
                    if (tarefa.dataAlarme > System.currentTimeMillis()) {
                        Bundle bundle = new Bundle();
                        bundle.putLong("tarefaId", tarefa.id);
                        bundle.putString("tarefaTitulo", tarefa.titulo);
                        bundle.putString("tarefaDescricao", tarefa.descricao);
                        bundle.putString("tarefaHora", String.valueOf(tarefa.dataAlarme));

                        Intent intencao = new Intent(context, NotificaAlarme.class);
                        intencao.putExtras(bundle);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intencao, 0);

                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, tarefa.dataAlarme, pendingIntent);
                    }
                }
            }
        }).start();
    }
}
