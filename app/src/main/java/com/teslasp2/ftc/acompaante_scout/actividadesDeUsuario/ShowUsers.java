package com.teslasp2.ftc.acompaante_scout.actividadesDeUsuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.adaptadores.AdapterUsuarios;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.util.ArrayList;

public class ShowUsers extends AppCompatActivity
{
    ArrayList<Usuarios> listaUsuarios;
    RecyclerView recycler;
    AdapterUsuarios adapter;
    Usuarios usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);
        recycler = findViewById(R.id.rvShowUsers);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        usuarioActual = Usuarios.getCurrentUser(preferences);

        if(listaUsuarios!=null)
            listaUsuarios.clear();

        listaUsuarios = Usuarios.getUsersArray();

        adapter = new AdapterUsuarios();
        adapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                Usuarios usuario = listaUsuarios.get(recycler.getChildAdapterPosition(view));

                bundle.putSerializable("usuario",  usuario);

                Intent i = new Intent(ShowUsers.this, ShowUserSelected.class);
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

        if(listaUsuarios!=null)
            listaUsuarios.clear();

        listaUsuarios = Usuarios.getUsersArray();

        adapter = new AdapterUsuarios();
        adapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                Usuarios usuario = listaUsuarios.get(recycler.getChildAdapterPosition(view));

                bundle.putSerializable("usuario",  usuario);

                Intent i = new Intent(ShowUsers.this, ShowUserSelected.class);
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
        item.setTitle("Añadir usuario");

        if(usuarioActual.isMonitor()==0)
            item.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.addShow)
        {
            Intent i = new Intent(this, AddUser.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
