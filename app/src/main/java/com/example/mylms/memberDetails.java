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

public class memberDetails extends AppCompatActivity {

    EditText editCard, editMemberName, editMemberAddress, editMemberPhone, editMemberUDN;
    Button btnMemberAdd, btnMemberView, btnMemberUpdate, btnMemberDelete;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        dbHelper = new DatabaseHelper(this);
        editCard = findViewById(R.id.editCard);
        editMemberName = findViewById(R.id.editMemberName);
        editMemberAddress = findViewById(R.id.editMemberAddress);
        editMemberPhone = findViewById(R.id.editMemberPhone);
        editMemberUDN = findViewById(R.id.editMemberUDN);
        btnMemberAdd = findViewById(R.id.btnMemberAdd);
        btnMemberView = findViewById(R.id.btnMemberView);
        btnMemberUpdate = findViewById(R.id.btnMemberUpdate);
        btnMemberDelete = findViewById(R.id.btnMemberDelete);

        btnMemberAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
            }
        });

        btnMemberView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMember();
            }
        });

        btnMemberUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMember();
            }
        });

        btnMemberDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMember();
            }
        });
    }

    private void addMember() {
        String card = editCard.getText().toString();
        String name = editMemberName.getText().toString();
        String address = editMemberAddress.getText().toString();
        String phone = editMemberPhone.getText().toString();
        String udn = editMemberUDN.getText().toString();

        // Input validation
        if (card.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty() || udn.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            boolean inserted = dbHelper.insertMember(card, name, address, phone, udn);
            if (inserted) {
                Toast.makeText(this, "Member added successfully", Toast.LENGTH_SHORT).show();
                editCard.setText("");
                editMemberName.setText("");
                editMemberAddress.setText("");
                editMemberPhone.setText("");
                editMemberUDN.setText("");
            } else {
                Toast.makeText(this, "Failed to add member", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    private void viewMember() {
        String card = editCard.getText().toString();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_MEMBER + " WHERE " + DatabaseHelper.MEMBER_COL_1 + "=?", new String[]{card});

        if (cursor != null && cursor.moveToFirst()) {
            editMemberName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEMBER_COL_2)));
            editMemberAddress.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEMBER_COL_3)));
            editMemberPhone.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEMBER_COL_4)));
            editMemberUDN.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEMBER_COL_5)));
            cursor.close();
        } else {
            Toast.makeText(this, "No member found with the provided card number", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMember() {
        String card = editCard.getText().toString();
        String name = editMemberName.getText().toString();
        String address = editMemberAddress.getText().toString();
        String phone = editMemberPhone.getText().toString();
        String udn = editMemberUDN.getText().toString();

        boolean updated = dbHelper.updateMember(card, name, address, phone, udn);
        if (updated) {
            Toast.makeText(this, "Member updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update member", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteMember() {
        String card = editCard.getText().toString();
        boolean deleted = dbHelper.deleteMember(card);
        if (deleted) {
            Toast.makeText(this, "Member deleted successfully", Toast.LENGTH_SHORT).show();
            editCard.setText("");
            editMemberName.setText("");
            editMemberAddress.setText("");
            editMemberPhone.setText("");
            editMemberUDN.setText("");
        } else {
            Toast.makeText(this, "Failed to delete member", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextButton(View view) {
        startActivity(new Intent(memberDetails.this, authorDetails.class)); // Replace NextActivity with the appropriate activity name
    }

    public void HomeButton(View view) {
        startActivity(new Intent(memberDetails.this, home.class));
    }

    public void clear(View view) {
        editMemberUDN.setText("");
    }

    public void backButton(View view) {
        startActivity(new Intent(memberDetails.this,branchDetails.class));

    }
}
