package br.com.alex.todo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import br.com.alex.todo.model.Tarefa;

/**
 * Created by ALEX-NOTE on 22/10/2015.
 */
public class NotificaAlarme extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, CancelaAlarme.class);
        newIntent.putExtras(intent.getExtras());

        Tarefa tarefa = (Tarefa)intent.getExtras().getSerializable("tarefa");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)tarefa.id, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification nt = new Notification.Builder(context)
                .setTicker("ToDo - Lembrete")
                .setContentTitle(tarefa.titulo)
                .setContentText(tarefa.descricao)
                .setSmallIcon(R.mipmap.todo_icon)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{500, 500, 500, 500, 500})
                .setLights(Color.BLUE, 2000, 2000)
                .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.alarm_sound))
                .setWhen(System.currentTimeMillis())
                .build();

        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        nm.notify((int)tarefa.id , nt);
    }
}
