package br.com.code4u.agenda;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.code4u.agenda.dao.AlunoDao;
import br.com.code4u.agenda.modelo.Aluno;

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
            btnSalvar.setText(R.string.btn_editar);
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
                Uri photoURI = FileProvider.getUriForFile(FormularioActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",arquivoFoto);
                goFoto.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
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
