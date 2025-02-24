package com.example.mylms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_PROGRESS = 250;
    private static final int PROGRESS_DELAY = 50; // milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressBar progressBar = findViewById(R.id.progressBar2);

        // Set up progress tracking
        progressBar.setMax(MAX_PROGRESS);

        // Simulate progress
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int progress = 0;

            @Override
            public void run() {
                if (progress <= MAX_PROGRESS) {
                    progressBar.setProgress(progress);
                    progress++;
                    handler.postDelayed(this, PROGRESS_DELAY);
                } else {
                    // Progress is fully loaded, transition to home activity
                    startActivity(new Intent(MainActivity.this, home.class));
                    finish(); // Optionally finish current activity
                }
            }
        }, PROGRESS_DELAY);
    }
}