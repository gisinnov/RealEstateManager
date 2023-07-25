package com.openclassrooms.realestatemanager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_PROPERTY = 1;
    private static final int REQUEST_LOCATION_PERMISSIONS = 2;
    private RecyclerView recyclerView;
    private PropertyAdapter propertyAdapter;
    private List<PropertyModel> propertyList;
    private List<Bitmap> imageList; // Ajout de la liste d'images

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        recyclerView = findViewById(R.id.recyclerViewPropertyList);
        propertyList = new ArrayList<>();
        propertyAdapter = new PropertyAdapter(this, propertyList, imageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(propertyAdapter);

// Initialisation de votre imageList
        List<Bitmap> imageList = new ArrayList<>();
// Exemple de comment remplir votre imageList avec des données de test
        for (int i = 0; i < 10; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
            imageList.add(bitmap);
        }

// Initialisation de votre PropertyAdapter avec les deux listes
        propertyAdapter = new PropertyAdapter(this, propertyList, imageList);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetAvailable(MainActivity.this)) {
                    if (PermissionUtils.checkLocationPermission(MainActivity.this)) {
                        Intent intent = new Intent(MainActivity.this, AddPropertyActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_ADD_PROPERTY);
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSIONS);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No network connection available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSIONS && PermissionUtils.didUserGrantPermissions(grantResults)) {
            Intent intent = new Intent(MainActivity.this, AddPropertyActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_PROPERTY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_PROPERTY && resultCode == Activity.RESULT_OK) {
            PropertyModel newProperty = data.getParcelableExtra("NEW_PROPERTY");
            propertyList.add(newProperty);
            propertyAdapter.notifyItemInserted(propertyList.size() - 1);
        }
    }
}
