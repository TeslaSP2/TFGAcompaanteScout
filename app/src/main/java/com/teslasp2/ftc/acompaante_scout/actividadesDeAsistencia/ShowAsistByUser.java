package com.teslasp2.ftc.acompaante_scout.actividadesDeAsistencia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.adaptadores.AdapterAsistencia;
import com.teslasp2.ftc.acompaante_scout.modelos.Asistencia;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.util.ArrayList;

public class ShowAsistByUser extends AppCompatActivity {
    ArrayList<Asistencia> listaAsistencias;
    RecyclerView recycler;
    AdapterAsistencia adapter;
    Usuarios usuario, usuarioActual;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_asist_by_users);
        add = findViewById(R.id.btAddAsistSh);
        recycler = findViewById(R.id.rvShowAsist);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));

        Bundle bundle = getIntent().getExtras();
        usuario = (Usuarios) bundle.getSerializable("usuario");

        //Obtiene el usuario actual basado en los datos guardados en el Login
        usuarioActual = Usuarios.getCurrentUser();

        //Vacia la lista
        if(listaAsistencias !=null)
            listaAsistencias.clear();

        listaAsistencias = Asistencia.getAsistsByUser(usuario.getId());

        adapter = new AdapterAsistencia(usuario.getId());
        adapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                Asistencia asistencia = listaAsistencias.get(recycler.getChildAdapterPosition(view));

                bundle.putSerializable("asistencia",  asistencia);

                Intent i = new Intent(ShowAsistByUser.this, ShowAsistSelected.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        recycler.setAdapter(adapter);

        if(usuarioActual.isMonitor()==0)
            add.setVisibility(View.INVISIBLE);
        else
            add.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        usuarioActual = Usuarios.getCurrentUser();

        if(listaAsistencias !=null)
            listaAsistencias.clear();

        listaAsistencias = Asistencia.getAsistsByUser(usuario.getId());

        adapter = new AdapterAsistencia();
        adapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                Asistencia asistencia = listaAsistencias.get(recycler.getChildAdapterPosition(view));

                bundle.putSerializable("asistencia",  asistencia);

                Intent i = new Intent(ShowAsistByUser.this, ShowAsistSelected.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        recycler.setAdapter(adapter);

        if(usuarioActual.isMonitor()==0)
            add.setVisibility(View.INVISIBLE);
        else
            add.setVisibility(View.VISIBLE);
    }

    public void addAsist(View view)
    {
        if(usuarioActual.isMonitor()==0)
        {
            Bundle bundle = new Bundle();

            bundle.putSerializable("usuario",  usuario);

            Intent i = new Intent(ShowAsistByUser.this, AddAsist.class);
            i.putExtras(bundle);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this,"No tienes permiso para hacer eso", Toast.LENGTH_SHORT).show();
        }
    }
}
