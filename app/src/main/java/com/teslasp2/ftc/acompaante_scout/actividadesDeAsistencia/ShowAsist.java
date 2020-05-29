package com.teslasp2.ftc.acompaante_scout.actividadesDeAsistencia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.adaptadores.AdapterAsistencia;
import com.teslasp2.ftc.acompaante_scout.modelos.Asistencia;

import java.util.ArrayList;

public class ShowAsist extends AppCompatActivity {
    ArrayList<Asistencia> listaAsistencias;
    RecyclerView recycler;
    AdapterAsistencia adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_asist);
        recycler = findViewById(R.id.rvShowAsist);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        if(listaAsistencias !=null)
            listaAsistencias.clear();

        listaAsistencias = Asistencia.getAsistsArray();

        adapter = new AdapterAsistencia();
        adapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
               Asistencia asistencia = listaAsistencias.get(recycler.getChildAdapterPosition(view));

                bundle.putSerializable("asistencia",  asistencia);

                Intent i = new Intent(ShowAsist.this, ShowAsistSelected.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(listaAsistencias !=null)
            listaAsistencias.clear();

        listaAsistencias = Asistencia.getAsistsArray();

        adapter = new AdapterAsistencia();
        adapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                Asistencia asistencia = listaAsistencias.get(recycler.getChildAdapterPosition(view));

                bundle.putSerializable("asistencia",  asistencia);

                Intent i = new Intent(ShowAsist.this, ShowAsistSelected.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        recycler.setAdapter(adapter);
    }
}
