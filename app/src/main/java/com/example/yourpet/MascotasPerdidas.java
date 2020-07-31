package com.example.yourpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MascotasPerdidas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas_perdidas);
        setTitle("Mascotas perdidas");
    }

    public void informacionMascota(View view) {
//        Intent intent = new Intent(MascotasPerdidas.this, InformacionMascota.class);
//        int requestCode = 1;
//        startActivityForResult(intent, requestCode);
    }

    public void buscarPorID(View view) {
        EditText id = findViewById(R.id.idMascota);
        int i = 0;

        if (id.getText().length() == 0) {
            id.setError("Debe llenar este espacio");
            i++;
        } else if ((id.getText().length() > 4) || (id.getText().length() < 4)) {
            id.setError("El id es de 4 caracteres");
            i++;
        }
//         else if (id.getText().length() == 4) {
//            try {
//                String cadena = id.getText().toString();
//                int numid = Integer.parseInt(cadena);
//            } catch (NumberFormatException e) {
//                id.setError("Ingrese solo números enteros");
//                i++;
//            }

//            char[] caracter = cadena.toCharArray();
        if (i == 0) {
//            Intent intent = new Intent(MascotasPerdidas.this, InformacionMascota.class);
//            int requestCode = 2;
//            startActivityForResult(intent, requestCode);
        }

    }
}
