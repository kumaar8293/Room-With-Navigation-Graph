package com.mukesh.roomassignment.database;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/*This is a room entity
 *Room Annotation (Entity) at compile time it will create all the necessary code  to create an SQLite table in this object
 *https://developer.android.com/training/data-storage/room/defining-data#java
 */
@Entity(tableName = "contact_table")
public class Contact implements Serializable {
    //Id will be auto-generated
    @PrimaryKey(autoGenerate = true)
    private int id;
    // @ColumnInfo(name = "name")  we can change column name like this
    private String name, emailId;
    private int priority;

    public Contact(String name, String emailId, int priority) {
        this.name = name;
        this.emailId = emailId;
        this.priority = priority;
    }

    @Ignore
    public Contact() {
    }

    //Because we are not passing id with constructor
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }

    public int getPriority() {
        return priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


}
