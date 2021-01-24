package com.ldm.cogetucita.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ldm.cogetucita.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Random;

public class ImageActivity extends AppCompatActivity {

    private EditText editText;
    private ImageView imageView;
    private ProgressDialog progressDialog;

    private AsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        setTitle("Descargar imagen");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // set EditText
        editText = findViewById(R.id.editTextView);
        editText.setText(R.string.image_example);

        // set ImageView
        imageView = findViewById(R.id.imageView);

        // set Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Descargando");
        progressDialog.setMessage("Por favor, mantente a la espera de que la descarga de la imagen finalice...");

        Button uploadButton = findViewById(R.id.button);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlDownload = editText.getText().toString();

                task = new DownloadTask()
                        .execute(stringToURL(urlDownload));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // go to main activity
            Intent homeIntent = new Intent(this, AdminActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(homeIntent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DownloadTask extends AsyncTask<URL,Void, Bitmap> {

        protected void onPreExecute(){
            // Display the progress dialog on async task start
            progressDialog.show();
        }

        protected Bitmap doInBackground(URL...urls){
            URL url = urls[0];
            HttpURLConnection connection = null;
            Bitmap bitmap = null;

            try {
                // Initialize a new http url connection
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // Get the input stream from http url connection
                InputStream inputStream = connection.getInputStream();

                // Initialize a new BufferedInputStream from InputStream
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                // Convert BufferedInputStream to Bitmap object and return
                bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                return bitmap;

            } catch (IOException e) {
                e.printStackTrace();

                Toast.makeText(ImageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                // Disconnect the http url connection
                connection.disconnect();
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap result){
            // Hide the progress dialog
            progressDialog.dismiss();

            if (result != null) {
                // Save bitmap to internal storage
                Uri imageInternalUri = saveImageToInternalStorage(result);

                // Set the ImageView image from internal storage
                imageView.setImageURI(imageInternalUri);

                Toast.makeText(ImageActivity.this, R.string.upload_message_sucess, Toast.LENGTH_SHORT).show();
            } else {
                // Notify user that an error occurred while downloading image
                Toast.makeText(ImageActivity.this, R.string.upload_message_fail, Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected URL stringToURL(String urlString){
        try {
            return new URL(urlString);
        } catch(MalformedURLException e){
            e.printStackTrace();

            Toast.makeText(ImageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    protected Uri saveImageToInternalStorage(Bitmap bitmap) {
        // Generate unique name
        byte[] array = new byte[6];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        // Initialize ContextWrapper
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());

        // Initializing a new file
        // The bellow line return a directory in internal storage
        File file = wrapper.getDir("Images", MODE_PRIVATE);

        // Create a file to save the image
        file = new File(file, generatedString + ".jpg");

        try {
            // Initialize a new OutputStream
            OutputStream stream = null;

            // If the output file exists, it can be replaced or appended to it
            stream = new FileOutputStream(file);

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);

            // Flushes the stream
            stream.flush();

            // Closes the stream
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(ImageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Parse the gallery image url to uri and return
        return Uri.parse(file.getAbsolutePath());
    }
}