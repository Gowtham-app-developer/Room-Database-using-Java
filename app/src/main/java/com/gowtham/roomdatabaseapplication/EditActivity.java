package com.gowtham.roomdatabaseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.gowtham.roomdatabaseapplication.constants.Constants;
import com.gowtham.roomdatabaseapplication.database.AppDatabase;
import com.gowtham.roomdatabaseapplication.database.AppExecutors;
import com.gowtham.roomdatabaseapplication.model.Contact;


public class EditActivity extends AppCompatActivity {
    private EditText name, email, pinCode, city, phoneNumber;
    private Button button;
    private int mPersonId;
    private Intent intent;
    private AppDatabase mDatabase;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();
        mDatabase = AppDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Person_Id)) {
            button.setText("Update");

            mPersonId = intent.getIntExtra(Constants.UPDATE_Person_Id, -1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Contact person = mDatabase.personDao().loadPersonById(mPersonId);
                    populateUI(person);
                }
            });


        }

    }

    private void populateUI(Contact person) {

        if (person == null) {
            return;
        }

        name.setText(person.getName());
        email.setText(person.getEmail());
        phoneNumber.setText(person.getNumber());
        pinCode.setText(person.getPinCode());
        city.setText(person.getCity());
    }

    private void initViews() {
        name = findViewById(R.id.edit_name);
        email = findViewById(R.id.edit_email);
        pinCode = findViewById(R.id.edit_pincode);
        city = findViewById(R.id.edit_city);
        phoneNumber = findViewById(R.id.edit_number);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {

        if (TextUtils.isEmpty( name.getText().toString()) ||
                TextUtils.isEmpty(email.getText().toString())) {
            Snackbar.make(name, R.string.empty, Snackbar.LENGTH_SHORT)
                    .show();
        } else {
            final Contact person = new Contact(
                    name.getText().toString(),
                    email.getText().toString(),
                    phoneNumber.getText().toString(),
                    pinCode.getText().toString(),
                    city.getText().toString());

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if (!intent.hasExtra(Constants.UPDATE_Person_Id)) {
                        mDatabase.personDao().insertPerson(person);
                    } else {
                        person.setId(mPersonId);
                        mDatabase.personDao().updatePerson(person);
                    }
                    finish();
                }
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
