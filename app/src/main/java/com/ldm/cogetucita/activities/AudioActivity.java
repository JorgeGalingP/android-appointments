package com.ldm.cogetucita.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.ldm.cogetucita.R;

import java.io.File;
import java.io.IOException;

public class AudioActivity extends AppCompatActivity {
    private MediaRecorder mediaRecorder;
    private String outputAudio;

    private Button buttonRecord;
    private Button buttonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        setTitle("Grabar cita");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.audio);

        buttonRecord = findViewById(R.id.btn_rec);
        buttonPlay = findViewById(R.id.btn_play);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                || ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){
            String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
            ActivityCompat.requestPermissions(this, permissions, 1000);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // go to the main activity
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void recordAudio(View view){
        if (mediaRecorder == null) {
            File dir = getBaseContext().getFilesDir();
            try {
                outputAudio = File.createTempFile("audio", ".3gp", dir).getAbsolutePath();
            } catch (IOException e) {
                Toast.makeText(this, "Error al crear el audio: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setOutputFile(outputAudio);

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            buttonRecord.setText("Grabando...");
            Toast.makeText(this, "Grabando...", Toast.LENGTH_LONG).show();
        } else if (mediaRecorder != null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;

            buttonRecord.setText("Grabar");

            Toast.makeText(this, "¡Grabación finalizada!", Toast.LENGTH_SHORT).show();
        }
    }

    public void playAudio(View view){
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            if (outputAudio != null){
                mediaPlayer.setDataSource(outputAudio);
                mediaPlayer.prepare();

                mediaPlayer.start();
                Toast.makeText(this, "Reproduciendo...", Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(this, "¡Primero tienes que grabar una cita!", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}