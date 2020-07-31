package com.example.yourpet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AgregarMascota extends AppCompatActivity {

    private String mTipo, mId, mDistrito;
    private Uri mImageUri;
    private StorageTask imageRefUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_mascota);

        Log.d("inicio", "agregar mascota");

        //Botones de accion
        Button mButtonChooseImage = findViewById(R.id.buttonElegirFoto);
        Button mButtonUpload = findViewById(R.id.buttonSubirDatos);
        TextView mTextViewMostrarMascotas = findViewById(R.id.textViewMostrarMascota);
        //Entradas de información
        EditText mEditTextNombre = findViewById(R.id.editTextSnombre);
        EditText mEditTextRaza = findViewById(R.id.editTextSraza);
        EditText mEditTextColor = findViewById(R.id.editTextScolor);
        EditText mEditTextAños = findViewById(R.id.editTextSaño);
        //Otros componentes
        Spinner mSpinnerdistrito = findViewById(R.id.spinnerSdistrito);
        RadioGroup mRadioG = findViewById(R.id.radioGroupTipo);
        ImageView mImageMascota = findViewById(R.id.imageViewSmascota);
        ProgressBar mProgressBar = findViewById(R.id.progress_bar);
        //Firebase Auth Storage RealTime
        ////Firebase Auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        ////Firebase Realtiem
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        ////Firebase Storage
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference mStorageRef = firebaseStorage.getReference();



        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageRefUpload != null && imageRefUpload.isInProgress()) {
                    Toast.makeText(AgregarMascota.this,
                            "Sus datos se están cargando", Toast.LENGTH_SHORT).show();
                } else openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarDatosIngresados()) {
                    if (imageRefUpload != null && imageRefUpload.isInProgress()) {
                        Toast.makeText(AgregarMascota.this,
                                "Sus datos se están cargando", Toast.LENGTH_SHORT).show();
                    } else {
                        upLoadFile();
                    }

                } else Toast.makeText(AgregarMascota.this,
                        "Debe completar los datos de su mascota antes de subir la foto", Toast.LENGTH_SHORT).show();

            }
        });

        mTextViewMostrarMascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Comprueba que no se esta descangando ningun archivo antes de enviar el intent
                if (imageRefUpload != null && imageRefUpload.isInProgress()) {
                    Toast.makeText(AgregarMascota.this,
                            "Sus datos se están cargando", Toast.LENGTH_SHORT).show();
                } else {
                    openMascotasUsuarioActivity();
                }


            }
        });


    }

    private void openMascotasUsuarioActivity() {
//        Intent intent = new Intent(this, PantallaPrincipal.class);
//        startActivity(intent);
//        if(getIntent().getStringExtra("act").equals("primera vez")){
//            finish();
//        }

        Intent intent = getIntent();
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            ImageView mImageMascota = findViewById(R.id.imageViewSmascota);
            Picasso.with(this).load(mImageUri).into(mImageMascota);
            Toast.makeText(AgregarMascota.this, "" + nombreFotoDispositivo(mImageUri), Toast.LENGTH_SHORT).show();


        }

    }


    private void upLoadFile() {
        if (mImageUri != null) {
            //Firebase Auth
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

            //Entradas de información
            EditText mEditTextNombre = findViewById(R.id.editTextSnombre);
            final EditText mEditTextRaza = findViewById(R.id.editTextSraza);
            final EditText mEditTextColor = findViewById(R.id.editTextScolor);
            final EditText mEditTextAños = findViewById(R.id.editTextSaño);
            //Otros componentes
            Spinner mSpinnerdistrito = findViewById(R.id.spinnerSdistrito);
            RadioGroup mRadioG = findViewById(R.id.radioGroupTipo);

//            StorageReference firebaseStorageRef = FirebaseStorage.getInstance().getReference();
//            StorageReference carpetaUsuariosRef = firebaseStorageRef.child("USUARIOS");
//            StorageReference userStorageRet = carpetaUsuariosRef.child(currentUser.getUid() + "/");
//            StorageReference imagenStorageRef = userStorageRet.child(currentUser.getDisplayName()
//                    + "." + nombreFotoDispositivo(mImageUri));
            StorageReference firebaseStorageRef = FirebaseStorage.getInstance().getReference();
            final StorageReference imagenStorageRef = firebaseStorageRef.child("USUARIOS").child(currentUser.getUid())
                    .child(currentUser.getDisplayName() + "." + nombreFotoDispositivo(mImageUri));

            final Mascota mascota = obtenerMetadatos();
            String date = obtenerFecha();

            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setCustomMetadata("Nombre", mascota.getNombre())
                    .setCustomMetadata("Mascota", mascota.getTipo())
                    .setCustomMetadata("ID", mascota.getId())
                    .setCustomMetadata("Raza", mascota.getRaza())
                    .setCustomMetadata("Color", mascota.getColor())
                    .setCustomMetadata("Años", mascota.getAños())
                    .setCustomMetadata("Distrito", mascota.getDistrito())
                    .setCustomMetadata("Fecha", date).build();

            imageRefUpload = imagenStorageRef.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ProgressBar mProgressBar = findViewById(R.id.progress_bar);
                            mProgressBar.setProgress(0);
                        }
                    }, 300);

                    Toast.makeText(AgregarMascota.this, "Subida Exitosa", Toast.LENGTH_SHORT).show();
                    final EditText mEditTextNombre = findViewById(R.id.editTextSnombre);


                    //String url = "/"+currentUser.getDisplayName() + "." + nombreFotoDispositivo(mImageUri);
                    imagenStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DetallesMascota uploadPet = new DetallesMascota(mascota.getNombre(), mascota.getTipo(),
                                    mascota.getId(), mascota.getRaza(), mascota.getColor()
                                    , mascota.getAños(), mascota.getDistrito(), uri.toString());
                            DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
                            //String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child("USUARIOS/" + currentUser.getUid() + "/" + mascota.getNombre()).setValue(uploadPet);

                            mEditTextNombre.setText("");
                            mEditTextRaza.setText("");
                            mEditTextColor.setText("");
                            mEditTextAños.setText("");
                            ImageView mImageMascota = findViewById(R.id.imageViewSmascota);
                            Picasso.with(AgregarMascota.this).load(R.drawable.camara).into(mImageMascota);



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            DetallesMascota uploadPet = new DetallesMascota(mascota.getNombre(), mascota.getTipo(),
                                    mascota.getId(), mascota.getRaza(), mascota.getColor()
                                    , mascota.getAños(), mascota.getDistrito(), "");
                            DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
                            //String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child("USUARIOS/" + currentUser.getUid() + "/" + mascota.getNombre()).setValue(uploadPet);
                            Toast.makeText(AgregarMascota.this, "No se encontro Url para descargar la foto" +
                                    e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AgregarMascota.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    ProgressBar mProgressBar = findViewById(R.id.progress_bar);
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);
                }
            });
        } else {
            Toast.makeText(this, "No selecciono ningun archivo", Toast.LENGTH_SHORT).show();
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        Intent intent = new Intent();

        int PICK_IMAGE_REQUEST = 1;
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String nombreFotoDispositivo(Uri u) {
        String fileName = "";
        if (u.getScheme().equals("file")) {
            fileName = u.getLastPathSegment();
        } else {
            Cursor cursor = null;
            try {
                cursor = getContentResolver().query(u, new String[]{MediaStore.Images.ImageColumns.DISPLAY_NAME}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));


                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        return fileName;
    }

    private String obtenerFecha() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = df.format(c.getTime());
        return date;
    }

    private Mascota obtenerMetadatos() {
        //Entradas de información
        EditText mEditTextNombre = findViewById(R.id.editTextSnombre);
        EditText mEditTextRaza = findViewById(R.id.editTextSraza);
        EditText mEditTextColor = findViewById(R.id.editTextScolor);
        EditText mEditTextAños = findViewById(R.id.editTextSaño);
        //Otros componentes
        Spinner mSpinnerdistrito = findViewById(R.id.spinnerSdistrito);
        RadioGroup mRadioG = findViewById(R.id.radioGroupTipo);
        RadioButton radioButton;

        //Sacar el texto del spinner
        String distText = mSpinnerdistrito.getSelectedItem().toString();
        //Obtener caracteres del distrito
        String dist = obeneterCaracteresDist(distText);
        //completar el id
        String id = "";
        //Obtener texto radiobutton
        int radioId = mRadioG.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String tipo = "" + radioButton.getText();
        char[] atipo = tipo.toCharArray();
        char[] araza = mEditTextRaza.getText().toString().toCharArray();
        //Generar ID de la mascota
        char[] aNom = (mEditTextNombre.getText().toString()).toCharArray();
        id = id + atipo[0] + aNom[0] + araza[0] + mEditTextAños.getText().toString() + dist;

        //Obtener la metadata
        Mascota metadata = new Mascota(mEditTextNombre.getText().toString(), tipo, id, mEditTextRaza.getText().toString(),
                mEditTextColor.getText().toString(), mEditTextAños.getText().toString(), distText);


        return metadata;
    }

    private boolean validarDatosIngresados() {
        EditText mEditTextNombre = findViewById(R.id.editTextSnombre);
        EditText mEditTextRaza = findViewById(R.id.editTextSraza);
        EditText mEditTextColor = findViewById(R.id.editTextScolor);
        EditText mEditTextAños = findViewById(R.id.editTextSaño);

        int i = 0;

        if (mEditTextNombre.getText().length() == 0) {
            mEditTextNombre.setError("Debe llenar este espacio");
            i++;
        } else if (mEditTextNombre.getText().length() > 15) {
            mEditTextNombre.setError("Máximo 15 caracteres");
            i++;
        }
        if (mEditTextRaza.getText().length() == 0) {
            mEditTextRaza.setError("Debe llenar este espacio");
            i++;
        } else if (mEditTextRaza.getText().length() > 20) {
            mEditTextRaza.setError("Máximo 15 caracteres");
            i++;
        }
        if (mEditTextColor.getText().length() == 0) {
            mEditTextColor.setError("Debe llenar este espacio");
            i++;
        } else if (mEditTextColor.getText().length() > 20) {
            mEditTextColor.setError("Máximo 20 caracteres");
            i++;
        }
        if (mEditTextAños.getText().length() == 0) {
            mEditTextAños.setError("Debe llenar este espacio");
            i++;
        }
        if (mEditTextAños.getText().length() != 0) {
            int numeroaños = Integer.parseInt(mEditTextAños.getText().toString());
            if (numeroaños > 15) {
                mEditTextAños.setError("Máximo 15");
                i++;
            }
        }

        if (i == 0) {
            return true;
        } else return false;

    }

    private String obeneterCaracteresDist(String distrito) {
        char[] aDist = (distrito).toCharArray();
        String dist = "";
        if (distrito.length() <= 6) {
            dist = dist + aDist[0] + aDist[1] + aDist[2];
        } else {
            if (aDist[3] == ' ' && aDist[4] != 'J' && aDist[0] != 'S') {
                dist = dist + aDist[0] + aDist[4] + aDist[5];
            } else if (aDist[2] == ' ') {
                dist = dist + aDist[0] + aDist[3] + aDist[4];
            } else if (aDist[4] == 'A' && aDist[5] == ' ') {
                dist = dist + aDist[0] + aDist[6] + aDist[7];
            } else if ((aDist[5] == 'E' || aDist[5] == 'O') && aDist[6] == ' ') {
                dist = dist + aDist[0] + aDist[7] + aDist[8];
            } else if (aDist[3] == ' ' && aDist[4] == 'J') {
                dist = dist + aDist[0] + aDist[4] + aDist[12];
            } else if (aDist[3] == ' ' && aDist[5] != 'J' && aDist[0] == 'S') {
                dist = dist + aDist[0] + aDist[4] + aDist[5];
            } else if (aDist[0] == 'V' && aDist[6] == 'E') {
                dist = dist + aDist[0] + aDist[6] + aDist[9];
            } else if (aDist[0] == 'V' && aDist[6] == 'M') {
                dist = dist + aDist[0] + aDist[6] + aDist[16];
            } else dist = dist + aDist[0] + aDist[1] + aDist[2];
        }

        return dist;
    }


}