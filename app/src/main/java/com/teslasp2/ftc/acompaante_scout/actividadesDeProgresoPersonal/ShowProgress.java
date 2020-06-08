package com.teslasp2.ftc.acompaante_scout.actividadesDeProgresoPersonal;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.adaptadores.AdapterProgreso;
import com.teslasp2.ftc.acompaante_scout.modelos.ProgresoPersonal;

import java.util.ArrayList;

public class ShowProgress extends Fragment {
    ArrayList<ProgresoPersonal> listaprogresos;
    RecyclerView recycler;
    AdapterProgreso adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vistaRaiz = inflater.inflate(R.layout.activity_show_progress, container, false);

        recycler = vistaRaiz.findViewById(R.id.rvShowProgress);

        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        if(listaprogresos !=null)
            listaprogresos.clear();

        listaprogresos = ProgresoPersonal.getProgressArray();

        adapter = new AdapterProgreso();
        adapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                ProgresoPersonal progresoPersonal = listaprogresos.get(recycler.getChildAdapterPosition(view));

                bundle.putSerializable("progresoPersonal",  progresoPersonal);

                Intent i = new Intent(getActivity(), ShowProgressSelected.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        recycler.setAdapter(adapter);

        return vistaRaiz;
    }
}
