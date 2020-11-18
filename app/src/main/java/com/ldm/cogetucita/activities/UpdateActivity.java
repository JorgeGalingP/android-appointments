package com.ldm.cogetucita.activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ldm.cogetucita.R;
import com.ldm.cogetucita.models.Appointment;
import com.ldm.cogetucita.models.Product;
import com.ldm.cogetucita.models.State;
import com.ldm.cogetucita.repositories.AppointmentRepository;
import com.ldm.cogetucita.repositories.ProductRepository;

public class UpdateActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText emailEditText;
    private EditText dateEditText;
    private EditText locationEditText;
    private Spinner stateSpinner;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");

        setTitle("Actualizar la cita ID-" + id);

        // init Repositories
        final AppointmentRepository appointmentRepository = new AppointmentRepository(this);

        final Appointment appointment = appointmentRepository.findAppointment(id);

        // set EditTexts
        nameEditText = findViewById(R.id.editTextName);
        nameEditText.setText(appointment.getName());

        surnameEditText = findViewById(R.id.editTextSurname);
        surnameEditText.setText(appointment.getSurname());

        emailEditText = findViewById(R.id.editTextEmail);
        emailEditText.setText(appointment.getEmail());

        dateEditText = findViewById(R.id.editTextDate);
        dateEditText.setText(appointment.getDate());

        locationEditText = findViewById(R.id.editTextLocation);
        locationEditText.setText(appointment.getLocation());

        // set Spinner
        State selectedState = null;
        stateSpinner = findViewById(R.id.stateSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.state_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(spinnerAdapter);
        stateSpinner.setSelection(0); // set default selection to 0
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] array = getResources().getStringArray(R.array.state_array);

                Toast.makeText(parent.getContext(), array[pos], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String surname = surnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String location = locationEditText.getText().toString();
                State selectedState = null;
                switch ((String) stateSpinner.getSelectedItem()){
                    case "Pendiente":
                        selectedState = State.PENDING;
                        break;
                    case "Confirmado":
                        selectedState = State.CONFIRMED;
                        break;
                    case "Terminado":
                        selectedState = State.DONE;
                        break;
                    default:
                        break;
                }

                boolean inserted = appointmentRepository.updateAppointment(String.valueOf(appointment.getId()), name, surname, email, location, date, selectedState);

                if (inserted) {
                    Toast.makeText(UpdateActivity.this, R.string.update_message_sucess, Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(UpdateActivity.this, R.string.update_message_fail, Toast.LENGTH_SHORT).show();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
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
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}