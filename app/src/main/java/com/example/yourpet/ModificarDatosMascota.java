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
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ModificarDatosMascota extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos_mascota);
        setTitle("Modificar datos de la mascota");


        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Subir imagen con metadatos
        if (requestCode == 1 && resultCode == RESULT_OK) {


            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();//
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();//
            final String uid = currentUser.getUid();//
            final String displayName = currentUser.getDisplayName();//
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();//
            final StorageReference storageReference = firebaseStorage.getReference();//
            StorageReference usuarioRef = storageReference.child(uid);//
            usuarioRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    //Número de archivos dentro de la carpeta del usuario
                    int numImage = listResult.getItems().size();

                    //Método subir imagen
                    subirImagenStorage(data, numImage);
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    subirImagenStorage(data, 0);
                }
            });

        }


    }

    public void subirImagenStorage(Intent data, int i) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        final String uid = currentUser.getUid();
        final String displayName = currentUser.getDisplayName();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference storageReference = firebaseStorage.getReference();
        StorageReference usuarioRef = storageReference.child(uid);

        //Ocultar botones hasta que termine de enviar la imagen
        Button bsubir = findViewById(R.id.buttonSubir);
        Button bregresar = findViewById(R.id.buttonRegresar);
        ImageView imageView = findViewById(R.id.imageViewM);
        bsubir.setEnabled(false);
        bregresar.setEnabled(false);

        Uri path = data.getData();
        imageView.setImageURI(path);

        //Crear Carpeta del usuario
        StorageReference imagenesRef;
        imagenesRef = storageReference.child(uid + "/" + displayName + i);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = df.format(c.getTime());

        RadioButton perroB = findViewById(R.id.rBperro);
        RadioButton gatoB = findViewById(R.id.rBgato);
        RadioButton conejoB = findViewById(R.id.rBconejo);
        RadioButton otrosB = findViewById(R.id.rBotros);
        EditText nombreM = findViewById(R.id.mnombre);
        EditText razaM = findViewById(R.id.mraza);
        EditText colorM = findViewById(R.id.mcolor);
        EditText añosM = findViewById(R.id.maños);
        Spinner distritosM = findViewById(R.id.spinnermDistritos);
        String tipo;

        //Generar ID de la mascota
        char[] aNom = (nombreM.getText().toString()).toCharArray();
        char[] aDist = (distritosM.getSelectedItem().toString()).toCharArray();
        String dist = "";
        if(distritosM.getSelectedItem().toString().length()<=6){
            dist = dist + aDist[0] + aDist[1] + aDist[2];
        }else{
            if(aDist[3]==' ' && aDist[4]!='J'&& aDist[0]!='S'){
                dist = dist + aDist[0] + aDist[4] + aDist[5];
            } else if (aDist[2]==' ') {
                dist = dist + aDist[0] + aDist[3] + aDist[4];
            }else if (aDist[4]=='A' && aDist[5]==' ') {
                dist = dist + aDist[0] + aDist[6] + aDist[7];
            }else if ((aDist[5]=='E'||aDist[5]=='O') && aDist[6]==' ') {
                dist = dist + aDist[0] + aDist[7] + aDist[8];
            }else if(aDist[3]==' ' && aDist[4]=='J'){
                dist = dist + aDist[0] + aDist[4] + aDist[12];
            }else if(aDist[3]==' ' && aDist[5]!='J' && aDist[0]=='S'){
                dist = dist + aDist[0] + aDist[5] + aDist[6];
            }else if(aDist[0]=='V' && aDist[6]=='E'){
                dist = dist + aDist[0] + aDist[6] + aDist[9];
            }else if(aDist[0]=='V' && aDist[6]=='M'){
                dist = dist + aDist[0] + aDist[6] + aDist[16];
            }else dist = dist + aDist[0] + aDist[1] + aDist[2];
        }



        String id;
        if (perroB.isChecked() == true) {
            tipo = "Perro";
            id = "P";
        } else if (gatoB.isChecked() == true) {
            tipo = "Gato";
            id = "G";
        } else if (gatoB.isChecked() == true) {
            tipo = "Conejo";
            id = "C";
        } else {
            tipo = "Otro";
            id = "O";
        }


        id = id + aNom[0] + añosM.getText().toString() + dist;


        final Mascota mascota = new Mascota(nombreM.getText().toString(), tipo, id, razaM.getText().toString(),
                colorM.getText().toString(), añosM.getText().toString(), distritosM.getSelectedItem().toString());

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata("Nombre", mascota.getNombre())
                .setCustomMetadata("Mascota", mascota.getTipo())
                .setCustomMetadata("ID", mascota.getId())
                .setCustomMetadata("Raza", mascota.getRaza())
                .setCustomMetadata("Color", mascota.getColor())
                .setCustomMetadata("Años", mascota.getAños())
                .setCustomMetadata("Distrito", mascota.getDistrito())
                .setCustomMetadata("Fecha",date).build();
        imagenesRef.putFile(path, metadata)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("infoAapp", "subida exitosa");
                        Toast.makeText(ModificarDatosMascota.this, "subida exitosa", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("infoAapp", "error al subir");
                        Toast.makeText(ModificarDatosMascota.this, "error al subir", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        long bytetransf = taskSnapshot.getBytesTransferred();
                        long totalByteCount = taskSnapshot.getTotalByteCount();
                        double progreso = (100.0 * bytetransf) / totalByteCount;
                        TextView prog = findViewById(R.id.textViewprogreso);
                        prog.setText("Porcentaje de subida " + Math.round(progreso) + " %");
                        Log.d("infoAapp", "Porcentaje de subida " + Math.round(progreso) + " %");
                        if (progreso == 100) {

                            //Mostrar los botones y terminar la actividad
                            Button bsubir = findViewById(R.id.buttonSubir);
                            Button bregresar = findViewById(R.id.buttonRegresar);
                            bsubir.setEnabled(true);
                            bregresar.setEnabled(true);

                            Intent intent = new Intent();
                            intent.putExtra("info", mascota);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });


    }

    //Comprobar campos y abrir galeria
    public void subirImagenStream(View view) {

        RadioButton perroB = findViewById(R.id.rBperro);
        RadioButton RgatoB = findViewById(R.id.rBgato);
        final EditText nombreM = findViewById(R.id.mnombre);
        final EditText razaM = findViewById(R.id.mraza);
        final EditText colorM = findViewById(R.id.mcolor);
        final EditText añosM = findViewById(R.id.maños);
        final Spinner distritosM = findViewById(R.id.spinnermDistritos);
        TextView textError = findViewById(R.id.textError1);
        final String tipo;

        textError.setVisibility(View.INVISIBLE);
        String textoaños = añosM.getText().toString();

        int i = 0;

        if (nombreM.getText().length() == 0) {
            nombreM.setError("Debe llenar este espacio");
            i++;
        } else if (nombreM.getText().length() > 15) {
            textError.setText("Máximo 15 caracteres");
            textError.setVisibility(View.VISIBLE);
            i++;
        }
        if (razaM.getText().length() == 0) {
            razaM.setError("Debe llenar este espacio");
            i++;
        } else if (razaM.getText().length() > 20) {
            textError.setText("Máximo 15 caracteres");
            textError.setVisibility(View.VISIBLE);
            i++;
        }
        if (colorM.getText().length() == 0) {
            colorM.setError("Debe llenar este espacio");
            i++;
        } else if (colorM.getText().length() > 20) {
            textError.setText("Máximo 20 caracteres");
            textError.setVisibility(View.VISIBLE);
            i++;
        }
        if (añosM.getText().length() == 0) {
            añosM.setError("Debe llenar este espacio");
            i++;
        }
        if (añosM.getText().length() != 0) {
            int numeroaños = Integer.parseInt(textoaños);
            if (numeroaños > 15) {
                añosM.setError("Máximo 15");
                i++;
            }
        }


        if (i == 0) {


            if (validarPermisos(1)) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Guardar los datos de " + nombreM.getText());
                dialog.setMessage("Se modificarán los datos anteriores por los nuevos datos ingresados." +
                        " Debe seleccionar una foto de su galeria que identifique a " + nombreM.getText() + ". " +
                        " Si completo correctamento los datos presiones ACTUALIZAR.");
                dialog.setPositiveButton("ACTUALIZAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        int requestCode = 1;
                        intent.setType("image/");
                        startActivityForResult(intent, requestCode);
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
                subirImagenStream(null);
            }
        }
    }


    public void atras(View view) {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
