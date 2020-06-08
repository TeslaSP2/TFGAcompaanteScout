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
import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.adaptadores.AdapterUsuarios;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.util.ArrayList;

public class ShowUsers extends Fragment
{
    ArrayList<Usuarios> listaUsuarios;
    RecyclerView recycler;
    AdapterUsuarios adapter;
    Usuarios usuarioActual;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vistaRaiz = inflater.inflate(R.layout.activity_show_users, container, false);
        recycler = vistaRaiz.findViewById(R.id.rvShowUsers);

        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        usuarioActual = Usuarios.getCurrentUser();

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

        return vistaRaiz;
    }
}
