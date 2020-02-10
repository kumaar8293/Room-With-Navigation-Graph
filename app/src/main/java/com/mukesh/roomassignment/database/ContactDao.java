package com.mukesh.roomassignment.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/*
 *This is a Room DAO (Data Access Object)
 *https://developer.android.com/training/data-storage/room/
 */
@Dao
public interface ContactDao {

    @Insert
    void insertData(Contact contact);

    @Update
    void updateData(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM CONTACT_TABLE ORDER BY priority ASC")
    LiveData<List<Contact>> getAllDataFromTable();

    @Query("DELETE FROM CONTACT_TABLE")
    void deleteAllData();

}
