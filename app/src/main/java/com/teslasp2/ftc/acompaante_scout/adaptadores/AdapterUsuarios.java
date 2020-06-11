package com.teslasp2.ftc.acompaante_scout.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.util.ArrayList;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.ViewHolderUsuarios> implements View.OnClickListener {

    ArrayList<Usuarios> listaUsuarios;
    private View.OnClickListener listener;

    public AdapterUsuarios()
    {
        this.listaUsuarios = Usuarios.getUsersArray();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderUsuarios onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.users_item_list, null, false);

        view.setOnClickListener(this);

        return new ViewHolderUsuarios(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUsuarios.ViewHolderUsuarios viewHolderUsuarios, int i) {
        String info = "";
        if(!listaUsuarios.get(i).getSeccion().equals("")||!listaUsuarios.get(i).getSeccion().equals(" ")
        ||listaUsuarios.get(i).getSeccion()!=null)
        {
            info += listaUsuarios.get(i).getSeccion()+", ";
        }

        if(!listaUsuarios.get(i).getSubgrupo().equals("")||!listaUsuarios.get(i).getSubgrupo().equals(" ")
        ||listaUsuarios.get(i).getSubgrupo()!=null)
        {
            info += listaUsuarios.get(i).getSubgrupo()+", ";
        }

        info += listaUsuarios.get(i).getCargo();

        viewHolderUsuarios.nombre.setText(listaUsuarios.get(i).getNombre()+" "+listaUsuarios.get(i).getApellidos());
        viewHolderUsuarios.info.setText(info);
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public class ViewHolderUsuarios extends RecyclerView.ViewHolder {

        TextView nombre, info;

        public ViewHolderUsuarios(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvNombreApellidos);
            info = itemView.findViewById(R.id.tvSeccionSubgrupo);
        }
    }
}