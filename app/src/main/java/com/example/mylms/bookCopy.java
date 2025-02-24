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

public class bookCopy extends AppCompatActivity {

    EditText editBookID, editBranchID, editAccess;
    Button btnAdd, btnView, btnUpdate, btnDelete;
    DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_copy);

        dbHelper = new DatabaseHelper(this);
        editBookID = findViewById(R.id.editBookID);
        editBranchID = findViewById(R.id.editBranchID);
        editAccess = findViewById(R.id.editAccess);
        btnAdd = findViewById(R.id.btndd);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCopy();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCopy();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCopy();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCopy();
            }
        });
    }

    private void addCopy() {
        String bookID = editBookID.getText().toString();
        String branchID = editBranchID.getText().toString();
        String accessNum = editAccess.getText().toString();

        boolean inserted = dbHelper.insertBookCopy(bookID, branchID, accessNum);
        if (inserted) {
            Toast.makeText(this, "Copy added successfully", Toast.LENGTH_SHORT).show();
            editBookID.setText("");
            editBranchID.setText("");
            editAccess.setText("");
        } else {
            Toast.makeText(this, "Failed to add copy", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    private void viewCopy() {
        String accessID = editAccess.getText().toString();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_BOOK_COPY + " WHERE " + DatabaseHelper.BOOK_COPY_COL_3 + "=?", new String[]{accessID});

        if (cursor != null && cursor.moveToFirst()) {
            editBookID.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOK_COPY_COL_1)));
            editBranchID.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOK_COPY_COL_2)));
            cursor.close();
        } else {
            Toast.makeText(this, "No copy found with the provided Book ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCopy() {
        String bookID = editBookID.getText().toString();
        String branchID = editBranchID.getText().toString();
        String accessNum = editAccess.getText().toString();

        boolean updated = dbHelper.updateBookCopy(bookID, branchID, accessNum);
        if (updated) {
            Toast.makeText(this, "Copy updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update copy", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCopy() {
        String branchID = editBranchID.getText().toString();
        String accessNum = editAccess.getText().toString();
        boolean deleted = dbHelper.deleteBookCopy(accessNum,branchID);
        if (deleted) {
            Toast.makeText(this, "Copy deleted successfully", Toast.LENGTH_SHORT).show();
            editBookID.setText("");
            editBranchID.setText("");
            editAccess.setText("");
        } else {
            Toast.makeText(this, "Failed to delete copy", Toast.LENGTH_SHORT).show();
        }
    }
    public  void nextButton(View view) {
        startActivity(new Intent(bookCopy.this, lendingDetails.class));
    }

    public void HomeButton(View view) {
        startActivity(new Intent(bookCopy.this,home.class));
    }

    public void backButton(View view) {
        startActivity(new Intent(bookCopy.this, authorDetails.class));
    }
}
