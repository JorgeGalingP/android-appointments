package com.ldm.cogetucita.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ldm.cogetucita.R;
import com.ldm.cogetucita.fragments.DatePickerFragment;
import com.ldm.cogetucita.models.Product;
import com.ldm.cogetucita.repositories.AppointmentRepository;
import com.ldm.cogetucita.repositories.ProductRepository;

public class RegistryActivity extends AppCompatActivity {
    private TextView productTextView;
    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText emailEditText;
    private EditText dateEditText;
    private Button registryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        setTitle("Registrar una nueva cita");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // init Repositories
        final ProductRepository productRepository = new ProductRepository(this);
        final AppointmentRepository appointmentRepository = new AppointmentRepository(this);

        // get Extras
        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("id");
        final Product product = productRepository.findProduct(id);

        // set Views
        productTextView = findViewById(R.id.productTextView);
        productTextView.setText(product.toString());

        nameEditText = findViewById(R.id.editTextName);
        surnameEditText = findViewById(R.id.editTextSurname);
        emailEditText = findViewById(R.id.editTextEmail);

        dateEditText = findViewById(R.id.editTextDate);
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        // set Button
        registryButton = findViewById(R.id.registryButton);
        registryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String surname = surnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String date = dateEditText.getText().toString();

                boolean inserted = appointmentRepository.insertAppointment(product.getId().toString(), name, surname, email, date, "");

                if (inserted) {
                    Toast.makeText(RegistryActivity.this, R.string.registry_message_sucess, Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(RegistryActivity.this, R.string.registry_message_fail, Toast.LENGTH_SHORT).show();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(RegistryActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // go to the main activity
                Intent intent = new Intent(RegistryActivity.this, ProductActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 (January is zero)
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                dateEditText.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }
}