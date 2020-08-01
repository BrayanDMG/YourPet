package com.example.yourpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportarPerdida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_perdida);
        setTitle("Reportar Pérdida");

        final EditText descripcion = findViewById(R.id.text_descripcion);
        final EditText celular = findViewById(R.id.celular);
        final TextView fecha = findViewById(R.id.fecha);
        Button publicar = findViewById(R.id.button_publicar);


        final DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


        Intent intent = getIntent();
        final DetallesMascota mascotaElegida = (DetallesMascota) intent.getSerializableExtra("datosMascota");

        TextView titulo = findViewById(R.id.textViewTitulo);
        titulo.setText(mascotaElegida.getNombre());


        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valido()){
                    //Crea un nuevo child dentro de la carpeta del usuario
                    DatabaseReference datosMacotaPerdido = mDatabaseRef.child("USUARIOS/" + currentUser.getUid() +
                            "/" + mascotaElegida.getNombre()).child("REPORTE");

                    DatosPerdida datos = new DatosPerdida(descripcion.getText().toString(),
                            celular.getText().toString(), fecha.getText().toString());

                    mascotaElegida.setDatosPerdida(datos);

                    datosMacotaPerdido.setValue(datos);

                    //Crear un nuevo child en la carpeta de mascotas perdidas
                    DatabaseReference mascotasPerdidas = mDatabaseRef.child("PERDIDAS/"); //+
                    DatabaseReference mascotaActualPerdida = mascotasPerdidas.child(mascotaElegida.getId()+"/"+mascotaElegida.getUser_id());
                    //Se guarda los datos de perdida dentro de la carpeta PERDIDA/IDMASCOTA/idUSUARIO
                    mascotaActualPerdida.setValue(mascotaElegida);



                    Toast.makeText(ReportarPerdida.this,
                            "Se reporto la pérdida de "+mascotaElegida.getNombre(),Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });




    }

    private boolean valido(){
        EditText descripcion = findViewById(R.id.text_descripcion);
        EditText celular = findViewById(R.id.celular);
        TextView fecha = findViewById(R.id.fecha);

        int i = 0;
        if (descripcion.getText().length() == 0) {
            descripcion.setError("Debe llenar este espacio");
            i++;
        } else if (descripcion.getText().length() > 100) {
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
            return true;
        }else return false;
    }


    public void atras(View view) {

        finish();
    }

}
