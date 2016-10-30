package br.com.code4u.agenda;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import br.com.code4u.agenda.fragments.DetalheProvaFragment;
import br.com.code4u.agenda.fragments.ListaProvasFragment;
import br.com.code4u.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    List<Prova> provas = new ArrayList<>();

    CardView content_prova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.frame_principal, new ListaProvasFragment());
        if (estaNoModoPaisagem()) {
            tx.replace(R.id.frame_secundario, new DetalheProvaFragment());
        }
        tx.commit();

    }

    private boolean estaNoModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(!estaNoModoPaisagem()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            DetalheProvaFragment detalheFragment = new DetalheProvaFragment();

            Bundle argumentos = new Bundle();
            argumentos.putSerializable("prova",prova);
            detalheFragment.setArguments(argumentos);

            transaction.replace(R.id.frame_principal, detalheFragment);

            transaction.addToBackStack(null);
            transaction.commit();
        }else{
            DetalheProvaFragment detalheFragment = (DetalheProvaFragment) fragmentManager.findFragmentById(R.id.frame_secundario);
            detalheFragment.populaCamposCom(prova);
        }
    }
}
