package com.example.mylms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void bookDetails(View view) {
        startActivity(new Intent(home.this,bookDetails.class));
    }

    public void bookCopyDetails(View view) {
        startActivity(new Intent(home.this, bookCopy.class));
    }

    public void memberDetails(View view) {
        startActivity(new Intent(home.this,memberDetails.class));
    }

    public void publisherDetails(View view) {
        startActivity(new Intent(home.this,publisherDetails.class));
    }

    public void authorDetails(View view) {
        startActivity(new Intent(home.this,authorDetails.class));
    }


    public void lendingDetails(View view) {
        startActivity(new Intent(home.this, lendingDetails.class));
    }

    public void branchDetails(View view) {
        startActivity(new Intent(home.this,branchDetails.class));
    }
}