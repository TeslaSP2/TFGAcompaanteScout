package com.teslasp2.ftc.acompaante_scout.actividadesDeUsuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

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

    public void aceptar(View view) throws ExecutionException, InterruptedException {
        if(nombre_user.getText().toString()==""||contra.getText().toString()==""||nombre.getText().toString()==""
                ||apellidos.getText().toString()==""||cargo.getText().toString()=="")
        {
            Toast.makeText(this,"El nombre de usuario, contraseña, nombre, apellidos y cargo no puede estar vacío",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!nombre_user.getText().toString().matches("[A-Za-z0-9]+"))
        {
            Toast.makeText(this,"El usuario no acepta símbolos",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!nombre.getText().toString().matches("[A-Za-z]+")
                ||!apellidos.getText().toString().matches("[A-Za-z]+")
                ||!cargo.getText().toString().matches("[A-Za-z]+")
                ||!seccion.getText().toString().matches("[A-Za-z]+")
                ||!subgrupo.getText().toString().matches("[A-Za-z]+")
                ||!alergenos.getText().toString().matches("[A-Za-z]+"))
        {
            Toast.makeText(this,"Solo la contraseña acepta números y símbolos",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            String seccion, subgrupo, alergenos;
            int monitor = 0;

            if(this.seccion.getText().toString()!=null)
            {
                seccion = this.seccion.getText().toString();
            }
            else
            {
                seccion = "";
            }

            if(this.subgrupo.getText().toString()!=null)
            {
                subgrupo = this.subgrupo.getText().toString();
            }
            else
            {
                subgrupo = "";
            }

            if(this.alergenos.getText().toString()!=null)
            {
                alergenos = this.alergenos.getText().toString();
            }
            else
            {
                alergenos = "";
            }

            if(this.monitor.isChecked())
                monitor = 1;

            Usuarios nuevoUsuario =
                    new Usuarios(0, nombre_user.getText().toString(),contra.getText().toString(),monitor,
                            nombre.getText().toString(),apellidos.getText().toString(),seccion,subgrupo,cargo.getText().toString(),
                            alergenos);

            String respuesta = new Usuarios.Post().execute(nuevoUsuario.toJSONString()).get();
            if(respuesta!=null)
            {
                Toast.makeText(this, nuevoUsuario.getNombre()+" añadido", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(this, "ERROR AL AÑADIR", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
