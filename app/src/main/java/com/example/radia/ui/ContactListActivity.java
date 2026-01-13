package com.example.radia.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.radia.model.Contact;
import com.example.radia.data.DataBase;
import com.example.radia.helpers.DatabaseHelper;
import com.example.radia.R;

public class ContactListActivity extends AppCompatActivity {
    ArrayAdapter<Contact> contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(v -> {
            Intent goback = new Intent(ContactListActivity.this, ContactActivity.class);
            startActivity(goback);
            finish();

        });


        ListView listView = findViewById(R.id.lvContact);
        // String[] items = {"item 1", "item 2", "item 3"};

        SQLiteDatabase db = DatabaseHelper.getInstance(this).getReadableDatabase();

        Cursor mycursor = db.rawQuery("SELECT nom, phone FROM contacts", null);

        if(mycursor != null) {
            try {
                DataBase.contactList.clear();
                while(mycursor.moveToNext()){
                    String nom = mycursor.getString(mycursor.getColumnIndexOrThrow("nom"));
                    String phone = mycursor.getString(mycursor.getColumnIndexOrThrow("phone"));
                    DataBase.contactList.add(new Contact(nom, phone));
                }
            } finally {
                mycursor.close();
            }

        }


        contactAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, DataBase.contactList);

        listView.setAdapter(contactAdapter);


        listView.setOnItemClickListener((parent, view,position, id) -> {

            Intent detail = new Intent(ContactListActivity.this, DetailActivity.class);
            detail.putExtra("position", position);
            startActivity(detail);
            Toast.makeText(this, String.valueOf(position) , Toast.LENGTH_LONG).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //to refresh the display
        contactAdapter.notifyDataSetChanged();
    }
}