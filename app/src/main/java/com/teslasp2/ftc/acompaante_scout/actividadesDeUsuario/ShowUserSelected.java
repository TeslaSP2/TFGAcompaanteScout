package com.teslasp2.ftc.acompaante_scout.actividadesDeUsuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.actividadesDeAsistencia.ShowAsistByUser;
import com.teslasp2.ftc.acompaante_scout.actividadesDeProgresoPersonal.ShowProgressByUser;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

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

        nombre.setText(usuario.getNombre());
        apellidos.setText(usuario.getApellidos());
        seccion.setText(usuario.getSeccion());
        subgrupo.setText(usuario.getSubgrupo());
        cargo.setText(usuario.getCargo());

        if(usuario.isMonitor()==1)
            monitor.setText("Monitor");
        else
            monitor.setText("");

        alergenos.setText(usuario.getAlergenos());

        usuarioActual = Usuarios.getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_selected, menu);
        return true;
    }

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
                if(usuarioActual.isMonitor()==0)
                    Toast.makeText(this, "No estás autorizado para hacer esto", Toast.LENGTH_SHORT);
                else
                {
                    DelUserDialog delUserDialog = new DelUserDialog();
                    delUserDialog.show(getSupportFragmentManager(), "del user dialog");
                }
                break;
            }
            case R.id.modShow:
            {
                if(usuarioActual.isMonitor()==0)
                    Toast.makeText(this, "No estás autorizado para hacer esto", Toast.LENGTH_SHORT);
                else
                    {
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("usuario", usuario);

                    Intent i = new Intent(this, ModUser.class);
                    i.putExtras(bundle);
                    startActivity(i);
                }
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
                String respuesta = new Usuarios.Delete().execute(usuario.getId()).get();
                if (respuesta != null)
                {
                    Toast.makeText(this, usuario.getNombre() + " eliminado", Toast.LENGTH_SHORT);
                    finish();
                }
                else
                {
                    Toast.makeText(this, usuario.getNombre() + " no se pudo eliminar", Toast.LENGTH_SHORT);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
