package br.com.alex.todo;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import br.com.alex.todo.dao.DaoTarefas;

/**
 * Created by ALEX-NOTE on 21/10/2015.
 */
public class CancelaAlarme extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        nm.cancel((int)intent.getExtras().getLong("tarefaId"));

        long tarefaId = intent.getExtras().getLong("tarefaId");
        DaoTarefas dao = new DaoTarefas(context);
        dao.Delete(tarefaId);
        Toast.makeText(context, "Alarme removido", Toast.LENGTH_LONG).show();
        context.getApplicationContext().sendBroadcast(new Intent("ATUALIZAR_LISTA"));
    }
}
