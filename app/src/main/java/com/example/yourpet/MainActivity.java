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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Inicio");

        //Instancias Firebase
        FirebaseAuth firebaseAuth = getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        //Comprobar si existe un usuario logueado
        if (currentUser != null) {
            Toast.makeText(this, "Usuario logueado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Usuario no logueado", Toast.LENGTH_SHORT).show();
        }


    }

    public void iniciarSesion(View view) {

        //Cargar la lista de proveedores y mandar la activity
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

    //La respuesta de la actividad
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Respuesta de iniciar sexión
        if (requestCode == 1) {
            //Si no hay error continua
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                //Instancias Firebase
                FirebaseAuth firebaseAuth = getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                //Comprobar si su correo esta verificado. Si no está verificado se le mando un correo
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


    // Entrar a la página principal
    public void comenzar(View view) {

        //Instancias Firebase
        FirebaseAuth firebaseAuth = getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        //Comprobar que hay un usuario logueado
        if (currentUser != null) {
            Log.d("correo", "estado de correo " + currentUser.isEmailVerified());
            if (currentUser.isEmailVerified()) {

                //Datos del usuario
                String email = currentUser.getEmail();
                String uid = currentUser.getUid();
                String displayName = currentUser.getDisplayName();

                //Abrir actividad Principal
                Intent intent = new Intent(this, MenuPrincipal.class);
                startActivity(intent);

                Toast.makeText(this, "Bienvenido " + displayName, Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(this, "Verifique su correo y vuelva iniciar sesión", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Inicie sesión para comenzar", Toast.LENGTH_SHORT).show();
        }

    }

    //Cerrar sesión del usuario
    public void cerrarSesion(View view) {

        //Instancias Firebase
        FirebaseAuth firebaseAuth = getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

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




