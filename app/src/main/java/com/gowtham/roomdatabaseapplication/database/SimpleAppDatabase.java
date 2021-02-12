package com.gowtham.roomdatabaseapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gowtham.roomdatabaseapplication.model.Contact;

@Database(entities = {Contact.class}, version  = 1)
public abstract class SimpleAppDatabase extends RoomDatabase {

    private static SimpleAppDatabase INSTANCE;
    private static final String DATABASE_NAME = "contact_database";

    public abstract PersonDao userDao();

    public static SimpleAppDatabase getDbInstance(Context context) {

        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SimpleAppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }
}
