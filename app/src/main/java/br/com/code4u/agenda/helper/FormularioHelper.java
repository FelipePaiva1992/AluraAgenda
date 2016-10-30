package br.com.code4u.agenda.helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.code4u.agenda.R;
import br.com.code4u.agenda.modelo.Aluno;

/**
 * Created by felipepaiva on 14/10/16.
 */

public class FormularioHelper{

    private final EditText nome;
    private final EditText endereco;
    private final EditText telefone;
    private final EditText site;
    private final RatingBar rating;
    private final ImageView foto;

    private Aluno aluno;

    public FormularioHelper(Activity formularioActivity){
        nome = (EditText) formularioActivity.findViewById(R.id.formulario_nome);
        endereco = (EditText) formularioActivity.findViewById(R.id.formulario_endereco);
        telefone = (EditText) formularioActivity.findViewById(R.id.formulario_telefone);
        site = (EditText) formularioActivity.findViewById(R.id.formulario_site);
        rating = (RatingBar) formularioActivity.findViewById(R.id.formulario_rating);
        foto = (ImageView) formularioActivity.findViewById(R.id.imageView);
        aluno = new Aluno();
    }

    public Aluno pegaAluno(){
        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setNota(Double.valueOf(rating.getProgress()));
        aluno.setCaminhoFoto((String) foto.getTag());
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        nome.setText(aluno.getNome());
        endereco.setText(aluno.getEndereco());
        telefone.setText(aluno.getTelefone());
        site.setText(aluno.getSite());
        rating.setProgress(aluno.getNota().intValue());
        carregaImamge(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    public void carregaImamge(String caminhoFoto){
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            foto.setImageBitmap(bitmapReduzido);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
            foto.setTag(caminhoFoto);
        }
    }
}
