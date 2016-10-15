package br.com.thesource.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import br.com.thesource.agenda.adapter.AlunoAdapter;
import br.com.thesource.agenda.dao.AlunoDao;
import br.com.thesource.agenda.modelo.Aluno;

public class AlunosActivity extends AppCompatActivity {

    AlunoDao dao = new AlunoDao(this);
    ListView listaAlunos;
    RelativeLayout content_alunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                Intent goForm = new Intent(AlunosActivity.this, FormularioActivity.class);
                goForm.putExtra("aluno", aluno);
                startActivity(goForm);
            }
        });

        /*listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                Snackbar.make(content_alunos, "Aluno " + aluno.getNome() + " clicado longo!", Snackbar.LENGTH_LONG).show();

                //true = consome o evento(sobrescreve outros eventos)
                //false = ná oconsome o evento (aparece o Snackbar e o menu de contexto)
                return true;
            }
        });*/


        content_alunos = (RelativeLayout) findViewById(R.id.content_alunos);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goForm = new Intent(AlunosActivity.this, FormularioActivity.class);
                startActivity(goForm);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    private void carregaLista() {
        dao = new AlunoDao(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoAdapter adapter = new AlunoAdapter(alunos,this);




        listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        MenuItem visitar = menu.add("Visitar site");
        Intent goNavegador = new Intent(Intent.ACTION_VIEW);

        String site = aluno.getSite();
        if (!site.toUpperCase().startsWith("HTTP://")) {
            site = "http://" + site;
        }

        goNavegador.setData(Uri.parse(site));
        visitar.setIntent(goNavegador);

        MenuItem ligar = menu.add("Ligar");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(AlunosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AlunosActivity.this, new String[]{Manifest.permission.CALL_PHONE},123);
                }else{
                    Intent goLigar = new Intent(Intent.ACTION_CALL);
                    goLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(goLigar);
                }
                return false;
            }
        });


        MenuItem enviarSms = menu.add("Enviar SMS");
        Intent goSMS = new Intent(Intent.ACTION_VIEW);
        goSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        enviarSms.setIntent(goSMS);

        MenuItem verMapa = menu.add("Ver Mapa");
        Intent goMapa = new Intent(Intent.ACTION_VIEW);
        goMapa.setData(Uri.parse("geo: 0,0?q=" + aluno.getEndereco()));
        verMapa.setIntent(goMapa);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDao dao = new AlunoDao(AlunosActivity.this);
                dao.deleta(aluno);
                dao.close();
                Snackbar.make(content_alunos, "Aluno " + aluno.getNome() + " deletado!", Snackbar.LENGTH_LONG).show();
                carregaLista();
                return false;
            }
        });
    }
}
