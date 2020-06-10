package com.teslasp2.ftc.acompaante_scout.actividadesDeProgresoPersonal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.modelos.ProgresoPersonal;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class ShowProgressSelected extends AppCompatActivity implements DelProgressDialog.DelProgressDialogListener {

    TextView nombreUser, apellidoUser, nombreProgress, fecha_inicio, prueba_1, prueba_2, prueba_3, fecha_final, entregado;
    ProgresoPersonal progresoPersonal;
    Usuarios usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_progress_selected);

        nombreUser = findViewById(R.id.nombreUserProgress);
        apellidoUser = findViewById(R.id.apellidosUserProgress);
        nombreProgress = findViewById(R.id.nombreProgress);
        fecha_inicio = findViewById(R.id.fechaInicioProgress);
        prueba_1 = findViewById(R.id.prueba_1Progress);
        prueba_2 = findViewById(R.id.prueba_2Progress);
        prueba_3 = findViewById(R.id.prueba_3Progress);
        fecha_final = findViewById(R.id.fechaFinalProgress);
        entregado = findViewById(R.id.entregadoProgress);

        Bundle bundle = getIntent().getExtras();
        progresoPersonal = (ProgresoPersonal) bundle.getSerializable("progresoPersonal");
        Usuarios usuario = Usuarios.getUserById(progresoPersonal.getId_ninio());

        nombreUser.setText(usuario.getNombre()+" ");
        apellidoUser.setText(usuario.getApellidos());
        nombreProgress.setText(progresoPersonal.getNombre_progreso()+" ");
        fecha_inicio.setText(progresoPersonal.getFecha_inicioString());

        if(progresoPersonal.getPrueba_1()==null)
            prueba_1.setText("Ninguna");
        else
            prueba_1.setText(progresoPersonal.getPrueba_1());

        if(progresoPersonal.getPrueba_2()==null)
            prueba_2.setText("Ninguna");
        else
            prueba_2.setText(progresoPersonal.getPrueba_2());

        if(progresoPersonal.getPrueba_3()==null)
            prueba_3.setText("Ninguna");
        else
            prueba_3.setText(progresoPersonal.getPrueba_3());

        if(progresoPersonal.getFecha_final()!=null)
            fecha_final.setText(progresoPersonal.getFecha_finalString());
        else
            fecha_final.setText("");

        if(progresoPersonal.isEntregado()==1)
            entregado.setText("Entregado");
        else
            entregado.setText("No entregado");

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
                    DelProgressDialog delProgressDialog= new DelProgressDialog();
                    delProgressDialog.show(getSupportFragmentManager(), "del progress dialog");
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

                    bundle.putSerializable("progresoPersonal",  progresoPersonal);

                    Intent i = new Intent(this, ModProgress.class);
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
                String respuesta = new ProgresoPersonal.Delete().execute(progresoPersonal.getId()).get();
                if (respuesta != null) {
                    Toast.makeText(this,
                            "Progreso personal " + progresoPersonal.getNombre_progreso() + " eliminado",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this,
                            "El progreso personal " + progresoPersonal.getNombre_progreso() + " no se pudo eliminar",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}