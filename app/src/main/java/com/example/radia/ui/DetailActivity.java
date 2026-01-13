package com.example.radia.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.radia.model.Contact;
import com.example.radia.data.DataBase;
import com.example.radia.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent detail = getIntent();
        int position = detail.getIntExtra("position", -1);

        Contact contact = DataBase.contactList.get(position);

        TextView name = findViewById(R.id.tvname);
        TextView phone = findViewById(R.id.tvphone);

        name.setText(contact.getName());
        phone.setText(contact.getPhone());

        Button deleteItem = findViewById(R.id.btndelete);
        deleteItem.setOnClickListener( v -> {
            new AlertDialog.Builder(this).setTitle("Title")
                    .setMessage("Are you sure you want to delete this contact ?")
                    .setPositiveButton("Delete", (d,w) -> {
                        // delete the code
                        DataBase.contactList.remove(position);
                        finish();
                    })
                    .setNegativeButton("Cancel",null)
                    .show();

        });
    }
}