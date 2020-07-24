package com.example.yourpet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ModificarDatosMascota extends AppCompatActivity {

    ImageView imagen;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos_mascota);
        setTitle("Modificar datos de la mascota");
        /*
        Spinner distritos = findViewById(R.id.spinner2);
        String[] listadistritos = {"Ancón", "Ate Vitarte", "Barranco", "Breña ","Carabayllo","Callao","Chaclacayo",
            "Chorrillos","Cieneguilla","Comas","El Agustino","Independencia","Jesús María","La Molina","La Victoria","Lima"};

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                listadistritos);
        distritos.setAdapter(adapterSpinner); */

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        imagen = findViewById(R.id.ImageM);


    }

    public void modificarDatos(View view) {
        EditText nombreMascota = findViewById(R.id.mnombre);
        EditText raza = findViewById(R.id.mraza);
        EditText color = findViewById(R.id.mcolor);
        TextView textError = findViewById(R.id.textError1);
        textError.setVisibility(View.INVISIBLE);
        EditText años = findViewById(R.id.maños);
        String textoaños = años.getText().toString();
        RadioButton perro = findViewById(R.id.radioButtonmperro);
        RadioButton gato = findViewById(R.id.radioButtonmgato);
        Spinner distritos = findViewById(R.id.spinnermDistritos);

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
        } else if (raza.getText().length() > 20) {
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
            ((TextView) findViewById(R.id.textView39)).setText("perro");
        } else ((TextView) findViewById(R.id.textView39)).setText("gato");

        String distritoselect = distritos.getSelectedItem().toString();
        ((TextView) findViewById(R.id.textView42)).setText(distritoselect);
        final String nomMascota = nombreMascota.getText().toString();

        if (i == 0) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Guardar los datos de " + nomMascota);
            dialog.setMessage("Se modificarán los datos de la mascota y los anteriores se eliminarán." +
                    " Debe seleccionar una foto de su galeria que identifique a " + nomMascota + " para " +
                    "continuar con la modificación.");
            dialog.setPositiveButton("ESCOGER FOTO Y SUBIR LOS DATOS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Toast.makeText(ModificarDatosMascota.this, "Se modifico los datos de "+nomMascota, Toast.LENGTH_LONG).show();
                    if (validarPermisos(1)) {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        int requestCode = 1;
                        intent.setType("image/");
                        startActivityForResult(intent, requestCode);

                    }

//                    Intent intent = new Intent();
//                    setResult(RESULT_OK, intent);
//                    finish();
                }
            });
            dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ModificarDatosMascota.this, "No se modifico ningún dato", Toast.LENGTH_LONG).show();
                }
            });
            dialog.show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String uid = currentUser.getUid();
        String displayName = currentUser.getDisplayName();
        StorageReference usuarioRef = storageReference.child(uid);

    }


    public boolean validarPermisos(int requestCode) {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    requestCode);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                modificarDatos(null);
            }
        }
    }

    public void atras(View view) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
