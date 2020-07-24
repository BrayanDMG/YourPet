package com.example.yourpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ModificarReporte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_reporte);
        setTitle("Mis reportes");
    }

    public void eliminarReporte (View view){
        //elimina información
    }
    public void modificarReporte (View view){
        Intent intent = new Intent(ModificarReporte.this, ReportarPerdida.class);
        int requestCode = 1;
        startActivityForResult(intent,requestCode);
    }
}
