package com.teslasp2.ftc.acompaante_scout.actividadesDeAsistencia;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.modelos.Asistencia;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class AddAsist extends AppCompatActivity {

    CalendarView fecha;
    RadioButton si, no, retraso, reunion, salida, campamento;
    Usuarios usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asist);

        fecha = findViewById(R.id.cvFechaEncuentro);
        si = findViewById(R.id.rbSiAsistio);
        no = findViewById(R.id.rbNoAsistio);
        retraso = findViewById(R.id.rbRetrasoAsistio);
        reunion = findViewById(R.id.rbEncuentroReunión);
        salida = findViewById(R.id.rbEncuentroSalida);
        campamento = findViewById(R.id.rbEncuentroCampa);

        Bundle bundle = getIntent().getExtras();
        usuario = (Usuarios) bundle.getSerializable("usuario");

        reunion.setChecked(true);
    }

    //Comprueba si todo está bien formado y la inserta en la base de datos
    public void aceptar(View view) throws ExecutionException, InterruptedException {
        if(!si.isChecked()&&!no.isChecked()&&!retraso.isChecked())
        {
            Toast.makeText(this,"Debes seleccionar si asistió, no asistió o se retrasó", Toast.LENGTH_SHORT);
        }
        else
        {
            Date fecha = new Date(this.fecha.getDate());

            String asistio, tipo_encuentro = "REUNIÓN";

            if(si.isChecked())
                asistio="SÍ";
            else if(no.isChecked())
                asistio="NO";
            else if(retraso.isChecked())
                asistio="RETRASO";
            else
                asistio="NO";

            if(reunion.isChecked())
                tipo_encuentro="REUNIÓN";
            else if(salida.isChecked())
                tipo_encuentro="SALIDA";
            else if(campamento.isChecked())
                tipo_encuentro="CAMPAMENTO";

            Asistencia asistencia = new Asistencia(0, usuario.getId(), tipo_encuentro, fecha, asistio);

            String respuesta = new Asistencia.Post().execute(asistencia.toJSONString()).get();
            if(respuesta!=null)
            {
                Toast.makeText(this, "Asistencia del niño/a "+usuario.getNombre()+" añadida", Toast.LENGTH_SHORT);
                finish();
            }
            else
            {
                Toast.makeText(this, "ERROR AL AÑADIR", Toast.LENGTH_SHORT);
                finish();
            }
        }
    }
}
