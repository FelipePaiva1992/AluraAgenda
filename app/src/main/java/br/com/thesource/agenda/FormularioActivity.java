package br.com.thesource.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.File;

import br.com.thesource.agenda.dao.AlunoDao;
import br.com.thesource.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 1;
    FormularioHelper helper;
    AlunoDao dao = new AlunoDao(this);
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);
        Button btnSalvar = (Button)findViewById(R.id.btnSalvar);
        FloatingActionButton btnFoto = (FloatingActionButton) findViewById(R.id.formulario_foto);

        final Intent goForm = getIntent();
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

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" +  System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                goFoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(goFoto, CODIGO_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode){
                case CODIGO_CAMERA:
                    if(resultCode == Activity.RESULT_OK) {
                        helper.carregaImamge(caminhoFoto);
                    }
                    break;
            }
    }
}
