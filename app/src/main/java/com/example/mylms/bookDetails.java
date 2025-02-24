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

public class bookDetails extends AppCompatActivity {

    EditText editBookID, editTitle, editPublisher;
    Button btnBookAdd, btnBookView, btnBookUpdate, btnBookDelete;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        dbHelper = new DatabaseHelper(this);
        editBookID = findViewById(R.id.editBookID);
        editTitle = findViewById(R.id.editTitle);
        editPublisher = findViewById(R.id.editPublisher);
        btnBookAdd = findViewById(R.id.btnBookAdd);
        btnBookView = findViewById(R.id.btnBookView);
        btnBookUpdate = findViewById(R.id.btnBookUpdate);
        btnBookDelete = findViewById(R.id.btnBookDelete);

        btnBookAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });

        btnBookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBook();
            }
        });

        btnBookUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });

        btnBookDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });
    }

    private void addBook() {
        String bookID = editBookID.getText().toString();
        String title = editTitle.getText().toString();
        String publisher = editPublisher.getText().toString();

        boolean inserted = dbHelper.insertBook(bookID, title, publisher);
        if (inserted) {
            Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show();
            editBookID.setText("");
            editTitle.setText("");
            editPublisher.setText("");
        } else {
            Toast.makeText(this, "Failed to add book", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    private void viewBook() {
        String bookID = editBookID.getText().toString();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_BOOK + " WHERE " + DatabaseHelper.BOOK_COL_1 + "=?", new String[]{bookID});

        if (cursor != null && cursor.moveToFirst()) {
            editTitle.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOK_COL_2)));
            editPublisher.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOK_COL_3)));
            cursor.close();
        } else {
            Toast.makeText(this, "No book found with the provided ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateBook() {
        String bookID = editBookID.getText().toString();
        String title = editTitle.getText().toString();
        String publisher = editPublisher.getText().toString();

        boolean updated = dbHelper.updateBook(bookID, title, publisher);
        if (updated) {
            Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update book", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteBook() {
        String bookID = editBookID.getText().toString();
        boolean deleted = dbHelper.deleteBook(bookID);
        if (deleted) {
            Toast.makeText(this, "Book deleted successfully", Toast.LENGTH_SHORT).show();
            editBookID.setText("");
            editTitle.setText("");
            editPublisher.setText("");
        } else {
            Toast.makeText(this, "Failed to delete book", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextButton(View view) {
        startActivity(new Intent(bookDetails.this, publisherDetails.class));
    }

    public void HomeButton(View view) {
        startActivity(new Intent(bookDetails.this,home.class));
    }

    public void backButton(View view) {
        startActivity(new Intent(bookDetails.this, MainActivity.class));

    }
}
