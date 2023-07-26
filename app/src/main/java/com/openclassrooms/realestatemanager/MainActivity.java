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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_PROPERTY = 1;
    private static final int REQUEST_LOCATION_PERMISSIONS = 2;
    private RecyclerView recyclerView;
    private PropertyAdapter propertyAdapter;
    private List<Property> propertyList;
    private List<Bitmap> imageList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the PropertyDao from the database
        PropertyDao propertyDao = PropertyDatabase.getInstance(this).propertyDao();

        Button buttonAdd = findViewById(R.id.buttonAdd);
        recyclerView = findViewById(R.id.recyclerViewPropertyList);

        // Initialize the propertyList and imageList with empty lists
        propertyList = new ArrayList<>();
        imageList = new ArrayList<>();

        // Add sample images to the imageList
        for (int i = 0; i < 10; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
            imageList.add(bitmap);
        }

        // Create and set the PropertyAdapter with the propertyList and imageList
        propertyAdapter = new PropertyAdapter(this, propertyList, imageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(propertyAdapter);

        // Get property list from the database and update property prices
        compositeDisposable.add(propertyDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(properties -> {
                    propertyList.clear();
                    propertyList.addAll(properties);
                    propertyAdapter.notifyDataSetChanged();
                }));

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
            Property newProperty = (Property) data.getSerializableExtra("NEW_PROPERTY");
            propertyList.add(newProperty);
            propertyAdapter.notifyItemInserted(propertyList.size() - 1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
