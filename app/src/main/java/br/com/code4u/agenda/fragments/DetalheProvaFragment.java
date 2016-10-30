package br.com.code4u.agenda.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.code4u.agenda.R;
import br.com.code4u.agenda.modelo.Prova;

import static android.R.attr.data;

public class DetalheProvaFragment extends Fragment {


    private TextView materia;
    private TextView dataa;
    private ListView topicos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhe_prova, container, false);


        materia = (TextView) view.findViewById(R.id.detalhe_materia);
        dataa = (TextView) view.findViewById(R.id.detalhe_data);
        topicos = (ListView) view.findViewById(R.id.detalhe_prova_topicos);

        Bundle argumentos = getArguments();
        if(argumentos!= null) {
            Prova prova = (Prova) argumentos.getSerializable("prova");
            populaCamposCom(prova);
        }

        return view;
    }

    public void populaCamposCom(Prova prova){
        materia.setText(prova.getMateria());
        dataa.setText(prova.getDate());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,prova.getTopicos());
        topicos.setAdapter(adapter);
    }

}
