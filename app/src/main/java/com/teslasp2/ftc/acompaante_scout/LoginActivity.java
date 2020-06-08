package com.teslasp2.ftc.acompaante_scout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    EditText user, contra;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.etUser);
        contra = findViewById(R.id.etContra);
    }

    public void comprobarUsuario(View view)
    {
        String contra = this.contra.getText().toString();
        String user = this.user.getText().toString();
        Usuarios usuarioActual;
        try
        {
            String respuesta = new Usuarios.GetALL().execute().get();
            JSONArray usuarios = new JSONArray(respuesta);
            boolean salir=false;
            int cont=0;

            while(!salir && cont < usuarios.length())
            {
                JSONObject obj = usuarios.getJSONObject(cont);
                if(user.equals(obj.getString("Nombre_User"))&&contra.equals(obj.getString("Contra")))
                {
                    String seccion, subgrupo, alergenos;
                    if(obj.getString("seccion")!=null)
                    {
                        seccion = obj.getString("seccion");
                    }
                    else
                    {
                        seccion = "";
                    }

                    if(obj.getString("subgrupo")!=null)
                    {
                        subgrupo = obj.getString("subgrupo");
                    }
                    else
                    {
                        subgrupo = "";
                    }

                    if(obj.getString("alergenos")!=null)
                    {
                        alergenos = obj.getString("alergenos");
                    }
                    else
                    {
                        alergenos = "";
                    }

                    usuarioActual =  new Usuarios(obj.getInt("Id"), obj.getString("Nombre_User"),
                            obj.getString("Contra"), obj.getInt("Monitor"), obj.getString("nombre"),
                            obj.getString("apellidos"), seccion,
                            subgrupo, obj.getString("Cargo"), alergenos);
                    Usuarios.setCurrentUser(usuarioActual);

                    salir = true;
                }
                else
                {
                    cont++;
                }
            }

            if(salir)
            {
                Intent intent = new Intent(this, MainMenu.class);
                startActivity(intent);
            }
            else
                Toast.makeText(this, "Imposible de encontrar, compruebe el internet del dispositivo o póngase en contacto con el administrador", Toast.LENGTH_SHORT);
        }
        catch (ExecutionException e)
        {
            Toast.makeText(this, "Imposible de encontrar, compruebe el internet del dispositivo o espere un minuto", Toast.LENGTH_SHORT);
        }
        catch (InterruptedException e)
        {
            Toast.makeText(this, "Imposible de encontrar, compruebe el internet del dispositivo o espere un minuto", Toast.LENGTH_SHORT);
        }
        catch (JSONException e)
        {
            Toast.makeText(this, "Imposible de encontrar, compruebe el internet del dispositivo o espere un minuto", Toast.LENGTH_SHORT);
        }
    }
}
