package com.teslasp2.ftc.acompaante_scout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Launch extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    private static final int INTERNET_PERMISION = 1;
    private View snackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        snackLayout = findViewById(R.id.snackLayout);
        comprobarPermiso();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == INTERNET_PERMISION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "El permiso de usar internet ha sido aceptado",
                        Toast.LENGTH_SHORT).show();
                startApp();
            } else {
                Toast.makeText(this, "El permiso de usar internet ha sido denegado",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void comprobarPermiso()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED)
        {
            pedirPermisoInternet();
        }
        else
        {
            startApp();
        }

    }

    private void pedirPermisoInternet()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.INTERNET))
        {
            Snackbar.make(snackLayout, "Esta app necesita acceso a Internet",
                    Snackbar.LENGTH_INDEFINITE).setAction("Aceptar", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(Launch.this,
                            new String[]{Manifest.permission.INTERNET},
                            INTERNET_PERMISION);
                }
            }).show();

        }
        else
        {
            Toast.makeText(this,
                    "La conexión a Internet no está disponible actualmente, compruebe que este encendida o " +
                            "el dispisitivo esté conectado a una red WIFI",
                    Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISION);
        }
    }

    private void startApp() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}