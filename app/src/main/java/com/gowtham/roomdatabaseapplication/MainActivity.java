package com.gowtham.roomdatabaseapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gowtham.roomdatabaseapplication.adapters.PersonAdapter;
import com.gowtham.roomdatabaseapplication.database.AppDatabase;
import com.gowtham.roomdatabaseapplication.database.AppExecutors;
import com.gowtham.roomdatabaseapplication.database.SimpleAppDatabase;
import com.gowtham.roomdatabaseapplication.model.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private RecyclerView mRecyclerView;
    private PersonAdapter mAdapter;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.addFAB);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditActivity.class));
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PersonAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        loadUserList();

        mDb = AppDatabase.getInstance(getApplicationContext());

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Contact> tasks = mAdapter.getTasks();
                        mDb.personDao().delete(tasks.get(position));
                        retrieveTasks();
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }

    private void retrieveTasks() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Contact> persons = mDb.personDao().loadAllPersons();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setTasks(persons);
                    }
                });
            }
        });
    }

    private void loadUserList() {

        SimpleAppDatabase db = SimpleAppDatabase.getDbInstance(this.getApplicationContext());
        List<Contact> userList = db.userDao().loadAllPersons();
        if(userList != null && userList.size() > 0) {
            Toast.makeText(this, userList.get(0).getName(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveNewUser() {
        SimpleAppDatabase db  = SimpleAppDatabase.getDbInstance(this.getApplicationContext());

        Contact user = new Contact();
        user.setName("Gauthy");
        user.setCity("Coimbatore");
        db.userDao().insertPerson(user);

        finish();

    }
}
