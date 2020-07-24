package com.example.yourpet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import static com.google.firebase.auth.FirebaseAuth.*;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Inicio");

        firebaseAuth = getInstance();
        currentUser = firebaseAuth.getCurrentUser();



        if (currentUser != null) {
            Toast.makeText(this, "Usuario logueado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Usuario no logueado", Toast.LENGTH_SHORT).show();
        }


    }

    public void iniciarSesion(View view) {
//        Intent intent = new Intent(MainActivity.this, IniciarSesion.class);
//        startActivity(intent);
//        startActivity(new Intent(this, IniciarSesion.class));
//        finish();
        List<AuthUI.IdpConfig> listaProveedores =
                Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build());
        startActivityForResult(AuthUI.getInstance().
                        createSignInIntentBuilder().
                        setLogo(R.drawable.logueoyourpet).
                        setAvailableProviders(listaProveedores)
                        .build(),
                1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                firebaseAuth = getInstance();
                currentUser = firebaseAuth.getCurrentUser();
                Log.d("correo", "estado de correo " + currentUser.isEmailVerified());
                if (!currentUser.isEmailVerified()) {
                    currentUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "Se le envio un correo para validar su cuenta",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,
                                    "Error al verificar su correo, vuelva a inciar sesión",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(this, "Inicio erroneo", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void comenzar(View view) {
//        Intent intent = new Intent(MainActivity.this,CrearCuenta.class);
//        startActivity(intent);
//        startActivity(new Intent(this, CrearCuenta.class));
//        finish();
        firebaseAuth = getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            Log.d("correo", "estado de correo " + currentUser.isEmailVerified());
            if (currentUser.isEmailVerified()) {
                String email = currentUser.getEmail();
                String uid = currentUser.getUid();
                String displayName = currentUser.getDisplayName();
                boolean emailVerified = currentUser.isEmailVerified();
                startActivity(new Intent(this, MenuPrincipal.class));
                Toast.makeText(this, "Bienvenido " + displayName, Toast.LENGTH_SHORT).show();
                //finish();
            } else
                Toast.makeText(this, "Verifique su correo y vuelva iniciar sesión", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Inicie sesión para comenzar", Toast.LENGTH_SHORT).show();
        }

    }

    public void cerrarSesion(View view) {

        if (currentUser != null) {
            AuthUI.getInstance().signOut(this).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MainActivity.this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Hubo un error al cerrar sesión", Toast.LENGTH_SHORT).show();
                }
            });

        } else{
            Toast.makeText(MainActivity.this, "No hay ningún usuario logueado", Toast.LENGTH_SHORT).show();
        }
    }


}




