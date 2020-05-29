package com.teslasp2.ftc.acompaante_scout.actividadesDeAsistencia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_asist);
        recycler = findViewById(R.id.rvShowAsist);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        Bundle bundle = getIntent().getExtras();
        usuario = (Usuarios) bundle.getSerializable("usuario");

        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        usuarioActual = Usuarios.getCurrentUser(preferences);

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        usuarioActual = Usuarios.getCurrentUser(preferences);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show, menu);

        MenuItem item = findViewById(R.id.addShow);
        item.setTitle("Añadir asistencia");

        if(usuarioActual.isMonitor()==0)
        {
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.addShow)
        {
            Bundle bundle = new Bundle();

            bundle.putSerializable("usuario",  usuario);

            Intent i = new Intent(ShowAsistByUser.this, AddAsist.class);
            i.putExtras(bundle);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
