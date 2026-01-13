package com.example.radia.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

public class ContactFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //ArrayList<String> contact_list = new ArrayList<>();

        EditText nom = findViewById(R.id.name);
        EditText phone = findViewById(R.id.phoneNumber);


        Button sub = findViewById(R.id.submit);
        sub.setOnClickListener(v -> {
            String name = nom.getText().toString().trim();
            String phonenum = phone.getText().toString().trim();
            // contact_list.add(name+" --> "+phonenum);
            Contact newContact = new Contact(name, phonenum);
            DataBase.contactList.add(newContact);

            SQLiteDatabase db = DatabaseHelper.getInstance(this).getWritableDatabase();
            db.execSQL("insert into contacts(nom, phone) values(?, ?)",new String[] {
                    name, phonenum
            });
            Toast.makeText(this,"Well add "+name,1).show();

            Intent intent3=new  Intent(ContactFormActivity.this,ContactActivity.class);
            startActivity(intent3);
        });
    }
}