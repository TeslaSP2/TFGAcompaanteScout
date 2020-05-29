package com.teslasp2.ftc.acompaante_scout.actividadesDeProgresoPersonal;

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
import com.teslasp2.ftc.acompaante_scout.adaptadores.AdapterProgreso;
import com.teslasp2.ftc.acompaante_scout.modelos.ProgresoPersonal;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.util.ArrayList;

public class ShowProgressByUser extends AppCompatActivity {
    ArrayList<ProgresoPersonal> listaprogresos;
    RecyclerView recycler;
    AdapterProgreso adapter;
    Usuarios usuario, usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_progress);
        recycler = findViewById(R.id.rvShowProgress);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        Bundle bundle = getIntent().getExtras();
        usuario = (Usuarios) bundle.getSerializable("usuario");

        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        usuarioActual = Usuarios.getCurrentUser(preferences);

        if(listaprogresos !=null)
            listaprogresos.clear();

        listaprogresos = ProgresoPersonal.getProgressByUser(usuario.getId());

        adapter = new AdapterProgreso(usuario.getId());
        adapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                ProgresoPersonal progresoPersonal = listaprogresos.get(recycler.getChildAdapterPosition(view));

                bundle.putSerializable("progresoPersonal",  progresoPersonal);

                Intent i = new Intent(ShowProgressByUser.this, ShowProgressSelected.class);
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

        if(listaprogresos !=null)
            listaprogresos.clear();

        listaprogresos = ProgresoPersonal.getProgressByUser(usuario.getId());

        adapter = new AdapterProgreso(usuario.getId());
        adapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                ProgresoPersonal progresoPersonal = listaprogresos.get(recycler.getChildAdapterPosition(view));

                bundle.putSerializable("progresoPersonal",  progresoPersonal);

                Intent i = new Intent(ShowProgressByUser.this, ShowProgressSelected.class);
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
        item.setTitle("Añadir progreso");

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

            Intent i = new Intent(ShowProgressByUser.this, AddProgress.class);
            i.putExtras(bundle);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
