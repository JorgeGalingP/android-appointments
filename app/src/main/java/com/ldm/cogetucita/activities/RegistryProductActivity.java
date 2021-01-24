package com.ldm.cogetucita.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ldm.cogetucita.R;
import com.ldm.cogetucita.adapters.SpinnerImageAdapter;
import com.ldm.cogetucita.models.Product;
import com.ldm.cogetucita.repositories.ProductRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistryProductActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText priceEditText;
    private Spinner imageSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry_product);
        setTitle("Registrar un nuevo producto");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // init Repositories
        final ProductRepository productRepository = new ProductRepository(this);

        // set Views
        nameEditText = findViewById(R.id.editTextProductName);
        descriptionEditText = findViewById(R.id.editTextProductDescription);
        priceEditText = findViewById(R.id.editTextProductPrice);

        // set image Spinner
        final List<String> imageList = getImagesFromAssetFolder();
        int selection = imageList.indexOf(imageList.get(0));

        imageSpinner = findViewById(R.id.imageSpinner);
        SpinnerImageAdapter spinnerAdapter = new SpinnerImageAdapter(this, R.layout.item_image_spinner_product, imageList);
        imageSpinner.setAdapter(spinnerAdapter);
        imageSpinner.setAdapter(spinnerAdapter);
        imageSpinner.setSelection(selection);

        // set Button
        Button registryButton = findViewById(R.id.registryProductButton);
        registryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                float price = Float.parseFloat(priceEditText.getText().toString());
                String image = imageList.get(imageSpinner.getSelectedItemPosition());

                boolean inserted = productRepository.insertProduct(name, description, price, image);

                if (inserted) {
                    Toast.makeText(RegistryProductActivity.this, R.string.registry_message_sucess, Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(RegistryProductActivity.this, R.string.registry_message_fail, Toast.LENGTH_SHORT).show();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(RegistryProductActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                }, 1500);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // go to the admin activity
                Intent intent = new Intent(RegistryProductActivity.this, AdminActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<String> getImagesFromAssetFolder(){
        List<String> imagesFinalList = new ArrayList<>();

        try {
            String regex = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)";
            Pattern pattern = Pattern.compile(regex);

            // get images from assets folder
            String[] imagesAsset = getAssets().list("");

            // get images from "Images" folder
            ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
            String[] imagesFolder = wrapper.getDir("Images", MODE_PRIVATE).list();

            // final image's list
            List<String> imagesList = new ArrayList<>();
            imagesList.addAll(Arrays.asList(imagesAsset));
            imagesList.addAll(Arrays.asList(imagesFolder));

            for (String image : imagesList) {
                Matcher matcher = pattern.matcher(image);

                if (!image.equals("") && matcher.matches()) {
                    imagesFinalList.add(image);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(this, R.string.update_images_message_fail, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(RegistryProductActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
            }, 1500);
        }

        return imagesFinalList;
    }
}