package com.teslasp2.ftc.acompaante_scout.actividadesDeUsuario;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.adaptadores.AdapterUsuarios;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ShowUsers extends Fragment
{
    ArrayList<Usuarios> listaUsuarios;
    RecyclerView recycler;
    AdapterUsuarios adapter;
    Usuarios usuarioActual;
    Button add;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vistaRaiz = inflater.inflate(R.layout.activity_show_users, container, false);

        add = vistaRaiz.findViewById(R.id.btAddUserShUser);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usuarioActual.isMonitor()==0)
                {
                    Intent i = new Intent(getActivity(), AddUser.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getContext(),"No tienes permiso para hacer eso", Toast.LENGTH_SHORT);
                }
            }
        });
        recycler = vistaRaiz.findViewById(R.id.rvShowUsers);

        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        SharedPreferences preferences = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
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

                Intent i = new Intent(getActivity(), ShowUserSelected.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        recycler.setAdapter(adapter);

        if(usuarioActual.isMonitor()==0)
            add.setVisibility(View.INVISIBLE);
        else
            add.setVisibility(View.VISIBLE);

        return vistaRaiz;
    }
}
