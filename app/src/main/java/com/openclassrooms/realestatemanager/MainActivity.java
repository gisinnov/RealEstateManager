package com.openclassrooms.realestatemanager;

import static com.openclassrooms.realestatemanager.PermissionUtils.checkLocationPermission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_PROPERTY = 1;
    private static final int REQUEST_LOCATION_PERMISSIONS = 2;
    private TextView textViewPropertyDetails;
    private List<String> propertyDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        textViewPropertyDetails = findViewById(R.id.textViewPropertyDetails);
        propertyDetailsList = new ArrayList<>();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetAvailable(MainActivity.this)) {
                    if (checkLocationPermission(MainActivity.this)) {
                        // Les autorisations de localisation sont accordées, vous pouvez continuer avec l'action souhaitée
                        Intent intent = new Intent(MainActivity.this, AddPropertyActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_ADD_PROPERTY);
                    } else {
                        // Les autorisations de localisation n'ont pas été accordées, affichez un message à l'utilisateur ou demandez les autorisations
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

        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Les autorisations de localisation ont été accordées par l'utilisateur, vous pouvez continuer avec l'action souhaitée
                Intent intent = new Intent(MainActivity.this, AddPropertyActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_PROPERTY);
            } else {
                // Les autorisations de localisation ont été refusées par l'utilisateur, vous pouvez afficher un message ou prendre une autre action
                Toast.makeText(MainActivity.this, "Permissions de localisation refusées", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean checkLocationPermission(Context context) {
        String fineLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION;
        String coarseLocationPermission = Manifest.permission.ACCESS_COARSE_LOCATION;

        int fineLocationPermissionResult = ContextCompat.checkSelfPermission(context, fineLocationPermission);
        int coarseLocationPermissionResult = ContextCompat.checkSelfPermission(context, coarseLocationPermission);

        return fineLocationPermissionResult == PackageManager.PERMISSION_GRANTED &&
                coarseLocationPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_PROPERTY) {
            if (resultCode == Activity.RESULT_OK) {
                String propertyName = data.getStringExtra(PropertyContract.PropertyEntry.COLUMN_PROPERTY_NAME);
                double propertyPrice = data.getDoubleExtra(PropertyContract.PropertyEntry.COLUMN_PROPERTY_PRICE, 0);
                String availabilityDate = data.getStringExtra(PropertyContract.PropertyEntry.COLUMN_AVAILABILITY_DATE);

                double propertyPriceInEuros = Utils.convertDollarToEuro(propertyPrice);

                NumberFormat euroFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
                String formattedPrice = euroFormat.format(propertyPriceInEuros);

                String propertyDetails = "Property Name: " + propertyName + "\n" +
                        "Property Price in Euro: " + formattedPrice + "\n" +
                        "Availability Date: " + availabilityDate;

                propertyDetailsList.add(propertyDetails);
                updatePropertyDetails();
            }
        }
    }

    private void updatePropertyDetails() {
        StringBuilder sb = new StringBuilder();
        for (String propertyDetails : propertyDetailsList) {
            sb.append(propertyDetails).append("\n\n");
        }
        textViewPropertyDetails.setText(sb.toString());
    }
}
