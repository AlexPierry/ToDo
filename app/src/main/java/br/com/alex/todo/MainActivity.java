package br.com.alex.todo;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.alex.todo.dao.DaoTarefas;
import br.com.alex.todo.model.Tarefa;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver atualizarListaReceiver;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lista = (ListView)findViewById(R.id.listView);
        registerForContextMenu(lista);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TarefaActivity.class);
                startActivity(intent);
            }
        });

        this.LoadTarefas();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete_id :
                TextView tvId = (TextView) info.targetView.findViewById(R.id.tvId);
                excluirItemDaLista(Long.parseLong(tvId.getText().toString()));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void excluirItemDaLista(long tarefaId){
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel((int)tarefaId);

        DaoTarefas dao = new DaoTarefas(MainActivity.this);
        dao.Delete(tarefaId);
        lista.setAdapter(dao.GetLista(MainActivity.this));
    }

    @Override
    protected void onPause() {
        unregisterReceiver(atualizarListaReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarListaReceiver = new AtualizarListaReceiver();
        registerReceiver(atualizarListaReceiver, new IntentFilter("ATUALIZAR_LISTA"));
        this.LoadTarefas();
    }

    public class AtualizarListaReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            LoadTarefas();
        }
    }

    private void LoadTarefas(){
        DaoTarefas dao = new DaoTarefas(MainActivity.this);
        lista.setAdapter(dao.GetLista(MainActivity.this));
    }
}
