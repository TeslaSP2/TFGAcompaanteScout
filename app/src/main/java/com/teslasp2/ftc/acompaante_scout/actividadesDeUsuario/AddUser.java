package com.teslasp2.ftc.acompaante_scout.actividadesDeUsuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;

public class AddUser extends AppCompatActivity
{
    EditText nombre_user, contra, nombre, apellidos, seccion, subgrupo, cargo, alergenos;
    CheckBox monitor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        nombre_user = findViewById(R.id.etNombre_User);
        contra = findViewById(R.id.etContra);
        nombre = findViewById(R.id.etNombre);
        apellidos = findViewById(R.id.etApellidos);
        seccion = findViewById(R.id.etSeccion);
        subgrupo = findViewById(R.id.etSubgrupo);
        cargo = findViewById(R.id.etCargo);
        alergenos = findViewById(R.id.etAlergenos);
        monitor = findViewById(R.id.cbMonitor);
    }

    //Comprueba si todo está bien formado y lo inserta en la base de datos
    public void aceptar(View view) throws ExecutionException, InterruptedException {
        if(nombre_user.getText().toString()==""||contra.getText().toString()==""||nombre.getText().toString()==""
                ||apellidos.getText().toString()==""||cargo.getText().toString()=="")
        {
            Toast.makeText(this,"El nombre de usuario, contraseña, nombre, apellidos y cargo no pueden estar vacíos",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!nombre_user.getText().toString().matches("[A-Za-z0-9]+"))
        {
            Toast.makeText(this,"El usuario no acepta símbolos",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!nombre.getText().toString().matches("[A-Za-z]+")
                ||!apellidos.getText().toString().matches("[A-Za-z]+")
                ||!cargo.getText().toString().matches("[A-Za-z]+"))
        {
            Toast.makeText(this,"Solo la contraseña acepta números y símbolos",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            String seccion = "", subgrupo = "", alergenos = "";
            int monitor = 0;

            if(this.seccion.getText().toString()!=null)
            {
                seccion = this.seccion.getText().toString();
            }

            if(this.subgrupo.getText().toString()!=null)
            {
                subgrupo = this.subgrupo.getText().toString();
            }

            if(this.alergenos.getText().toString()!=null)
            {
                alergenos = this.alergenos.getText().toString();
            }

            if(this.monitor.isChecked())
                monitor = 1;

            int lastId = Usuarios.getLastID();
            Usuarios nuevoUsuario =
                    new Usuarios(lastId+1, nombre_user.getText().toString(), contra.getText().toString(),
                            monitor, nombre.getText().toString(), apellidos.getText().toString(),
                            seccion, subgrupo, cargo.getText().toString(), alergenos);

            //Obtiene el código de la respuesta para determinar si se insertó bien o no
            int respuesta = new Usuarios.Post().execute(nuevoUsuario.toJSONString()).get();
            if(respuesta== HttpURLConnection.HTTP_OK)
            {
                Toast.makeText(this, nuevoUsuario.getNombre()+" añadido", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "ERROR AL AÑADIR "+respuesta+" "+nuevoUsuario.toJSONString(), Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }
}
