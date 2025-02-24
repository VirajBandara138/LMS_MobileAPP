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

public class lendingDetails extends AppCompatActivity {

    EditText editAccess, editBranchID, editCard, editDO, editDD, editDR;
    Button btnAdd, btnView, btnUpdate, btnDelete;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lending_details);

        dbHelper = new DatabaseHelper(this);
        editAccess = findViewById(R.id.editAccess);
        editBranchID = findViewById(R.id.editBranchID);
        editCard = findViewById(R.id.editCard);
        editDO = findViewById(R.id.editDO);
        editDD = findViewById(R.id.editDD);
        editDR = findViewById(R.id.editDR);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLanding();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLanding();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLanding();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLanding();
            }
        });}

        private void addLanding() {
            String access = editAccess.getText().toString();
            String branchID = editBranchID.getText().toString();
            String card = editCard.getText().toString();
            String doDate = editDO.getText().toString();
            String ddDate = editDD.getText().toString();
            String drDate = editDR.getText().toString();

            boolean inserted = dbHelper.insertBookLoan(access, branchID, card, doDate, ddDate, drDate);
            if (inserted) {
                Toast.makeText(this, "Branch added successfully", Toast.LENGTH_SHORT).show();
                // Clear EditText fields after successful insertion
                clearEditTextFields();
            } else {
                Toast.makeText(this, "Failed to add branch", Toast.LENGTH_SHORT).show();
            }
        }

        @SuppressLint("Range")
        private void viewLanding() {
            String access = editAccess.getText().toString();
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_BOOK_LOAN + " WHERE " + DatabaseHelper.BOOK_LOAN_COL_1+ "=?", new String[]{access});

            if (cursor != null && cursor.moveToFirst()) {
                editBranchID.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOK_LOAN_COL_2)));
                editCard.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOK_LOAN_COL_3)));
                editDO.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOK_LOAN_COL_4)));
                editDD.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOK_LOAN_COL_5)));
                editDR.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BOOK_LOAN_COL_6)));
                cursor.close();
            } else {
                Toast.makeText(this, "No branch data found with the provided access number", Toast.LENGTH_SHORT).show();
            }
        }

        private void updateLanding() {
            String access = editAccess.getText().toString();
            String branchID = editBranchID.getText().toString();
            String card = editCard.getText().toString();
            String doDate = editDO.getText().toString();
            String ddDate = editDD.getText().toString();
            String drDate = editDR.getText().toString();

            boolean updated = dbHelper.updateBookLoan(access, branchID, card, doDate, ddDate, drDate);
            if (updated) {
                Toast.makeText(this, "Branch updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to update branch", Toast.LENGTH_SHORT).show();
            }
        }

        private void deleteLanding() {
            String access = editAccess.getText().toString();
            boolean deleted = dbHelper.deleteBookLoan(access);
            if (deleted) {
                Toast.makeText(this, "Branch deleted successfully", Toast.LENGTH_SHORT).show();
                // Clear EditText fields after successful deletion
                clearEditTextFields();
            } else {
                Toast.makeText(this, "Failed to delete branch", Toast.LENGTH_SHORT).show();
            }
        }

// Method to clear EditText fields
        private void clearEditTextFields() {
            editAccess.setText("");
            editBranchID.setText("");
            editCard.setText("");
            editDO.setText("");
            editDD.setText("");
            editDR.setText("");
        }



    public void HomeButton(View view) {
        startActivity(new Intent(lendingDetails.this, home.class));
    }

    public void backButton(View view) {
        startActivity(new Intent(lendingDetails.this, bookCopy.class));
    }
}
