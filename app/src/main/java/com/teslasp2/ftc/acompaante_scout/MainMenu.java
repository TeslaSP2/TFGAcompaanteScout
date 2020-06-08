package com.teslasp2.ftc.acompaante_scout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.teslasp2.ftc.acompaante_scout.actividadesDeAsistencia.ShowAsist;
import com.teslasp2.ftc.acompaante_scout.actividadesDeProgresoPersonal.ShowProgress;
import com.teslasp2.ftc.acompaante_scout.actividadesDeUsuario.DelUserDialog;
import com.teslasp2.ftc.acompaante_scout.actividadesDeUsuario.ShowUsers;
import com.teslasp2.ftc.acompaante_scout.modelos.Usuarios;

import java.util.concurrent.ExecutionException;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CloseSessionDialog.CloseSessionDialogListener {
    DrawerLayout barraLateral;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        barraLateral = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, barraLateral,
                toolbar, R.string.bLAbrir, R.string.bLCerrar);
        barraLateral.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ShowUsers()).commit();
            navigationView.setCheckedItem(R.id.bLUsers);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bLUsers:
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ShowUsers()).commit();
                    break;
                }
            case R.id.bLAsist:
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ShowProgress()).commit();
                    break;
                }
            case R.id.bLProgress:
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ShowAsist()).commit();
                    break;
                }
            case R.id.bLChat:
                {
                    Toast.makeText(this, "No implementado aún", Toast.LENGTH_SHORT);
                    break;
                }
            case R.id.bLChangeUser:
                {
                    CloseSessionDialog closeSessionDialog = new CloseSessionDialog();
                    closeSessionDialog.show(getSupportFragmentManager(), "close session dialog");
                    break;
                }
        }
        barraLateral.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (barraLateral.isDrawerOpen(GravityCompat.START)) {
            barraLateral.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void canDelete(boolean response)
    {
        if(response)
        {
            SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
            preferences.edit().remove("Nombre_User").commit();
            preferences.edit().remove("Contra").commit();

            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
    }
}