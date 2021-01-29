package com.gowtham.roomdatabaseapplication.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.gowtham.roomdatabaseapplication.model.Contact;

import java.util.List;

@Dao
public interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPerson(Contact person);

    @Query("SELECT * FROM contact_table ORDER BY ID")
    List<Contact> loadAllPersons();

    @Update
    void updatePerson(Contact person);

    @Delete
    void delete(Contact person);

    @Query("SELECT * FROM contact_table WHERE id = :id")
    Contact loadPersonById(int id);

}
