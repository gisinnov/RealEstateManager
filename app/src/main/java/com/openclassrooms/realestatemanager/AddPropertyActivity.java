package com.openclassrooms.realestatemanager;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddPropertyActivity extends AppCompatActivity {
    private static final int REQUEST_SELECT_IMAGES = 1;
    private static final int REQUEST_CAPTURE_PHOTO = 2;
    private static final int REQUEST_LOCATION_PERMISSION = 1001;

    private FusedLocationProviderClient fusedLocationProviderClient;


    private List<Bitmap> selectedImages;
    private ImageAdapter imageAdapter;
    private double latitude;
    private double longitude;
    private String propertyAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        selectedImages = new ArrayList<>();
        imageAdapter = new ImageAdapter(selectedImages);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationPermission();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(imageAdapter);

        Button buttonSave = findViewById(R.id.buttonSave);
        EditText editTextAvailabilityDate = findViewById(R.id.editTextAvailabilityDate);
        editTextAvailabilityDate.setText(Utils.getTodayDate());

        Spinner estateType = findViewById(R.id.spinnerEstateType);
        Spinner rooms = findViewById(R.id.spinnerRooms);
        Spinner bedrooms = findViewById(R.id.spinnerBedrooms);
        Spinner bathrooms = findViewById(R.id.spinnerBathrooms);
        Spinner agent = findViewById(R.id.spinnerAgent);
        Spinner description = findViewById(R.id.spinnerDescription);
        Spinner status = findViewById(R.id.spinnerStatus);

        EditText editTextPropertyArea = findViewById(R.id.editTextPropertyArea);
        EditText editTextPropertyAddress = findViewById(R.id.editTextPropertyAddress);
        CheckBox checkboxSchool = findViewById(R.id.checkboxSchool);
        CheckBox checkboxShops = findViewById(R.id.checkboxShops);
        CheckBox checkboxPark = findViewById(R.id.checkboxPark);

        ArrayAdapter<CharSequence> adapterEstate = ArrayAdapter.createFromResource(this, R.array.ESTATES, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterRooms = ArrayAdapter.createFromResource(this, R.array.ROOMS, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterBedrooms = ArrayAdapter.createFromResource(this, R.array.BEDROOMS, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterBathrooms = ArrayAdapter.createFromResource(this, R.array.BATHROOMS, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterAgent = ArrayAdapter.createFromResource(this, R.array.AGENT, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterDescription = ArrayAdapter.createFromResource(this, R.array.DESCRIPTION, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this, R.array.STATUS, android.R.layout.simple_spinner_item);

        estateType.setAdapter(adapterEstate);
        rooms.setAdapter(adapterRooms);
        bedrooms.setAdapter(adapterBedrooms);
        bathrooms.setAdapter(adapterBathrooms);
        agent.setAdapter(adapterAgent);
        description.setAdapter(adapterDescription);
        status.setAdapter(adapterStatus);

        Button buttonSelectImages = findViewById(R.id.buttonSelectImages);
        buttonSelectImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Images"), REQUEST_SELECT_IMAGES);
            }
        });

        Button buttonAutoAddress = findViewById(R.id.buttonAutoAddress);
        buttonAutoAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation(); // Appel de la méthode pour récupérer la géolocalisation
            }
        });


        Button buttonCapturePhoto = findViewById(R.id.buttonCapturePhoto);
        buttonCapturePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAPTURE_PHOTO);
            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetAvailable(AddPropertyActivity.this)) {
                    EditText editTextPropertyName = findViewById(R.id.editTextPropertyName);
                    EditText editTextPropertyPrice = findViewById(R.id.editTextPropertyPrice);
                    String propertyName = editTextPropertyName.getText().toString();
                    String propertyPriceString = editTextPropertyPrice.getText().toString();
                    String availabilityDate = editTextAvailabilityDate.getText().toString();

                    String propertyAreaString = editTextPropertyArea.getText().toString();
                    String propertyAddress = editTextPropertyAddress.getText().toString();

                    boolean isSchoolNearby = checkboxSchool.isChecked();
                    boolean areShopsNearby = checkboxShops.isChecked();
                    boolean isParkNearby = checkboxPark.isChecked();

                    String estateTypeString = estateType.getSelectedItem().toString();
                    String roomsString = rooms.getSelectedItem().toString();
                    String bedroomsString = bedrooms.getSelectedItem().toString();
                    String bathroomsString = bathrooms.getSelectedItem().toString();
                    String agentString = agent.getSelectedItem().toString();
                    String descriptionString = description.getSelectedItem().toString();
                    String statusString = status.getSelectedItem().toString();

                    double propertyPrice = 0;
                    try {
                        propertyPrice = Double.parseDouble(propertyPriceString);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    double propertyArea = 0;
                    try {
                        propertyArea = Double.parseDouble(propertyAreaString);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(PropertyContract.PropertyEntry.COLUMN_PROPERTY_NAME, propertyName);
                    resultIntent.putExtra(PropertyContract.PropertyEntry.COLUMN_PROPERTY_PRICE, propertyPrice);
                    resultIntent.putExtra(PropertyContract.PropertyEntry.COLUMN_AVAILABILITY_DATE, availabilityDate);

                    resultIntent.putExtra("estateType", estateTypeString);
                    resultIntent.putExtra("rooms", roomsString);
                    resultIntent.putExtra("bedrooms", bedroomsString);
                    resultIntent.putExtra("bathrooms", bathroomsString);
                    resultIntent.putExtra("agent", agentString);
                    resultIntent.putExtra("description", descriptionString);
                    resultIntent.putExtra("status", statusString);

                    resultIntent.putExtra("propertyArea", propertyArea);
                    resultIntent.putExtra("propertyAddress", propertyAddress);
                    resultIntent.putExtra("isSchoolNearby", isSchoolNearby);
                    resultIntent.putExtra("areShopsNearby", areShopsNearby);
                    resultIntent.putExtra("isParkNearby", isParkNearby);

                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(AddPropertyActivity.this, "Pas de connexion réseau disponible", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set current address to the property address field
        String currentAddress = Utils.getCurrentAddress(this);
        editTextPropertyAddress.setText(currentAddress);
    }


    // Method to request location permission
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            getLocation();
        }
    }

    // Method to get location
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                getAddressFromLocation();
                            }
                        }
                    });
        } else {
            showEnableGPSDialog();
        }
    }




    // Method to use geocoding and get the address from location
    private void getAddressFromLocation() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                propertyAddress = address.getAddressLine(0);
                EditText editTextPropertyAddress = findViewById(R.id.editTextPropertyAddress);
                editTextPropertyAddress.setText(propertyAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs ici
        }
    }


    // Method to show a dialog to prompt the user to enable GPS
    private void showEnableGPSDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Veuillez activer le GPS pour obtenir automatiquement l'adresse.")
                .setCancelable(false)
                .setPositiveButton("Paramètres", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_IMAGES) {
                if (data != null) {
                    if (data.getClipData() != null) {
                        ClipData clipData = data.getClipData();
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri imageUri = clipData.getItemAt(i).getUri();
                            handleSelectedImage(imageUri);
                        }
                    } else if (data.getData() != null) {
                        Uri imageUri = data.getData();
                        handleSelectedImage(imageUri);
                    }
                }
            } else if (requestCode == REQUEST_CAPTURE_PHOTO) {
                if (data != null && data.getExtras() != null) {
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    handleCapturedImage(imageBitmap);
                }
            }
        }
    }


    private void handleSelectedImage(Uri imageUri) {
        try {
            // Convert the Uri to a Bitmap
            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            selectedImages.add(imageBitmap);
            imageAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            Toast.makeText(this, "Erreur lors de la lecture de l'image : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleCapturedImage(Bitmap imageBitmap) {
        selectedImages.add(imageBitmap);
        imageAdapter.notifyDataSetChanged();
    }
}