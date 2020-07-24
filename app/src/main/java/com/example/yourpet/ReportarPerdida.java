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
        EditText ultimavez = findViewById(R.id.ultimavez);
        EditText celular = findViewById(R.id.celular);
        TextView textError = findViewById(R.id.textError3);
        TextView fecha = findViewById(R.id.fecha);
        textError.setVisibility(View.INVISIBLE);
        int i = 0;
        if (ultimavez.getText().length() == 0) {
            ultimavez.setError("Debe llenar este espacio");
            i++;
        } else if (ultimavez.getText().length() > 50) {
            textError.setText("Máximo 50 caracteres");
            textError.setVisibility(View.VISIBLE);
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
