package com.ldm.cogetucita.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ldm.cogetucita.R;
import com.ldm.cogetucita.fragments.DatePickerFragment;
import com.ldm.cogetucita.models.Appointment;
import com.ldm.cogetucita.models.State;
import com.ldm.cogetucita.repositories.AppointmentRepository;

public class UpdateAppointmentActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText emailEditText;
    private EditText dateEditText;
    private EditText locationEditText;
    private Spinner stateSpinner;

    private boolean deleted = false;

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

        setTitle("Actualizar la cita C-" + id);

        // init Repositories
        final AppointmentRepository appointmentRepository = new AppointmentRepository(this);

        // get Appointment
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
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        locationEditText = findViewById(R.id.editTextLocation);
        locationEditText.setText(appointment.getLocation());

        // set Spinner
        State appointmentState = appointment.getState();
        int selection = appointmentState.ordinal();

        stateSpinner = findViewById(R.id.stateSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.state_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(spinnerAdapter);
        stateSpinner.setSelection(selection);

        Button updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String surname = surnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String location = locationEditText.getText().toString();
                State selectedState = State.values()[stateSpinner.getSelectedItemPosition()];

                boolean updated = appointmentRepository.updateAppointment(String.valueOf(appointment.getId()), name, surname, email, date, location, selectedState);

                if (updated) {
                    Toast.makeText(UpdateAppointmentActivity.this, R.string.update_message_sucess, Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(UpdateAppointmentActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }, 1500);
                } else {
                    Toast.makeText(UpdateAppointmentActivity.this, R.string.update_message_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateAppointmentActivity.this);
                builder
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleted = appointmentRepository.deleteAppointment(String.valueOf(appointment.getId()));
                            }
                        })
                        .setNegativeButton("No, mantener", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                deleted = false;
                            }
                        })
                        .setMessage("¿Estás seguro de querer borrar esta cita?")
                        .setTitle("Eliminar");
                AlertDialog dialog = builder.create();
                dialog.show();

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (deleted) {
                            Toast.makeText(UpdateAppointmentActivity.this, R.string.delete_message_sucess, Toast.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(UpdateAppointmentActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }, 1500);
                        } else {
                            Toast.makeText(UpdateAppointmentActivity.this, R.string.delete_message_fail, Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(this, MainActivity.class);
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