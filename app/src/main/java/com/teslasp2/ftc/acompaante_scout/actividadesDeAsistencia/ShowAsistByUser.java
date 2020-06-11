package com.teslasp2.ftc.acompaante_scout.actividadesDeAsistencia;

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
import com.teslasp2.ftc.acompaante_scout.adaptadores.AdapterAsistencia;
import com.teslasp2.ftc.acompaante_scout.modelos.Asistencia;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.util.ArrayList;

public class ShowAsistByUser extends AppCompatActivity implements SearchView.OnQueryTextListener{
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

        //Vuelve a llenar la lista con los datos de la base de datos
        listaAsistencias = Asistencia.getAsistsByUser(usuario.getId());

        //Añade un listener al adaptador para luego aplicarlo al recycler view
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
        //Añade el adaptador al recycler view
        recycler.setAdapter(adapter);

        //Si el usuario actual es monitor oculta el botón para insertar nuevas asistencias
        if(usuarioActual.isMonitor()==0)
            add.setVisibility(View.INVISIBLE);
        else
            add.setVisibility(View.VISIBLE);
    }

    /*
     * Lo mismo que en el onCreate solo que se activa cada vez que se vuelve a la actividad tras
     * modificar o borrar asistencias
     */
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
        ArrayList<Asistencia> listaBusqueda = new ArrayList<>();

        if(busqueda.equals(""))
        {
            listaBusqueda=listaAsistencias;
        }
        else
        {
            for (Asistencia asistencia: listaAsistencias)
            {
                if(asistencia.getFechaString().contains(busqueda))
                {
                    listaBusqueda.add(asistencia);
                }
            }
        }

        adapter.setListaAsistencias(listaBusqueda);

        return false;
    }

    //Envía al usuario a la actividad para añadir Asistencias
    public void addAsist(View view)
    {
        if(usuarioActual.isMonitor()==1)
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
