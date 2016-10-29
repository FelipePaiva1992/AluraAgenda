package br.com.code4u.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.code4u.agenda.modelo.Prova;

public class DetalheProvaActivity extends AppCompatActivity {

    CardView activity_detalhe_prova;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_prova);

        activity_detalhe_prova = (CardView) findViewById(R.id.activity_detalhe_prova);

        Intent intent = getIntent();
        Prova prova = (Prova) intent.getSerializableExtra("prova");


        TextView materia = (TextView) findViewById(R.id.detalhe_materia);
        TextView data = (TextView) findViewById(R.id.detalhe_data);
        ListView lista = (ListView) findViewById(R.id.detalhe_prova_topicos);

        materia.setText(prova.getMateria());
        data.setText(prova.getDate());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,prova.getTopicos());
        lista.setAdapter(adapter);
    }
}
