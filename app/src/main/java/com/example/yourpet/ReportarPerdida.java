package com.example.yourpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ReportarPerdida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_perdida);
        setTitle("Reportar Pérdida");
    }

    public void publicarReporte(View view) {
        EditText descripcion = findViewById(R.id.text_descripcion);
        EditText celular = findViewById(R.id.celular);
        TextView fecha = findViewById(R.id.fecha);

        int i = 0;
        if (descripcion.getText().length() == 0) {
            descripcion.setError("Debe llenar este espacio");
            i++;
        } else if (descripcion.getText().length() > 50) {
            descripcion.setError("Máximo 50 caracteres");
            i++;
        }
        if (fecha.getText().length() == 0) {
            fecha.setError("Debe llenar este espacio");
            i++;
        } else if (fecha.getText().length() > 8) {
            fecha.setError("Use este formato: DD/MM/AA");
            i++;
        } else if (fecha.getText().length() < 8) {
            fecha.setError("Use este formato: DD/MM/AA");
            i++;
        }
        if (celular.getText().length() == 0) {
            celular.setError("Debe llenar este espacio");
            i++;
        } else if (celular.getText().length() > 9) {
            celular.setError("Máximo 9 números");
            i++;
        } else if (celular.getText().length() < 9) {
            celular.setError("Debe escribir 9 números");
            i++;
        }

        if (i == 0) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void atras(View view) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

}
