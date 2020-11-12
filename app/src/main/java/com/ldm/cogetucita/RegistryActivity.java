package com.ldm.cogetucita;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ldm.cogetucita.fragments.DatePickerFragment;
import com.ldm.cogetucita.models.Product;
import com.ldm.cogetucita.repositories.ProductRepository;

public class RegistryActivity extends AppCompatActivity {
    private TextView productTextView;
    private EditText dateEditText;
    private Button registryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);
        setTitle("Registrar una nueva cita");

        // init Repositories
        ProductRepository productRepository = new ProductRepository(this);

        // get Extras
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");

        Product product = productRepository.findProduct(id);

        // set Views
        productTextView = findViewById(R.id.productTextView);
        productTextView.setText(product.toString());

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
                // TODO add registerAppointment from AppointmentRepository
                Toast.makeText(RegistryActivity.this, "Registry Done!", Toast.LENGTH_SHORT).show();
            }
        });
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