package com.teslasp2.ftc.acompaante_scout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.teslasp2.ftc.acompaante_scout.actividadesDeUsuario.ShowUsers;

public class TemporaryMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary_menu);
    }

    public void getUsers(View view)
    {
        Intent intent = new Intent(this, ShowUsers.class);
        startActivity(intent);
    }
}