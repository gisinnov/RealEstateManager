package com.openclassrooms.realestatemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddPropertyActivity extends AppCompatActivity {
    private static final int REQUEST_SELECT_IMAGES = 1;
    private static final int REQUEST_CAPTURE_PHOTO = 2;


    private List<Bitmap> selectedImages;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        selectedImages = new ArrayList<>();
        imageAdapter = new ImageAdapter(selectedImages);

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
