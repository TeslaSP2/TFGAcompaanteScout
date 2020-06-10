package com.teslasp2.ftc.acompaante_scout.actividadesDeAsistencia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.adaptadores.AdapterAsistencia;
import com.teslasp2.ftc.acompaante_scout.modelos.Asistencia;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class ShowAsistSelected extends AppCompatActivity implements DelAsistDialog.DelAsistDialogListener {

    TextView nombreUser, apellidoUser, tipo_encuentro, fecha, asistio;
    Asistencia asistencia;
    Usuarios usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_asist_selected);

        nombreUser = findViewById(R.id.nombreUserAsist);
        apellidoUser = findViewById(R.id.apellidosUserAsist);
        tipo_encuentro = findViewById(R.id.tipoEncuentroAsist);
        fecha = findViewById(R.id.fechaAsist);
        asistio = findViewById(R.id.asistioAsist);

        Bundle bundle = getIntent().getExtras();
        asistencia = (Asistencia) bundle.getSerializable("asistencia");
        Usuarios usuario = Usuarios.getUserById(asistencia.getId_ninio());

        nombreUser.setText(usuario.getNombre()+" ");
        apellidoUser.setText(usuario.getApellidos());
        tipo_encuentro.setText(asistencia.getTipo_encuentro()+" del ");
        fecha.setText(asistencia.getFechaString()+" ");
        asistio.setText("Asistió: "+asistencia.getAsistio());

        usuarioActual = Usuarios.getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_selected_asist_progress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.delShow:
            {
                if(usuarioActual.isMonitor()==1)
                {
                    DelAsistDialog delAsistDialog = new DelAsistDialog();
                    delAsistDialog.show(getSupportFragmentManager(), "del asist dialog");
                }
                else
                    Toast.makeText(this, "No estás autorizado para hacer esto", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.modShow:
            {
                if(usuarioActual.isMonitor()==1)
                {
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("asistencia",  asistencia);

                    Intent i = new Intent(this, ModAsist.class);
                    i.putExtras(bundle);
                    startActivity(i);
                }
                else
                    Toast.makeText(this, "No estás autorizado para hacer esto", Toast.LENGTH_SHORT).show();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void canDelete(boolean response)
    {
        if(response)
        {
            try {
                String respuesta = new Asistencia.Delete().execute(asistencia.getId()).get();
                if (respuesta != null) {
                    Toast.makeText(this, "Asistencia eliminada", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "No se pudo eliminar la asistencia seleccionada", Toast.LENGTH_SHORT).show();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}