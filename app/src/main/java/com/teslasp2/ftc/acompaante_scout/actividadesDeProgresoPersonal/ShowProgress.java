package com.teslasp2.ftc.acompaante_scout.actividadesDeProgresoPersonal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.adaptadores.AdapterProgreso;
import com.teslasp2.ftc.acompaante_scout.modelos.ProgresoPersonal;

import java.util.ArrayList;

public class ShowProgress extends AppCompatActivity {
    ArrayList<ProgresoPersonal> listaprogresos;
    RecyclerView recycler;
    AdapterProgreso adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_progress);
        recycler = findViewById(R.id.rvShowProgress);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

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

                Intent i = new Intent(ShowProgress.this, ShowProgressSelected.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

                Intent i = new Intent(ShowProgress.this, ShowProgressSelected.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        recycler.setAdapter(adapter);
    }
}
