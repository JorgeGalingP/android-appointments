package com.ldm.cogetucita.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ldm.cogetucita.R;
import com.ldm.cogetucita.models.Product;
import com.ldm.cogetucita.models.State;
import com.ldm.cogetucita.repositories.ProductRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class UpdateProductActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText priceEditText;
    private Spinner imageSpinner;

    private boolean deleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");

        setTitle("Actualizar el producto P-" + id);

        // init Repositories
        final ProductRepository productRepository = new ProductRepository(this);

        // get Appointment
        final Product product = productRepository.findProduct(id);

        // set EditTexts
        nameEditText = findViewById(R.id.editTextProductUpdateName);
        nameEditText.setText(product.getName());

        descriptionEditText = findViewById(R.id.editTextProductUpdateDescription);
        descriptionEditText.setText(product.getDescription());

        priceEditText = findViewById(R.id.editTextProductUpdatePrice);
        priceEditText.setText(Float.toString(product.getPrice()));

        // set image Spinner
        final List<String> imageList = getImagesFromAssetFolder();
        int selection = imageList.indexOf(product.getImage());

        imageSpinner = findViewById(R.id.stateSpinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, imageList);
        imageSpinner.setAdapter(spinnerAdapter);
        imageSpinner.setSelection(selection);

        Button updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String price = priceEditText.getText().toString();
                String image = imageList.get(imageSpinner.getSelectedItemPosition());

                boolean updated = productRepository.updateProduct(String.valueOf(product.getId()), name, description, Float.parseFloat(price), image);

                if (updated) {
                    Toast.makeText(UpdateProductActivity.this, R.string.update_message_sucess, Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(UpdateProductActivity.this, AdminActivity.class);
                            startActivity(intent);
                        }
                    }, 1500);
                } else {
                    Toast.makeText(UpdateProductActivity.this, R.string.update_message_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProductActivity.this);
                builder
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleted = productRepository.deleteProduct(String.valueOf(product.getId()));
                            }
                        })
                        .setNegativeButton("No, mantener", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                deleted = false;
                            }
                        })
                        .setMessage("¿Estás seguro de querer borrar este producto?")
                        .setTitle("Eliminar");
                AlertDialog dialog = builder.create();
                dialog.show();

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (deleted) {
                            Toast.makeText(UpdateProductActivity.this, R.string.delete_message_sucess, Toast.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(UpdateProductActivity.this, AdminActivity.class);
                                    startActivity(intent);
                                }
                            }, 1500);
                        } else {
                            Toast.makeText(UpdateProductActivity.this, R.string.delete_message_fail, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // go to the main activity
                Intent intent = new Intent(this, AdminActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<String> getImagesFromAssetFolder(){
        List<String> imagesList = new ArrayList<>();

        try {
            String regex = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)";
            Pattern pattern = Pattern.compile(regex);
            String[] images = getAssets().list("");

            for (String image : images) {
                Matcher matcher = pattern.matcher(image);

                if (!image.equals("") && matcher.matches()) {
                    imagesList.add(image);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(UpdateProductActivity.this, R.string.update_images_message_fail, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(UpdateProductActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
            }, 1500);
        }

        return imagesList;
    }
}