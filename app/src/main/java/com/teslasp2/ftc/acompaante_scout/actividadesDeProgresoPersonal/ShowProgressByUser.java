package com.teslasp2.ftc.acompaante_scout.actividadesDeProgresoPersonal;

import android.content.Intent;
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

        //Obtiene el usuario actual basado en los datos guardados en el Login
        usuarioActual = Usuarios.getCurrentUser();

        //Vacia la lista
        if(listaprogresos !=null)
            listaprogresos.clear();

        //Vuelve a llenar la lista con los datos de la base de datos
        listaprogresos = ProgresoPersonal.getProgressByUser(usuario.getId());

        //Añade un listener al adaptador para luego aplicarlo al recycler view
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
        //Añade el adaptador al recycler view
        recycler.setAdapter(adapter);

        //Si el usuario actual es monitor oculta el botón para insertar nuevos progresos
        if(usuarioActual.isMonitor()==0)
            add.setVisibility(View.INVISIBLE);
        else
            add.setVisibility(View.VISIBLE);
    }

    /*
     * Lo mismo que en el onCreate solo que se activa cada vez que se vuelve a la actividad tras
     * modificar o borrar progresos
     */
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

    //Añade el menú de búsqueda a la actividad
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_query, menu);

        MenuItem menuItem = menu.findItem(R.id.search_list);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(this);
        return true;
    }

    /*
     * Se activa cuando se le da a enter en el  teclado al buscar, nos lo obligan ponerlo
     * aunque no nos es necesario en esta ocasión
     */
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    //Se activa cada vez que haya un cambio de texto en la búsqueda
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

    //Envía al usuario a la actividad para añadir Progresos Personales
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
