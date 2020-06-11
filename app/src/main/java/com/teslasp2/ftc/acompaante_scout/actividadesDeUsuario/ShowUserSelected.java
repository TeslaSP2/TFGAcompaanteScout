package com.teslasp2.ftc.acompaante_scout.actividadesDeUsuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.actividadesDeAsistencia.ShowAsistByUser;
import com.teslasp2.ftc.acompaante_scout.actividadesDeProgresoPersonal.ShowProgressByUser;
import com.teslasp2.ftc.acompaante_scout.modelos.Asistencia;
import com.teslasp2.ftc.acompaante_scout.modelos.ProgresoPersonal;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ShowUserSelected extends AppCompatActivity implements DelUserDialog.DelUserDialogListener{

    TextView nombre, apellidos, seccion, subgrupo, cargo, monitor, alergenos;
    Usuarios usuario, usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_selected);

        nombre = findViewById(R.id.nombreUser);
        apellidos = findViewById(R.id.apellidosUser);
        seccion = findViewById(R.id.seccionUser);
        subgrupo = findViewById(R.id.subgrupoUser);
        cargo = findViewById(R.id.cargoUser);
        monitor = findViewById(R.id.monitorUser);
        alergenos = findViewById(R.id.alergenosUser);

        Bundle bundle = getIntent().getExtras();
        usuario = (Usuarios) bundle.getSerializable("usuario");

        //Carga los datos del usuario escogido
        nombre.setText(usuario.getNombre()+" ");
        apellidos.setText(usuario.getApellidos());
        seccion.setText("Sección: "+usuario.getSeccion()+" ");
        subgrupo.setText("Subgrupo: "+usuario.getSubgrupo()+" ");
        cargo.setText("Cargo: "+usuario.getCargo()+" ");

        if(usuario.isMonitor()==1)
            monitor.setText("Monitor");
        else
            monitor.setText("");

        alergenos.setText("Alérgenos: "+usuario.getAlergenos());

        //Obtiene el usuario actual para determinar si puede borrar y modificar o no
        usuarioActual = Usuarios.getCurrentUser();
    }

    //Inserta el menú con todas las opciones que pueden hacer los usuarios
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_selected, menu);
        return true;
    }

    /*
     * Dependiendo de lo que pulsase el usuario este método hará varias cosas:
     * showProgress: Muestra una lista con todos los progresos personales de ese usuario
     * showAsist: Muestra una lista con todas las asistencias de ese usuario
     * delShow: Muestra el diálogo para borrar el usuario
     * modShow: Envía al usuario a una nueva ventana donde podrá modificar el usuario
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.showProgress:
            {
                Bundle bundle = new Bundle();

                bundle.putSerializable("usuario",  usuario);

                Intent i = new Intent(this, ShowProgressByUser.class);
                i.putExtras(bundle);
                startActivity(i);
                break;
            }
            case R.id.showAsist:
            {
                Bundle bundle = new Bundle();

                bundle.putSerializable("usuario",  usuario);

                Intent i = new Intent(this, ShowAsistByUser.class);
                i.putExtras(bundle);
                startActivity(i);
                break;
            }
            case R.id.delShow:
            {
                if(usuarioActual.isMonitor()==1)
                {
                    DelUserDialog delUserDialog = new DelUserDialog();
                    delUserDialog.show(getSupportFragmentManager(), "del user dialog");
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

                    bundle.putSerializable("usuario", usuario);

                    Intent i = new Intent(this, ModUser.class);
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

    //Método implementado de DelUserDialog para borrar el usuario
    @Override
    public void canDelete(boolean response)
    {
        if(response)
        {
            try
            {
                ArrayList<ProgresoPersonal> progresosUser= ProgresoPersonal.getProgressByUser(usuario.getId());
                ArrayList<Asistencia> asistenciasUser = Asistencia.getAsistsByUser(usuario.getId());

                int respuesta = new Usuarios.Delete().execute(usuario.getId()).get();
                if (respuesta == HttpURLConnection.HTTP_OK)
                {
                    for (ProgresoPersonal progreso : progresosUser)
                    {
                        new ProgresoPersonal.Delete().execute(progreso.getId());
                    }

                    for(Asistencia asistencia : asistenciasUser)
                    {
                        new Asistencia.Delete().execute(asistencia.getId());
                    }

                    Toast.makeText(this, usuario.getNombre() + " eliminado", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(this, usuario.getNombre() + " no se pudo eliminar", Toast.LENGTH_SHORT).show();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
