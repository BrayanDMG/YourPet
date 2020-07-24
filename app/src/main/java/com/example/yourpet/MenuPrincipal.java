package com.example.yourpet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MenuPrincipal extends AppCompatActivity {

    ImageView imagen;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        imagen = findViewById(R.id.Image);
        String displayName = currentUser.getDisplayName();

        setTitle(displayName);


    }

    public void modificarDatosMascota(View view) {
        Intent intent = new Intent(MenuPrincipal.this, ModificarDatosMascota.class);
        int requestCode = 1;
        startActivityForResult(intent, requestCode);
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
                        int requestCode = 3;
                        startActivityForResult(intent, requestCode);
                        return true;
                    case R.id.modificar:
                        Intent intent1 = new Intent(MenuPrincipal.this, ModificarReporte.class);
                        int requestCode1 = 4;
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
        int requestCode = 5;
        startActivityForResult(intent, requestCode);
    }


    public void agregarMascota(MenuItem item) {

    }


}
