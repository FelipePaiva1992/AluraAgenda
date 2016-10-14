package br.com.thesource.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.thesource.agenda.modelo.Aluno;

/**
 * Created by felipepaiva on 14/10/16.
 */

public class AlunoDao extends SQLiteOpenHelper{


    public AlunoDao(Context context) {
        super(context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Aluno ( id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Aluno";
        db.execSQL(sql);
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());

        db.insert("Aluno", null, dados);
    }

    public List<Aluno> buscaAlunos() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id,nome,endereco,telefone,site,nota FROM Aluno";
        Cursor c = db.rawQuery(sql, null);
        List<Aluno> alunos = new ArrayList<>();
        Aluno aluno;
        while (c.moveToNext()){
            aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            alunos.add(aluno);
        }
        c.close();
        return alunos;
    }
}
