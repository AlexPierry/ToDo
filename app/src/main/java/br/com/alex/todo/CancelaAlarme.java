package br.com.alex.todo;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import br.com.alex.todo.dao.DaoTarefas;
import br.com.alex.todo.model.Tarefa;

/**
 * Created by ALEX-NOTE on 21/10/2015.
 */
public class CancelaAlarme extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Tarefa tarefa = (Tarefa) intent.getExtras().getSerializable("tarefa");
        nm.cancel((int)tarefa.id);

        DaoTarefas dao = new DaoTarefas(context);
        dao.Delete(tarefa.id);
        Toast.makeText(context, "Alarme removido", Toast.LENGTH_LONG).show();
        context.getApplicationContext().sendBroadcast(new Intent("ATUALIZAR_LISTA"));
    }
}
