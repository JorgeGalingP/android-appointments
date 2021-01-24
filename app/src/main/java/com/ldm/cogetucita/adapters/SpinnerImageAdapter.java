package com.ldm.cogetucita.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldm.cogetucita.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SpinnerImageAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> objects;

    public SpinnerImageAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        String imageName = objects.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_image_spinner_product, parent, false);

        // set imageView
        ImageView imageView = (ImageView) row.findViewById(R.id.imageView);

        // set AssetManager
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(imageName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get Bitmap from image's name
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }

        return row;
    }
}
