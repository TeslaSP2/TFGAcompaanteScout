package com.teslasp2.ftc.acompaante_scout.adaptadores;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.modelos.Asistencia;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdapterAsistencia extends RecyclerView.Adapter<AdapterAsistencia.ViewHolderAsistencia> implements View.OnClickListener {

    ArrayList<Asistencia> listaAsistencias;
    private View.OnClickListener listener;

    public AdapterAsistencia()
    {
        this.listaAsistencias = Asistencia.getAsistsArray();
        notifyDataSetChanged();
    }

    public AdapterAsistencia(int id_user)
    {
        this.listaAsistencias = Asistencia.getAsistsByUser(id_user);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderAsistencia onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.asist_item_list, null, false);

        view.setOnClickListener(this);

        return new ViewHolderAsistencia(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAsistencia.ViewHolderAsistencia viewHolderAsistencia, int i) {
        Usuarios ninio = Usuarios.getUserById(i+1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if(ninio!=null)
            viewHolderAsistencia.nombre.setText(ninio.getNombre()+" "+ninio.getApellidos()+": "+
                    sdf.format(listaAsistencias.get(i).getFecha())
                    +" "+listaAsistencias.get(i).getTipo_encuentro());
        else
            viewHolderAsistencia.nombre.setText("Niño/a sin identificar: "+
                    sdf.format(listaAsistencias.get(i).getFecha())
                    +" "+listaAsistencias.get(i).getTipo_encuentro());

        if(listaAsistencias.get(i).getAsistio().equals("SÍ"))
        {
            viewHolderAsistencia.asistio.setText("Asistió");
            viewHolderAsistencia.asistio.setTextColor(Color.GREEN);
        }
        else if(listaAsistencias.get(i).getAsistio().equals("RETRASO"))
        {
            viewHolderAsistencia.asistio.setText("Se retrasó");
            viewHolderAsistencia.asistio.setTextColor(Color.YELLOW);
        }
        else
        {
            viewHolderAsistencia.asistio.setText("No asistió");
            viewHolderAsistencia.asistio.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return listaAsistencias.size();
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

    public class ViewHolderAsistencia extends RecyclerView.ViewHolder {

        TextView nombre, asistio;

        public ViewHolderAsistencia(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvFechaYTipo);
            asistio = itemView.findViewById(R.id.tvAsistio);
        }
    }

    public void setListaAsistencias(ArrayList<Asistencia> nuevasAsistencias) {
        listaAsistencias = new ArrayList<>();
        listaAsistencias.addAll(nuevasAsistencias);
        notifyDataSetChanged();
    }
}