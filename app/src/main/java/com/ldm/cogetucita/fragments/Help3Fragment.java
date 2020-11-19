package com.ldm.cogetucita.fragments;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ldm.cogetucita.R;

public class Help3Fragment extends Fragment {

    public Help3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help3, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.help_3);

        TextView textView = view.findViewById(R.id.textView);
        textView.setText("Esta es la pantalla de selección de productos. " +
                "\n" +
                "Indicado por el círculo rojo, se muestran todos los posibles productos. " +
                "\n" +
                "Al pulsar sobre un producto, podrás crear una nueva cita.");

        return view;
    }
}