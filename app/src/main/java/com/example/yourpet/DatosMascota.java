package com.example.yourpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DatosMascota extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_mascota);
        setTitle("Datos de la mascota");
    }


    public void guardarDatosMascota(View view) {
        EditText nombreMascota = findViewById(R.id.nombreMascota);
        EditText raza = findViewById(R.id.raza);
        EditText color = findViewById(R.id.color);
        TextView textError = findViewById(R.id.textError);
        textError.setVisibility(View.INVISIBLE);
        EditText años = findViewById(R.id.años);
        String textoaños = años.getText().toString();
        RadioButton perro = findViewById(R.id.radioButtonperro);
        RadioButton gato = findViewById(R.id.radioButtongato);
        Spinner distritos = findViewById(R.id.spinnerDistritos);


        int i = 0;

        if (nombreMascota.getText().length() == 0) {
            nombreMascota.setError("Debe llenar este espacio");
            i++;
        } else if (nombreMascota.getText().length() > 15) {
            textError.setText("Máximo 15 caracteres");
            textError.setVisibility(View.VISIBLE);
            i++;
        }
        if (raza.getText().length() == 0) {
            raza.setError("Debe llenar este espacio");
            i++;
        } else if (raza.getText().length() > 15) {
            textError.setText("Máximo 15 caracteres");
            textError.setVisibility(View.VISIBLE);
            i++;
        }
        if (color.getText().length() == 0) {
            color.setError("Debe llenar este espacio");
            i++;
        } else if (color.getText().length() > 20) {
            textError.setText("Máximo 20 caracteres");
            textError.setVisibility(View.VISIBLE);
            i++;
        }
        if (años.getText().length() == 0) {
            años.setError("Debe llenar este espacio");
            i++;
        }
        if (años.getText().length() != 0) {
            int numeroaños = Integer.parseInt(textoaños);
            if (numeroaños > 15) {
                años.setError("Máximo 15");
                i++;
            }
        }
        if (perro.isChecked() == true) {
            TextView error = findViewById(R.id.textView36);
            error.setText("perro");
        } else {
            TextView error = findViewById(R.id.textView36);
            error.setText("gato");
        }

        String distritoselect = distritos.getSelectedItem().toString();
        ((TextView) findViewById(R.id.textView41)).setText(distritoselect);

        if (i == 0) {
            Toast.makeText(this,"Datos de mascota completados",Toast.LENGTH_LONG).show();
            //Intent intent = new Intent(DatosMascota.this, MenuPrincipal.class);
            startActivity(new Intent(this,MenuPrincipal.class));
            finish();
        }
    }
}
