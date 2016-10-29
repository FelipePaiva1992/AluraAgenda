package br.com.code4u.agenda.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by felipepaiva on 29/10/16.
 */

public class Prova implements Serializable{

    public Prova(String materia, String date, List<String> topicos) {
        this.materia = materia;
        this.date = date;
        this.topicos = topicos;
    }

    private String materia;
    private String date;
    private List<String> topicos;

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<String> topicos) {
        this.topicos = topicos;
    }

    @Override
    public String toString() {
        return this.materia;
    }
}
