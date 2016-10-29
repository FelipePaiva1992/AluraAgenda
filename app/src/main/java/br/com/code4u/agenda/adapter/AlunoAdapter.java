package br.com.code4u.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.code4u.agenda.R;
import br.com.code4u.agenda.modelo.Aluno;

/**
 * Created by felipepaiva on 15/10/16.
 */

public class AlunoAdapter extends BaseAdapter {


    private final List<Aluno> alunos;
    private final Context context;

    public AlunoAdapter(List<Aluno> alunos, Context context){
        this.alunos = alunos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if(view == null){
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        nome.setText(aluno.getNome());

        TextView telefone = (TextView) view.findViewById(R.id.item_telefone);
        telefone.setText(aluno.getTelefone());

        TextView endereco = (TextView) view.findViewById(R.id.item_endereco);
        if(endereco != null){
            endereco.setText(aluno.getEndereco());
        }

        TextView site = (TextView) view.findViewById(R.id.item_site);
        if(site != null){
            site.setText(aluno.getSite());
        }

        ImageView foto = (ImageView) view.findViewById(R.id.item_foto);
        String caminhoFoto = aluno.getCaminhoFoto();
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            foto.setImageBitmap(bitmapReduzido);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
