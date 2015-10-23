package br.com.alex.todo;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

import br.com.alex.todo.dao.DaoTarefas;
import br.com.alex.todo.model.Tarefa;

public class TarefaActivity extends AppCompatActivity {
    static final int DATE_DIALOG_ID = 1;
    static final int TIME_DIALOG_ID = 2;

    private int ano, mes, dia, hora, minuto;
    TextView txData;
    TextView txHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);

        txData = (TextView)findViewById(R.id.txData);
        txHora = (TextView)findViewById(R.id.txHora);

        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
        ano = cal.get(Calendar.YEAR);
        mes = cal.get(Calendar.MONTH);
        dia = cal.get(Calendar.DAY_OF_MONTH);
        hora = cal.get(Calendar.HOUR_OF_DAY);
        minuto = cal.get(Calendar.MINUTE);

        txData.setText(String.format("%02d", dia) + "/" + String.format("%02d", (mes + 1)) + "/" + ano);
        txHora.setText(String.format("%02d", hora) + ":" + String.format("%02d", minuto));
    }

    public void createDateDialog(View v){
        showDialog(DATE_DIALOG_ID);
    }

    public void createTimeDialog(View v){
        showDialog(TIME_DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATE_DIALOG_ID) {
            DatePickerDialog dp = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, datePickerListener, ano, mes, dia);
            dp.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return dp;
        }
        else if (id == TIME_DIALOG_ID) {
            TimePickerDialog tp = new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, timePickerListener, hora, minuto, true);
            return tp;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ano = year;
            mes = monthOfYear + 1;
            dia = dayOfMonth;

            txData.setText(String.format("%02d", dia) + "/" + String.format("%02d", mes) + "/" + ano);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hora = hourOfDay;
            minuto = minute;

            txHora.setText(String.format("%02d", hora) + ":" + String.format("%02d", minute));
        }
    };


    public void salvar(View view) throws ParseException {
        DaoTarefas dao = new DaoTarefas(this);

        Tarefa tarefa = new Tarefa();
        tarefa.titulo = ((EditText)findViewById(R.id.edtTitulo)).getText().toString();
        tarefa.descricao = ((EditText)findViewById(R.id.edtDescricao)).getText().toString();

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
        cal.set(ano, mes, dia, hora, minuto, 0);

        tarefa.dataAlarme = cal.getTime().getTime();
        tarefa.id = dao.Insert(tarefa);

        this.registrarAlarme(tarefa);

        finish();
    }

    private void registrarAlarme(Tarefa tarefa){
        Intent intencao = new Intent(this, NotificaAlarme.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("tarefa", tarefa);
        intencao.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int)tarefa.id, intencao, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, tarefa.dataAlarme, pendingIntent);
    }



}
