package br.com.code4u.agenda.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.widget.RelativeLayout;

import java.util.List;

import br.com.code4u.agenda.converter.AlunoConverter;
import br.com.code4u.agenda.dao.AlunoDao;
import br.com.code4u.agenda.modelo.Aluno;
import br.com.code4u.agenda.web.WebClient;

/**
 * Created by felipepaiva on 20/10/16.
 */

public class EnviaAlunosTask extends AsyncTask<Void,Void,String> {

    private Context context;
    private RelativeLayout content_alunos;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context,RelativeLayout content_alunos){
        this.context = context;
        this.content_alunos = content_alunos;
    }

    @Override
    protected String doInBackground(Void... params) {
        AlunoDao alunoDao = new AlunoDao(context);
        List<Aluno> alunos = alunoDao.buscaAlunos();
        alunoDao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converterParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);

        //Snackbar.make(content_alunos, resposta, Snackbar.LENGTH_LONG).show();
        return  resposta;
    }

    @Override
    protected void onPostExecute(String o) {
        dialog.dismiss();
        Snackbar.make(content_alunos, o, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,"Aguarde","Enviando Alunos...",true,true);
    }
}
