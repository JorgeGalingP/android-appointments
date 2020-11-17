package com.ldm.cogetucita.activities;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ldm.cogetucita.R;
import com.ldm.cogetucita.models.Appointment;
import com.ldm.cogetucita.models.Product;
import com.ldm.cogetucita.repositories.AppointmentRepository;
import com.ldm.cogetucita.repositories.ProductRepository;

public class UpdateActivity extends AppCompatActivity {

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

        TextView textView = findViewById(R.id.textView);
        textView.setText(appointment.getName());
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