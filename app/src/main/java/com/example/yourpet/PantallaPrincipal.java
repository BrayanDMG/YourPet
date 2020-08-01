package com.example.yourpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PantallaPrincipal extends AppCompatActivity implements MascotaAdapter.OnItemClickListener {

    private List<DetallesMascota> uploadPet;
    private ValueEventListener mDBListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);


        final ProgressBar mProgressCircle = findViewById(R.id.progress_circle);
        //Firebase Auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //Firebase Database Referencia principal y Usuario
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mUsuariosRef = mDatabaseRef.child("USUARIOS");
        DatabaseReference mUsuarioActualRef = mUsuariosRef.child(currentUser.getUid());
//        DatabaseReference mUsuarioRef = mDatabaseRef.child("USUARIOS/" + currentUser.getUid());
        //Firebase Storage Referencia principal y Usuario de las imagenes
        StorageReference firebaseStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imagenStorageRef = firebaseStorageRef.child("USUARIOS").child(currentUser.getUid());

        setTitle("Bienvenido: " + currentUser.getDisplayName());

        //Creacion del recyclerView
        final RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Crear la lista
        uploadPet = new ArrayList<>();
        //Adapter
        final MascotaAdapter mAdapter = new MascotaAdapter(PantallaPrincipal.this, uploadPet);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(PantallaPrincipal.this);


        mDBListener = mUsuarioActualRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                uploadPet.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DetallesMascota upload = postSnapshot.getValue(DetallesMascota.class);
                    upload.setMkey(postSnapshot.getKey());
                    Log.d("key", "" + postSnapshot.getKey());
                    uploadPet.add(upload);


                }

                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mProgressCircle.setVisibility(View.INVISIBLE);

            }
        });
//////////////////////////////////
        findViewById(R.id.button14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://firebasestorage.googleapis.com/v0/b/your-pet-d8d8d.appspot.com/o/USUARIOS%2FUlLPWSxcURNYnBREKHlvjuepoDi2%2FBrayan.28b513afc3c6a4587bfe60f42cb08524.jpg?alt=media&token=97933aeb-215b-46ea-a6eb-9263d6740022";
//                StorageReference imageRef = FirebaseStorage.getInstance().getReference(url);

//                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                StorageReference principal = FirebaseStorage.getInstance().getReference();
                StorageReference imagRef = principal.child("USUARIO").child(currentUser.getUid())
                       .child("https://firebasestorage.googleapis.com/v0/b/your-pet-d8d8d.appspot.com/o/USUARIOS%2FUlLPWSxcURNYnBREKHlvjuepoDi2%2FBrayan.28b513afc3c6a4587bfe60f42cb08524.jpg?alt=media&token=97933aeb-215b-46ea-a6eb-9263d6740022");
               StorageReference imageRR = FirebaseStorage.getInstance().getReferenceFromUrl(url);
                imageRR.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PantallaPrincipal.this, "Se elimino la foto", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PantallaPrincipal.this, "No se pudo eliminar la foto"
                                + " "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


//                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference()
//                        .child("USUARIOS").child(currentUser.getUid()).child("brisapet");
//                //mDatabaseRef.child("brisapet").removeValue();
//                String dataKey = mDatabaseRef.getKey();
//                Toast.makeText(PantallaPrincipal.this, dataKey,Toast.LENGTH_SHORT).show();
//                mDatabaseRef.removeValue();


            }
        });


    }

    public void agregarMascota(View view) {
        Intent intent = new Intent(PantallaPrincipal.this, AgregarMascota.class);
        startActivity(intent);
    }

    public void buscarMascotas(View view) {
        Intent intent = new Intent(PantallaPrincipal.this, MascotasPerdidas.class);
        startActivity(intent);
    }

    public void reportes(View view) {
        Intent intent = new Intent(PantallaPrincipal.this, ModificarReporte.class);
        startActivity(intent);
    }


    public void cambiarCuenta(View view) {
        finish();
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, uploadPet.get(position).getNombre(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onReportarClick(int position) {
        Toast.makeText(this, "Reportar pérdida de  " + uploadPet.get(position).getNombre(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PantallaPrincipal.this,ReportarPerdida.class);
        DetallesMascota selectedItem = uploadPet.get(position);
        intent.putExtra("datosMascota", selectedItem);
        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int position) {
        final DetallesMascota selectedItem = uploadPet.get(position);
        final String url = selectedItem.getUrl();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PantallaPrincipal.this);
        alertDialog.setTitle("Eliminar a "+ selectedItem.getNombre());
        alertDialog.setMessage("Está a punto de eliminar a "+ selectedItem.getNombre() +
                ". Si quiere eliminarlo haga click en ELIMINAR");
        alertDialog.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StorageReference imageRR = FirebaseStorage.getInstance().getReferenceFromUrl(url);
                imageRR.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference()
                                .child("USUARIOS").child(currentUser.getUid()).child(selectedItem.getNombre());
                        mDatabaseRef.removeValue();

                        DatabaseReference mDataPerdiadRef = FirebaseDatabase.getInstance().getReference()
                                .child("PERDIDAS").child(selectedItem.getId()+"/"+selectedItem.getUser_id());
                        mDataPerdiadRef.removeValue();


                        Toast.makeText(PantallaPrincipal.this, "La mascota que selecciono se elminino de su lista", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference()
                                .child("USUARIOS").child(currentUser.getUid()).child(selectedItem.getNombre());
                        mDatabaseRef.removeValue();

                        DatabaseReference mDataPerdiadRef = FirebaseDatabase.getInstance().getReference()
                                .child("PERDIDAS").child(selectedItem.getId()+"/"+selectedItem.getUser_id());
                        mDataPerdiadRef.removeValue();

                        Toast.makeText(PantallaPrincipal.this, "No se pudo eliminar la foto, pero si los datos de la mascota"
                                + " "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alertDialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(PantallaPrincipal.this, "No se elimino nada", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference()
                .child("USUARIOS").child(currentUser.getUid()).removeEventListener(mDBListener);
        Log.d("Listener", "Se destruye el listener");

    }
}