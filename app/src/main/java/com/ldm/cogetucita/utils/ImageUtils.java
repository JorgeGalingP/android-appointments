package com.ldm.cogetucita.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

import com.ldm.cogetucita.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class ImageUtils {

    private final Context context;

    public ImageUtils(Context context) {
        this.context = context;
    }

    public URL stringToURL(String urlString){
        try {
            return new URL(urlString);
        } catch(MalformedURLException e){
            e.printStackTrace();

            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    public Uri saveImageToInternalStorage(Bitmap bitmap) {
        // generate unique name
        byte[] array = new byte[6];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        // initialize ContextWrapper
        ContextWrapper wrapper = new ContextWrapper(this.context);

        // initializing a new file
        File file = wrapper.getDir("Images", MODE_PRIVATE);

        // create a file to save the image
        file = new File(file, generatedString + ".jpg");

        try {
            // initialize a new OutputStream
            OutputStream stream = null;

            // if the output file exists, it can be replaced or appended to it
            stream = new FileOutputStream(file);

            // compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);

            // flushes the stream
            stream.flush();

            // closes the stream
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Parse the gallery image url to uri and return
        return Uri.parse(file.getAbsolutePath());
    }

    public List<String> getImagesFromAssetFolder(final Class<?> finalActivity){
        List<String> imagesFinalList = new ArrayList<>();

        try {
            String regex = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)";
            Pattern pattern = Pattern.compile(regex);

            // get images from assets folder
            String[] imagesAsset = this.context.getAssets().list("");

            // get images from "Images" folder
            ContextWrapper wrapper = new ContextWrapper(this.context);
            String[] imagesFolder = wrapper.getDir("Images", MODE_PRIVATE).list();

            // final image's list
            List<String> imagesList = new ArrayList<>();
            if (imagesAsset != null && imagesFolder != null) {
                imagesList.addAll(Arrays.asList(imagesAsset));
                imagesList.addAll(Arrays.asList(imagesFolder));
            }

            for (String image : imagesList) {
                Matcher matcher = pattern.matcher(image);

                if (!image.equals("") && matcher.matches()) {
                    imagesFinalList.add(image);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(this.context, R.string.update_images_message_fail, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getContext(), finalActivity);
                    getContext().startActivity(intent);
                }
            }, 1500);
        }

        return imagesFinalList;
    }

    public Context getContext() {
        return context;
    }
}
