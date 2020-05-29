package com.teslasp2.ftc.acompaante_scout.actividadesDeProgresoPersonal;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.modelos.ProgresoPersonal;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class ModProgress extends AppCompatActivity {

    EditText nombre_progreso, prueba_1, prueba_2, prueba_3;
    CheckBox cbFechaFinal, entregado;
    CalendarView fechaInicio, fechaFinal;
    Usuarios usuario;
    ProgresoPersonal progresoPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_progress);

        nombre_progreso = findViewById(R.id.etModNombre_Progreso);
        prueba_1 = findViewById(R.id.etModPrueba_1);
        prueba_2 = findViewById(R.id.etModPrueba_2);
        prueba_3 = findViewById(R.id.etModPrueba_3);
        cbFechaFinal = findViewById(R.id.cbModActivarFechaFinal);
        entregado = findViewById(R.id.cbModEntregado);
        fechaFinal = findViewById(R.id.cvModFechaFinal);
        fechaFinal.setEnabled(false);
        fechaInicio = findViewById(R.id.cvModFechaEncuentro);

        Bundle bundle = getIntent().getExtras();
        progresoPersonal = (ProgresoPersonal) bundle.getSerializable("progresoPersonal");

        usuario = Usuarios.getUserById(progresoPersonal.getId_ninio());

        nombre_progreso.setText(progresoPersonal.getNombre_progreso());
        prueba_1.setText(progresoPersonal.getPrueba_1());
        prueba_2.setText(progresoPersonal.getPrueba_2());
        prueba_3.setText(progresoPersonal.getPrueba_3());

        if(progresoPersonal.getFecha_final()!=null)
        {
            cbFechaFinal.setChecked(true);
            fechaFinal.setDate(progresoPersonal.getFecha_final().getTime());
        }
        else
            fechaFinal.setEnabled(false);

        fechaInicio.setDate(progresoPersonal.getFecha_inicio().getTime());

        if(progresoPersonal.isEntregado()==1)
            entregado.setChecked(true);
        else
            entregado.setChecked(false);
    }

    public void aceptar(View view) throws ExecutionException, InterruptedException {
        if(nombre_progreso.getText().toString()=="")
        {
            Toast.makeText(this,"El nombre del progreso personal no puede estar en blanco", Toast.LENGTH_SHORT);
        }
        else
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String prueba_2 = "", prueba_3 = "";
            Date fechaFinal=null, fechaInicio = new Date(this.fechaInicio.getDate());
            int entregado = 0;

            if(cbFechaFinal.isChecked())
                fechaFinal = new Date(this.fechaFinal.getDate());

            if(this.prueba_2.getText().toString()!="")
                prueba_2 = this.prueba_2.getText().toString();

            if(this.prueba_3.getText().toString()!="")
                prueba_3 = this.prueba_3.getText().toString();

            if(this.entregado.isChecked())
                entregado = 1;

            ProgresoPersonal nuevoProgresoPersonal = new ProgresoPersonal(progresoPersonal.getId(),
                    progresoPersonal.getId_ninio(), nombre_progreso.getText().toString(),
                    fechaInicio,prueba_1.getText().toString(),prueba_2,prueba_3,fechaFinal,entregado);

            String respuesta = new ProgresoPersonal.Put().execute(nuevoProgresoPersonal.toJSONString()).get();
            if(respuesta!=null)
            {
                Toast.makeText(this, "Progreso "+nuevoProgresoPersonal.getNombre_progreso()+" del niño/a "+usuario.getNombre()+" modificado", Toast.LENGTH_SHORT);
                finish();
            }
            else
            {
                Toast.makeText(this, "ERROR AL MODIFICAR", Toast.LENGTH_SHORT);
                finish();
            }
        }
    }

    public void activateFinalDate(View view)
    {
        if(cbFechaFinal.isChecked())
            fechaFinal.setEnabled(true);
        else
            fechaFinal.setEnabled(false);
    }
}
