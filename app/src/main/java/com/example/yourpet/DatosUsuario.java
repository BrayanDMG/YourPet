package com.example.yourpet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DatosUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_usuario);
        setTitle("Datos del usuario");
    }

    public void guardarDatosUsuario(View view) {
        EditText nombreUsuario = findViewById(R.id.musuario);
        EditText apellidoUsuario = findViewById(R.id.mapellido);
        TextView textError = findViewById(R.id.textError2);
        textError.setVisibility(View.INVISIBLE);
        int i = 0;
        if (nombreUsuario.getText().length() == 0) {
            nombreUsuario.setError("Debe llenar este espacio");
            i++;
        } else if (nombreUsuario.getText().length() > 15) {
            textError.setText("Máximo 15 caracteres");
            textError.setVisibility(View.VISIBLE);
            i++;
        }
        if (apellidoUsuario.getText().length() == 0) {
            apellidoUsuario.setError("Debe llenar este espacio");
            i++;
        } else if (apellidoUsuario.getText().length() > 15) {
            textError.setText("Máximo 15 caracteres");
            textError.setVisibility(View.VISIBLE);
            i++;
        }

        if (i == 0) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Modificar datos de usuario");
            dialog.setMessage("Se modificarán sus datos y los datos anteriores se eliminarán." +
                    " Desea continuar con la modificación.");
            dialog.setPositiveButton("MODIFICAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(DatosUsuario.this, "Se modifico su nombre y apellido", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(DatosUsuario.this, "No se modifico ningún dato", Toast.LENGTH_LONG).show();
                }
            });
            dialog.show();

        }

    }
}
