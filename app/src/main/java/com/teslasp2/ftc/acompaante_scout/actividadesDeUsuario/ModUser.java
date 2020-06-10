package com.teslasp2.ftc.acompaante_scout.actividadesDeUsuario;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teslasp2.ftc.acompaante_scout.R;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.util.concurrent.ExecutionException;

public class ModUser extends AppCompatActivity
{
    EditText nombre_user, contra, nombre, apellidos, seccion, subgrupo, cargo, alergenos;
    CheckBox monitor;
    Usuarios usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_user);

        nombre_user = findViewById(R.id.etModNombre_User);
        contra = findViewById(R.id.etModContra);
        nombre = findViewById(R.id.etModNombre);
        apellidos = findViewById(R.id.etModApellidos);
        seccion = findViewById(R.id.etModSeccion);
        subgrupo = findViewById(R.id.etModSubgrupo);
        cargo = findViewById(R.id.etModCargo);
        alergenos = findViewById(R.id.etModAlergenos);
        monitor = findViewById(R.id.cbModMonitor);

        Bundle bundle = getIntent().getExtras();
        usuario = (Usuarios) bundle.getSerializable("usuario");

        nombre_user.setText(usuario.getNombre_user());
        contra.setText(usuario.getContra());
        nombre.setText(usuario.getNombre());
        apellidos.setText(usuario.getApellidos());

        if(usuario.getSeccion()!=null||!usuario.getSeccion().equals(""))
            seccion.setText(usuario.getSeccion());

        if(usuario.getSubgrupo()!=null||!usuario.getSubgrupo().equals(""))
            subgrupo.setText(usuario.getSubgrupo());

        if(usuario.getCargo()!=null||!usuario.getCargo().equals(""))
            cargo.setText(usuario.getCargo());

        if(usuario.getAlergenos()!=null||!usuario.getAlergenos().equals(""))
            alergenos.setText(usuario.getAlergenos());

        if(usuario.isMonitor()==1)
            monitor.setChecked(true);
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

            Usuarios nuevoUsuario =
                    new Usuarios(usuario.getId(), nombre_user.getText().toString(),contra.getText().toString(),monitor,
                            nombre.getText().toString(),apellidos.getText().toString(),seccion,subgrupo,cargo.getText().toString(),
                            alergenos);

            String respuesta = new Usuarios.Put().execute(nuevoUsuario.toJSONString()).get();

            if(respuesta!=null)
            {
                Toast.makeText(this, nuevoUsuario.getNombre()+" modificado", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "ERROR AL MODIFICAR", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }
}
