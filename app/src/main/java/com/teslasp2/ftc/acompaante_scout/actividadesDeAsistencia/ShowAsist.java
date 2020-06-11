package com.teslasp2.ftc.acompaante_scout.actividadesDeAsistencia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.adaptadores.AdapterAsistencia;
import com.teslasp2.ftc.acompaante_scout.modelos.Asistencia;

import java.util.ArrayList;

public class ShowAsist extends Fragment
{
    ArrayList<Asistencia> listaAsistencias;
    RecyclerView recycler;
    AdapterAsistencia adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vistaRaiz = inflater.inflate(R.layout.activity_show_asist, container, false);

        recycler = vistaRaiz.findViewById(R.id.rvShowAsist);

        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        //Vacia la lista
        if(listaAsistencias !=null)
            listaAsistencias.clear();

        //Vuelve a llenar la lista con los datos de la base de datos
        listaAsistencias = Asistencia.getAsistsArray();

        //Añade un listener al adaptador para luego aplicarlo al recycler view
        adapter = new AdapterAsistencia();
        adapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                Asistencia asistencia = listaAsistencias.get(recycler.getChildAdapterPosition(view));

                bundle.putSerializable("asistencia",  asistencia);

                Intent i = new Intent(getActivity(), ShowAsistSelected.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        //Añade el adaptador al recycler view
        recycler.setAdapter(adapter);

        return vistaRaiz;
    }
}
