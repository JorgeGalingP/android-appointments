package com.ldm.cogetucita.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.ldm.cogetucita.R;
import com.ldm.cogetucita.models.Appointment;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder>{
    private List<Appointment> appointmentList;

    public AppointmentAdapter(List<Appointment> appointmentList){
        this.appointmentList = appointmentList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView idTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            // set views
            idTextView = itemView.findViewById(R.id.idTextView);

            // attach a click listener to the entire row view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Appointment appointment = appointmentList.get(position);

                Toast.makeText(view.getContext(), "Selected: " + appointment.getEmail(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // usually involves inflating a layout from XML and returning the holder
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View productView = layoutInflater.inflate(R.layout.item_appointment, parent, false);

        return new ViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // involves populating data into the item through holder
        Appointment appointment = appointmentList.get(position);

        // set TextViews
        TextView textViewId = holder.idTextView;
        textViewId.setText(String.valueOf(appointment.getId()));
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }
}
