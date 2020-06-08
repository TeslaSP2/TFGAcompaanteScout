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

public class ModAsist extends AppCompatActivity {

    CalendarView fecha;
    RadioButton si, no, retraso, reunion, salida, campamento;
    Usuarios usuario;
    Asistencia asistencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_asist);

        fecha = findViewById(R.id.cvFechaEncuentro);
        si = findViewById(R.id.rbModSiAsistio);
        no = findViewById(R.id.rbModNoAsistio);
        retraso = findViewById(R.id.rbModRetrasoAsistio);
        reunion = findViewById(R.id.rbModEncuentroReunión);
        salida = findViewById(R.id.rbModEncuentroSalida);
        campamento = findViewById(R.id.rbModEncuentroCampa);

        Bundle bundle = getIntent().getExtras();
        asistencia = (Asistencia) bundle.getSerializable("asistencia");

        usuario = Usuarios.getUserById(asistencia.getId_ninio());

        fecha.setDate(asistencia.getFecha().getTime());

        switch(asistencia.getAsistio())
        {
            case "SÍ":
            {
                si.setChecked(true);
                break;
            }
            case "NO":
            {
                no.setChecked(true);
                break;
            }
            case "RETRASO":
            {
                retraso.setChecked(true);
                break;
            }
            default:
            {
                no.setChecked(true);
                break;
            }
        }

        switch(asistencia.getTipo_encuentro())
        {
            case "REUNIÓN":
            {
                reunion.setChecked(true);
                break;
            }
            case "SALIDA":
            {
                salida.setChecked(true);
                break;
            }
            case "CAMPAMENTO":
            {
                campamento.setChecked(true);
                break;
            }
            default:
            {
                reunion.setChecked(true);
                break;
            }
        }
    }

    //Comprueba que todo este bien para poder cambiar la asistencia seleccionada
    public void aceptar(View view) throws ExecutionException, InterruptedException {
        if(!si.isChecked()&&!no.isChecked()&&!retraso.isChecked())
        {
            Toast.makeText(this,"Debes seleccionar si asistió, no asistió o se retrasó",
                    Toast.LENGTH_SHORT);
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

            Asistencia nuevaAsistencia = new Asistencia(asistencia.getId(), asistencia.getId_ninio(),
                    tipo_encuentro, fecha, asistio);

            String respuesta = new Asistencia.Put().execute(nuevaAsistencia.toJSONString()).get();
            if(respuesta!=null)
            {
                Toast.makeText(this, "Asistencia del niño/a "+usuario.getNombre()+" modificada",
                        Toast.LENGTH_SHORT);
                finish();
            }
            else
            {
                Toast.makeText(this, "ERROR AL MODIFICAR", Toast.LENGTH_SHORT);
                finish();
            }
        }
    }
}
