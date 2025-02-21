package com.ldm.cogetucita.fragments;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ldm.cogetucita.R;

public class Help2Fragment extends Fragment {

    public Help2Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_help2, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.help_2);

        TextView textView = view.findViewById(R.id.textView);
        textView.setText("Al pulsar cualquier cita (flecha amarilla) navegas a la pantalla de actualización. " +
                "\n" +
                "En ella podrás actualizar todos los datos de dicha cita: Nombre, Apellidos, Email, Fecha, Lugar y Estado." +
                "\n" +
                "Tambien puedes eliminar la cita." +
                "\n" +
                "¡Prueba tú mismo!");

        return view;
    }
}