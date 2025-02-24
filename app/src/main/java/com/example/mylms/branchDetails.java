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

public class branchDetails extends AppCompatActivity {

    EditText editBranchID, editName, editAddress;
    Button btnBranchAdd, btnBranchView, btnBranchUpdate, btnBranchDelete;
    DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_details);

        dbHelper = new DatabaseHelper(this);
        editBranchID = findViewById(R.id.editBranchID);
        editName = findViewById(R.id.editName);
        editAddress = findViewById(R.id.editAddress);
        btnBranchAdd = findViewById(R.id.btnBranchAdd);
        btnBranchView = findViewById(R.id.btnBranchView);
        btnBranchUpdate = findViewById(R.id.btnBranchUpdate);
        btnBranchDelete = findViewById(R.id.btnBranchDelete);

        btnBranchAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBranch();
            }
        });

        btnBranchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBranch();
            }
        });

        btnBranchUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBranch();
            }
        });

        btnBranchDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBranch();
            }
        });
    }

    private void addBranch() {
        String branchID = editBranchID.getText().toString();
        String name = editName.getText().toString();
        String address = editAddress.getText().toString();

        boolean inserted = dbHelper.insertBranch(branchID, name, address);
        if (inserted) {
            Toast.makeText(this, "Branch added successfully", Toast.LENGTH_SHORT).show();
            editBranchID.setText("");
            editName.setText("");
            editAddress.setText("");
        } else {
            Toast.makeText(this, "Failed to add branch", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    private void viewBranch() {
        String branchID = editBranchID.getText().toString();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_BRANCH + " WHERE " + DatabaseHelper.BRANCH_COL_1 + "=?", new String[]{branchID});

        if (cursor != null && cursor.moveToFirst()) {
            editName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BRANCH_COL_2)));
            editAddress.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BRANCH_COL_3)));
            cursor.close();
        } else {
            Toast.makeText(this, "No branch found with the provided ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateBranch() {
        String branchID = editBranchID.getText().toString();
        String name = editName.getText().toString();
        String address = editAddress.getText().toString();

        boolean updated = dbHelper.updateBranch(branchID, name, address);
        if (updated) {
            Toast.makeText(this, "Branch updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update branch", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteBranch() {
        String branchID = editBranchID.getText().toString();
        boolean deleted = dbHelper.deleteBranch(branchID);
        if (deleted) {
            Toast.makeText(this, "Branch deleted successfully", Toast.LENGTH_SHORT).show();
            editBranchID.setText("");
            editName.setText("");
            editAddress.setText("");
        } else {
            Toast.makeText(this, "Failed to delete branch", Toast.LENGTH_SHORT).show();
        }
    }
    public  void nextButton(View view) {
        startActivity(new Intent(branchDetails.this, memberDetails.class));
    }

    public void HomeButton(View view) {
        startActivity(new Intent(branchDetails.this,home.class));
    }

    public void backButton(View view) {
        startActivity(new Intent(branchDetails.this,publisherDetails.class));
    }
}
