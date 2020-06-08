package com.teslasp2.ftc.acompaante_scout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

        if(getSharedPreferences("Login", MODE_PRIVATE) != null)
        {
            sendToMenu();
        }
    }

    void sendToMenu()
    {
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        Intent intent = new Intent(this, MainMenu.class);
        Bundle bundle = new Bundle(2);
        bundle.putString("User", preferences.getString("Nombre_User", null));
        bundle.putString("Contra", preferences.getString("Contra", null));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void comprobarUsuario(View view)
    {
        String contra = this.contra.getText().toString();
        String user = this.user.getText().toString();
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
                    //Añadir usuario a sharedpreference para inicio rápido
                    SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Nombre_User", user);
                    editor.putString("Contra", contra);
                    editor.commit();

                    salir = true;
                }
                else
                {
                    cont++;
                }
            }

            if(salir)
                sendToMenu();
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
