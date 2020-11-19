package com.ldm.cogetucita.fragments;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ldm.cogetucita.R;

public class Help1Fragment extends Fragment {

    public Help1Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_help1, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.help_1);

        TextView textView = view.findViewById(R.id.textView);
        textView.setText("Esta es la pantalla principal de la aplicación. " +
                "En ella podrás navegar por las diferentes pestañas con todas las citas. " +
                "\n" +
                "Indicado por la flecha roja, podrás crear una nueva cita." +
                "\n" +
                "Indicado por la flecha verde, podrás acceder a las instrucciones de la aplicación." +
                "\n" +
                "Indicado por la flecha amarilla, podrás actualizar y eliminar.");

        return view;
    }
}