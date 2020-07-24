package com.example.yourpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InformacionMascota extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_mascota);
        setTitle("Información de la mascota");
    }

    public void regresar (View view){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
