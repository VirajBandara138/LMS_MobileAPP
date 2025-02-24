package com.example.mylms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class publisherDetails extends AppCompatActivity {

    EditText editPublisherName, editAddress, editPhone;
    Button btnPublisherAdd, btnPublisherView, btnPublisherUpdate, btnPublisherDelete;
    DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_details);

        dbHelper = new DatabaseHelper(this);
        editPublisherName = findViewById(R.id.editName);
        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editPhone);
        btnPublisherAdd = findViewById(R.id.btnAdd);
        btnPublisherView = findViewById(R.id.btnView);
        btnPublisherUpdate = findViewById(R.id.btnUpdate);
        btnPublisherDelete = findViewById(R.id.btnDelete);

        btnPublisherAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPublisher();
            }
        });

        btnPublisherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPublisher();
            }
        });

        btnPublisherUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePublisher();
            }
        });

        btnPublisherDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePublisher();
            }
        });
    }

    private void addPublisher() {
        String name = editPublisherName.getText().toString();
        String address = editAddress.getText().toString();
        String phone = editPhone.getText().toString();

        boolean inserted = dbHelper.insertPublisher(name, address, phone);
        if (inserted) {
            Toast.makeText(this, "Publisher added successfully", Toast.LENGTH_SHORT).show();
            editPublisherName.setText("");
            editAddress.setText("");
            editPhone.setText("");
        } else {
            Toast.makeText(this, "Failed to add publisher", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    private void viewPublisher() {
        String publisherName = editPublisherName.getText().toString();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_PUBLISHER + " WHERE " + DatabaseHelper.PUBLISHER_COL_1 + "=?", new String[]{publisherName});

        if (cursor != null && cursor.moveToFirst()) {
            editAddress.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.PUBLISHER_COL_2)));
            editPhone.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.PUBLISHER_COL_3)));
            cursor.close();
        } else {
            Toast.makeText(this, "No Publisher data found with the provided name", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePublisher() {
        String name = editPublisherName.getText().toString();
        String address = editAddress.getText().toString();
        String phone = editPhone.getText().toString();

        boolean updated = dbHelper.updatePublisher(name, address, phone);
        if (updated) {
            Toast.makeText(this, "Publisher updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update publisher", Toast.LENGTH_SHORT).show();
        }
    }

    private void deletePublisher() {
        String name = editPublisherName.getText().toString();
        boolean deleted = dbHelper.deletePublisher(name);
        if (deleted) {
            Toast.makeText(this, "Publisher deleted successfully", Toast.LENGTH_SHORT).show();
            editPublisherName.setText("");
            editAddress.setText("");
            editPhone.setText("");
        } else {
            Toast.makeText(this, "Failed to delete publisher", Toast.LENGTH_SHORT).show();
        }
    }

    public  void nextButton(View view) {
        startActivity(new Intent(publisherDetails.this, branchDetails.class));
    }

    public void HomeButton(View view) {
        startActivity(new Intent(publisherDetails.this,home.class));
    }

    public void backButton(View view) {
        startActivity(new Intent(publisherDetails.this, bookDetails.class));

    }
}
