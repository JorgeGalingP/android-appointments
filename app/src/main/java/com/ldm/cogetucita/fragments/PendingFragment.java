package com.ldm.cogetucita.fragments;

import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ldm.cogetucita.R;
import com.ldm.cogetucita.adapters.AppointmentAdapter;
import com.ldm.cogetucita.models.Appointment;
import com.ldm.cogetucita.models.State;
import com.ldm.cogetucita.repositories.AppointmentRepository;

import java.util.List;
import java.util.Objects;

public class PendingFragment extends Fragment {
    private ProgressBar progressBar;

    private List<Appointment> pendingList;

    public PendingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending, container, false);
        Context context = view.getContext();

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        // set correct AppointmentList
        AppointmentRepository appointmentRepository = new AppointmentRepository(getActivity());
        setPendingList(appointmentRepository.findAllAppointmentsByState(State.PENDING));

        progressBar.setVisibility(View.GONE);

        // set RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // set LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        // set Adapter
        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getPendingList());
        recyclerView.setAdapter(appointmentAdapter);

        return view;
    }

    public List<Appointment> getPendingList() {
        return pendingList;
    }

    public void setPendingList(List<Appointment> pendingList) {
        this.pendingList = pendingList;
    }
}