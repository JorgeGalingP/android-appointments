package com.ldm.cogetucita.fragments;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ldm.cogetucita.R;

public class Help4Fragment extends Fragment {

    public Help4Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_help4, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.help_4);

        TextView textView = view.findViewById(R.id.textView);
        textView.setText("Esta es la pantalla de creción de una nueva cita. " +
                "En ella podrás crear nuevas citas en estado pendiente. " +
                "\n" +
                "Indicado por la línea verde, aparece el producto seleccionado." +
                "\n" +
                "Indicado por el círculo rojo, se encuentran los campos." +
                "\n" +
                "Indicado por el cuadrado amarillo, realizarás el registro.");

        return view;
    }
}