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
        public TextView nameTextView;
        public TextView surnameTextView;
        public TextView emailTextView;
        public TextView dateTextView;
        public TextView productTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            // set views
            idTextView = itemView.findViewById(R.id.idTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            surnameTextView = itemView.findViewById(R.id.surnameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            productTextView = itemView.findViewById(R.id.productTextView);

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

        View appointmentView = layoutInflater.inflate(R.layout.item_appointment, parent, false);

        return new ViewHolder(appointmentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // involves populating data into the item through holder
        Appointment appointment = appointmentList.get(position);

        // set TextViews
        TextView textViewId = holder.idTextView;
        textViewId.setText("#" + appointment.getId());

        TextView textViewName = holder.nameTextView;
        textViewName.setText(appointment.getName());

        TextView textViewSurname = holder.surnameTextView;
        textViewSurname.setText(appointment.getSurname());

        TextView textViewEmail = holder.emailTextView;
        textViewEmail.setText(appointment.getEmail());

        TextView textViewDate = holder.dateTextView;
        textViewDate.setText(appointment.getDate());

        TextView textViewProduct = holder.productTextView;
        textViewProduct.setText(appointment.getProduct().toString());

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }
}
