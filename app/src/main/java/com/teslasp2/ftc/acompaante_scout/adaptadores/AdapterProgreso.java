package com.teslasp2.ftc.acompaante_scout.adaptadores;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.modelos.ProgresoPersonal;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.util.ArrayList;

public class AdapterProgreso extends RecyclerView.Adapter<AdapterProgreso.ViewHolderProgreso> implements View.OnClickListener {

    ArrayList<ProgresoPersonal> listaProgresos;
    private View.OnClickListener listener;

    public AdapterProgreso()
    {
        this.listaProgresos = ProgresoPersonal.getProgressArray();
        notifyDataSetChanged();
    }

    public AdapterProgreso(int id_user)
    {
        this.listaProgresos = ProgresoPersonal.getProgressByUser(id_user);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderProgreso onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.progress_item_list, null, false);

        view.setOnClickListener(this);

        return new ViewHolderProgreso(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProgreso.ViewHolderProgreso viewHolderProgreso, int i) {
        Usuarios ninio = Usuarios.getUserById(i);

        if(ninio!=null)
            viewHolderProgreso.nombre.setText(ninio.getNombre()+" "+ninio.getApellidos()+": "+listaProgresos.get(i).getNombre_progreso());
        else
            viewHolderProgreso.nombre.setText("Niño/a sin identificar: "+listaProgresos.get(i).getNombre_progreso());

        if(listaProgresos.get(i).isEntregado()==1)
        {
            viewHolderProgreso.entregado.setText("Entregado");
            viewHolderProgreso.entregado.setTextColor(Color.GREEN);
        }
        else
        {
            viewHolderProgreso.entregado.setText("No Entregado");
            viewHolderProgreso.entregado.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return listaProgresos.size();
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

    public class ViewHolderProgreso extends RecyclerView.ViewHolder {

        TextView nombre, entregado;

        public ViewHolderProgreso(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvProgreso);
            entregado = itemView.findViewById(R.id.tvEntregado);
        }
    }

    public void setListaProgresos(ArrayList<ProgresoPersonal> nuevosProgresos) {
        listaProgresos = new ArrayList<>();
        listaProgresos.addAll(nuevosProgresos);
        notifyDataSetChanged();
    }
}