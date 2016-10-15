package br.com.thesource.agenda;

import android.app.Activity;
import android.widget.EditText;
import android.widget.RatingBar;

import br.com.thesource.agenda.modelo.Aluno;

/**
 * Created by felipepaiva on 14/10/16.
 */

public class FormularioHelper{

    private final EditText nome;
    private final EditText endereco;
    private final EditText telefone;
    private final EditText site;
    private final RatingBar rating;

    private Aluno aluno;

    public FormularioHelper(Activity formularioActivity){
        nome = (EditText) formularioActivity.findViewById(R.id.formulario_nome);
        endereco = (EditText) formularioActivity.findViewById(R.id.formulario_endereco);
        telefone = (EditText) formularioActivity.findViewById(R.id.formulario_telefone);
        site = (EditText) formularioActivity.findViewById(R.id.formulario_site);
        rating = (RatingBar) formularioActivity.findViewById(R.id.formulario_rating);
        aluno = new Aluno();
    }

    public Aluno pegaAluno(){
        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setNota(Double.valueOf(rating.getProgress()));
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        nome.setText(aluno.getNome());
        endereco.setText(aluno.getEndereco());
        telefone.setText(aluno.getTelefone());
        site.setText(aluno.getSite());
        rating.setProgress(aluno.getNota().intValue());
        this.aluno = aluno;
    }
}
