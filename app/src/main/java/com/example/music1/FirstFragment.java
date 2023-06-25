package com.example.music1;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import com.example.music1.MusicasAdapter;
import com.example.music1.BDSQLiteHelper;
import com.example.music1.databinding.FragmentFirstBinding;
import com.example.music1.Musica;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private BDSQLiteHelper bd;
    ArrayList<Musica> listaMusicas;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        bd = new BDSQLiteHelper(getContext());
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
        listaMusicas = bd.getAllMusicas();

        //ListView lista = (ListView) findViewById(R.id.lvMusicas);

        ListView lista = binding.lvMusicas;

        MusicasAdapter adapter = new MusicasAdapter(getContext(), listaMusicas);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getContext(), EditarMusicaActivity.class);
                intent.putExtra("ID", listaMusicas.get(position).getId());

                intent.putExtra("MusicaCorrente", (CharSequence) listaMusicas.get(position));

                startActivity(intent);
            }
        });
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
