package com.teslasp2.ftc.acompaante_scout.actividadesDeProgresoPersonal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.adaptadores.AdapterProgreso;
import com.teslasp2.ftc.acompaante_scout.modelos.ProgresoPersonal;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.util.ArrayList;

public class ShowProgressByUser extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ArrayList<ProgresoPersonal> listaprogresos;
    RecyclerView recycler;
    AdapterProgreso adapter;
    Usuarios usuario, usuarioActual;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_progress_by_users);
        add = findViewById(R.id.btAddProgSh);
        recycler = findViewById(R.id.rvShowProgress);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        Bundle bundle = getIntent().getExtras();
        usuario = (Usuarios) bundle.getSerializable("usuario");

        usuarioActual = Usuarios.getCurrentUser();

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

        if(usuarioActual.isMonitor()==0)
            add.setVisibility(View.INVISIBLE);
        else
            add.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        usuarioActual = Usuarios.getCurrentUser();

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
        getMenuInflater().inflate(R.menu.menu_query, menu);

        MenuItem menuItem = menu.findItem(R.id.search_list);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        String busqueda = s.toLowerCase();
        ArrayList<ProgresoPersonal> listaBusqueda = new ArrayList<>();

        if(busqueda.equals(""))
        {
            listaBusqueda=listaprogresos;
        }
        else
        {
            for (ProgresoPersonal progresoPersonal: listaprogresos)
            {
                if(progresoPersonal.getNombre_progreso().toLowerCase().contains(busqueda))
                {
                    listaBusqueda.add(progresoPersonal);
                }
            }
        }

        adapter.setListaProgresos(listaBusqueda);

        return false;
    }

    public void addProg(View view)
    {
        if(usuarioActual.isMonitor()==1)
        {
            Bundle bundle = new Bundle();

            bundle.putSerializable("usuario",  usuario);

            Intent i = new Intent(ShowProgressByUser.this, AddProgress.class);
            i.putExtras(bundle);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this,"No tienes permiso para hacer eso", Toast.LENGTH_SHORT).show();
        }
    }
}
