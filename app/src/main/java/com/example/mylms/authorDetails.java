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

public class authorDetails extends AppCompatActivity {

    EditText editBookID, editName;
    Button btnAdd, btnView, btnDelete, btnUpdate;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_details);

        editBookID = findViewById(R.id.editBookID);
        editName = findViewById(R.id.editName);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        dbHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAuthor();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAuthor();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAuthor();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAuthor();
            }
        });
    }

    private void addAuthor() {
        String bookID = editBookID.getText().toString();
        String name = editName.getText().toString();
        boolean inserted = dbHelper.insertBookAuthor(bookID, name);
        if (inserted) {
            Toast.makeText(this, "Author added successfully", Toast.LENGTH_SHORT).show();
            editBookID.setText("");
            editName.setText("");

        } else {
            Toast.makeText(this, "Failed to add Author", Toast.LENGTH_SHORT).show();
        }

    }


    @SuppressLint("Range")
    private void viewAuthor() {
        String bookID = editBookID.getText().toString();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_BOOK_AUTHOR + " WHERE " + DatabaseHelper.BOOK_AUTHOR_COL_1 + "=?", new String[]{bookID});

        if (cursor != null && cursor.moveToFirst()) {
            editName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOK_AUTHOR_COL_2)));
            cursor.close();
        } else {
            Toast.makeText(this, "No author found with the provided ID", Toast.LENGTH_SHORT).show();
            editName.setText(""); // Clear the editName field if no author is found
        }
    }


    private void updateAuthor() {

        String bookID = editBookID.getText().toString();
       String name = editName.getText().toString();
       boolean updated= dbHelper.updateBookAuthor(bookID, name);

        if (updated) {
            Toast.makeText(this, "Author update successfully", Toast.LENGTH_SHORT).show();
            editBookID.setText("");
            editName.setText("");

        } else {
            Toast.makeText(this, "Failed to delete Author", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteAuthor() {

        String bookID = editBookID.getText().toString();
        boolean deleted = dbHelper.deleteBookAuthor(bookID);
        if (deleted) {
            Toast.makeText(this, "Author deleted successfully", Toast.LENGTH_SHORT).show();
            editBookID.setText("");
            editName.setText("");

        } else {
            Toast.makeText(this, "Failed to delete Author", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextButton(View view) {
        startActivity(new Intent(authorDetails.this, bookCopy.class));
    }

    public void HomeButton(View view) {
        startActivity(new Intent(authorDetails.this, home.class));
    }

    public void backButton(View view) {
        startActivity(new Intent(authorDetails.this, memberDetails.class));
    }
}
