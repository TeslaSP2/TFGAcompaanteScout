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

    TextView nombreUser, apellidoUser, fecha_inicio, prueba_1, prueba_2, prueba_3, fecha_final, entregado;
    ProgresoPersonal progresoPersonal;
    Usuarios usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_progress_selected);

        nombreUser = findViewById(R.id.nombreUserProgress);
        apellidoUser = findViewById(R.id.apellidosUserProgress);
        fecha_inicio = findViewById(R.id.fechaInicioProgress);
        prueba_1 = findViewById(R.id.prueba_1Progress);
        prueba_2 = findViewById(R.id.prueba_2Progress);
        prueba_3 = findViewById(R.id.prueba_3Progress);
        fecha_final = findViewById(R.id.fechaFinalProgress);
        entregado = findViewById(R.id.entregadoProgress);

        Bundle bundle = getIntent().getExtras();
        progresoPersonal = (ProgresoPersonal) bundle.getSerializable("progresoPersonal");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Usuarios usuario = Usuarios.getUserById(progresoPersonal.getId_ninio());

        nombreUser.setText(usuario.getNombre());
        apellidoUser.setText(usuario.getApellidos());
        fecha_inicio.setText(sdf.format(progresoPersonal.getFecha_inicio()));
        prueba_1.setText(progresoPersonal.getPrueba_1());
        prueba_2.setText(progresoPersonal.getPrueba_2());
        prueba_3.setText(progresoPersonal.getPrueba_3());

        if(progresoPersonal.getFecha_final()!=null)
            fecha_final.setText(sdf.format(progresoPersonal.getFecha_final()));
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
        getMenuInflater().inflate(R.menu.menu_show_selected, menu);

        MenuItem item = findViewById(R.id.modShow);
        if(usuarioActual.isMonitor()==0)
            item.setVisible(false);

        item = findViewById(R.id.delShow);
        if(usuarioActual.isMonitor()==0)
            item.setVisible(false);

        item = findViewById(R.id.showAsist);
        item.setVisible(false);

        item=findViewById(R.id.showProgress);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.delShow)
        {
            DelProgressDialog delProgressDialog= new DelProgressDialog();
            delProgressDialog.show(getSupportFragmentManager(), "del progress dialog");
        }
        if(id==R.id.modShow)
        {
            Bundle bundle = new Bundle();

            bundle.putSerializable("progresoPersonal",  progresoPersonal);

            Intent i = new Intent(this, ModProgress.class);
            i.putExtras(bundle);
            startActivity(i);
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