package br.com.code4u.agenda.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.code4u.agenda.gps.Localizador;
import br.com.code4u.agenda.dao.AlunoDao;
import br.com.code4u.agenda.modelo.Aluno;

/**
 * Created by felipepaiva on 29/10/16.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        LatLng minhaCasa = pregarCoordenadaDoEndereco("Rua Mauricio Jacquey, 268");
        if(minhaCasa != null){
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(minhaCasa,2);
            googleMap.moveCamera(update);
        }

        AlunoDao alunoDao = new AlunoDao(getContext());
        for(Aluno aluno:alunoDao.buscaAlunos()){
            LatLng coordenada = pregarCoordenadaDoEndereco(aluno.getEndereco());
            if (coordenada != null) {
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }

        alunoDao.close();

        new Localizador(getContext(),googleMap);

    }

    private LatLng pregarCoordenadaDoEndereco(String endereco){
        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> resultados = geocoder.getFromLocationName(endereco, 1);

            if(!resultados.isEmpty()){
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(),resultados.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
