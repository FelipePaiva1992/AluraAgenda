package br.com.thesource.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import br.com.thesource.agenda.dao.AlunoDao;
import br.com.thesource.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    FormularioHelper helper;
    AlunoDao dao = new AlunoDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);
        Button btnSalvar = (Button)findViewById(R.id.btnSalvar);

        Intent goForm = getIntent();
        Aluno aluno = (Aluno) goForm.getSerializableExtra("aluno");

        if(aluno != null){
            btnSalvar.setText("Editar");
            helper.preencheFormulario(aluno);
        }



        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno aluno = helper.pegaAluno();

                if(aluno.getId() != null){
                    dao.edita(aluno);
                }else{
                    dao.insere(aluno);
                }
                dao.close();
                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


}
