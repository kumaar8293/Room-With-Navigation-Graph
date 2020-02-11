package com.mukesh.roomnavigation.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/*
 * This is a Room DAO (Data Access Object)
 * Which contains all the methods related to SQLite Database
 * https://developer.android.com/training/data-storage/room/
 */
@Dao
public interface ContactDao {

    @Insert(onConflict = REPLACE)
    void insertData(Contact contact);

    @Update
    void updateData(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM contact_table ORDER BY priority ASC")
    LiveData<List<Contact>> getAllDataFromTable();

    @Query("DELETE FROM contact_table")
    void deleteAllData();

}
