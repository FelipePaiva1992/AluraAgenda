package br.com.thesource.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.thesource.agenda.dao.AlunoDao;
import br.com.thesource.agenda.modelo.Aluno;

public class AlunosActivity extends AppCompatActivity {

    AlunoDao dao = new AlunoDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goForm = new Intent(AlunosActivity.this,FormularioActivity.class);
                startActivity(goForm);
            }
        });
    }

    private void carregaLista() {
        List<Aluno> alunos = dao.buscaAlunos();

        ListView listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }
}
