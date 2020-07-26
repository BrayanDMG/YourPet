package com.example.yourpet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MenuPrincipal extends AppCompatActivity {

    //Inflar el menu appbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        //Obtener instancias de Firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String displayName = currentUser.getDisplayName();
        setTitle("Hola " + displayName);

    }


    //Abrir actividad de modificar datos de la mascota
    public void ingresarDatos(View view) {
        Intent intent = new Intent(MenuPrincipal.this, ModificarDatosMascota.class);
        int requestCode = 1;
        startActivityForResult(intent, requestCode);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            //Toast.makeText(this, "pasoresul", Toast.LENGTH_SHORT).show();
            TextView nombre = findViewById(R.id.textViewNombre);
            TextView tipo = findViewById(R.id.textViewTipo);
            TextView id = findViewById(R.id.textViewID);
            TextView raza = findViewById(R.id.textViewRaza);
            TextView color = findViewById(R.id.textViewColor);
            TextView años = findViewById(R.id.textViewAño);
            TextView distrito = findViewById(R.id.textViewDistrito);
            ImageView imagen = findViewById(R.id.imagePrincipal);

            //Obtiene los valores de ModificarDatosMascota
            Mascota mascota = (Mascota) data.getSerializableExtra("info");

            nombre.setText(mascota.getNombre());
            tipo.setText(mascota.getTipo());
            id.setText(mascota.getId());
            raza.setText(mascota.getRaza());
            color.setText(mascota.getColor());
            años.setText(mascota.getAños());
            distrito.setText(mascota.getDistrito());


        }
    }


    public void reportar(MenuItem item) {
        View menuView = findViewById(item.getItemId());
        PopupMenu popupMenu = new PopupMenu(this, menuView);
        popupMenu.getMenuInflater().inflate(R.menu.menu_reporte, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.reportar:
                        Intent intent = new Intent(MenuPrincipal.this, ReportarPerdida.class);
                        int requestCode = 2;
                        startActivityForResult(intent, requestCode);
                        return true;
                    case R.id.modificar:
                        Intent intent1 = new Intent(MenuPrincipal.this, ModificarReporte.class);
                        int requestCode1 = 3;
                        startActivityForResult(intent1, requestCode1);
                        return true;
                    default:
                        return false;
                }

            }
        });
        popupMenu.show();
    }

    public void buscarMascotas(MenuItem item) {
        Intent intent = new Intent(MenuPrincipal.this, MascotasPerdidas.class);
        int requestCode = 4;
        startActivityForResult(intent, requestCode);
    }


    public void agregarMascota(MenuItem item) {
        int requestCode = 5;
    }

    public void cambiarCuenta(View view) {
        finish();
    }


}
