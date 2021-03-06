package com.teslasp2.ftc.acompaante_scout.actividadesDeProgresoPersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.modelos.ProgresoPersonal;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class AddProgress extends AppCompatActivity {

    EditText nombre_progreso, prueba_1, prueba_2, prueba_3;
    CheckBox cbFechaFinal, entregado;
    CalendarView fechaInicio, fechaFinal;
    Usuarios usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_progress);

        nombre_progreso = findViewById(R.id.etNombre_Progreso);
        prueba_1 = findViewById(R.id.etPrueba_1);
        prueba_2 = findViewById(R.id.etPrueba_2);
        prueba_3 = findViewById(R.id.etPrueba_3);
        cbFechaFinal = findViewById(R.id.cbActivarFechaFinal);
        entregado = findViewById(R.id.cbEntregado);
        fechaFinal = findViewById(R.id.cvFechaFinal);
        fechaFinal.setEnabled(false);
        fechaInicio = findViewById(R.id.cvFechaEncuentro);

        Bundle bundle = getIntent().getExtras();
        usuario = (Usuarios) bundle.getSerializable("usuario");
    }

    //Comprueba si todo está bien formado y lo inserta en la base de datos
    public void aceptar(View view) throws ExecutionException, InterruptedException {
        if(nombre_progreso.getText().toString()=="")
        {
            Toast.makeText(this,"El nombre del progreso personal no puede estar en blanco",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            String prueba_1 = "Ninguna", prueba_2 = "Ninguna", prueba_3 = "Ninguna";
            Date fechaFinal=null, fechaInicio = new Date(this.fechaInicio.getDate());
            int entregado = 0;

            if(cbFechaFinal.isChecked())
                fechaFinal = new Date(this.fechaFinal.getDate());

            if(this.prueba_1.getText().toString()!="")
                prueba_1 = this.prueba_1.getText().toString();

            if(this.prueba_2.getText().toString()!="")
                prueba_2 = this.prueba_2.getText().toString();

            if(this.prueba_3.getText().toString()!="")
                prueba_3 = this.prueba_3.getText().toString();

            if(this.entregado.isChecked())
                entregado = 1;

            int lastId = ProgresoPersonal.getLastID();
            ProgresoPersonal progresoPersonal = new ProgresoPersonal(lastId+1, usuario.getId(),
                    nombre_progreso.getText().toString(),fechaInicio,prueba_1,prueba_2,prueba_3,
                    fechaFinal,entregado);

            //Obtiene el código de la respuesta para determinar si se insertó bien o no
            String respuesta = new ProgresoPersonal.Post().execute(progresoPersonal.toJSONString()).get();
            if(respuesta!=null)
            {
                Toast.makeText(this,
                        "Progreso "+progresoPersonal.getNombre_progreso()+" del niño/a "+usuario.getNombre()+" añadido",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "ERROR AL AÑADIR", Toast.LENGTH_SHORT).show();
            }
            finish();
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
