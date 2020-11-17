package com.ldm.cogetucita.activities;

import android.content.Intent;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
    private EditText stateEditText;
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

        // set Views
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

        stateEditText = findViewById(R.id.editTextState);
        stateEditText.setText(appointment.getState().name());

        updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String surname = surnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String location = locationEditText.getText().toString();
                String state = stateEditText.toString();

                State stateEnum = Enum.valueOf(State.class, state);

                boolean inserted = appointmentRepository.updateAppointment(String.valueOf(appointment.getId()), name, surname, email, location, date, stateEnum);

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