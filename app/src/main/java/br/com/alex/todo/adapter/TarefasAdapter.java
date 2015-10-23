package br.com.alex.todo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.alex.todo.R;
import br.com.alex.todo.model.Tarefa;

/**
 * Created by ALEX-NOTE on 20/10/2015.
 */
public class TarefasAdapter extends ArrayAdapter<Tarefa> {
    public TarefasAdapter(Context context, ArrayList<Tarefa> tarefas){
        super(context, 0, tarefas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tarefa tarefa = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tarefa, parent, false);
        }

        TextView tvTitulo = (TextView) convertView.findViewById(R.id.tvTitulo);
        TextView tvDescricao = (TextView) convertView.findViewById(R.id.tvDescricao);
        TextView tvData = (TextView) convertView.findViewById(R.id.tvData);
        TextView tvId = (TextView) convertView.findViewById(R.id.tvId);

        SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        tvTitulo.setText(tarefa.titulo);
        tvDescricao.setText(tarefa.descricao);
        tvData.setText(dtFormat.format(tarefa.dataAlarme));
        tvId.setText(String.valueOf(tarefa.id));

        return convertView;
    }
}
