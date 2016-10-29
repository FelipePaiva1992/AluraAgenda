package br.com.code4u.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.code4u.agenda.modelo.Prova;

/**
 * Created by felipepaiva on 29/10/16.
 */

public class ListaProvasFragment extends Fragment {

    List<Prova> provas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List<String> topicos = Arrays.asList("Top1", "Top2");

        provas.add(new Prova("Portugues","13/05/1992", topicos));
        provas.add(new Prova("Matematica","15/05/1992", topicos));

        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(),android.R.layout.simple_list_item_1,provas);

        ListView lista = (ListView) view.findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);

                //Snackbar.make(content_prova, "Clicou na prova de " + prova, Snackbar.LENGTH_LONG).show();

                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.selecionaProva(prova);

            }
        });

        return view;
    }
}
