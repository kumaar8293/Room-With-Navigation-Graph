package com.mukesh.roomassignment.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
/*
 *Room doesn't allow database operation in main thread
 */

@Database(entities = Contact.class, version = 1, exportSchema = false)
public abstract class ContactDatabase extends RoomDatabase {
    private static ContactDatabase instance;

    //ROOM auto generates all the necessary code we need here
    public abstract ContactDao noteDao();

    public static synchronized ContactDatabase getNoteDatabase(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class, "contact_database")
                    .addCallback(databaseCallBack)
                    .fallbackToDestructiveMigration().build();
        return instance;
    }

    private static RoomDatabase.Callback databaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //inserting some predefinedData
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        ContactDao contactDao;

        PopulateDBAsyncTask(ContactDatabase db) {
            contactDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            contactDao.insertData(new Contact("Mukesh", "mukesh@gmail.com", 1));
            contactDao.insertData(new Contact("Sunil", "sunil@gmail.com", 2));
            contactDao.insertData(new Contact("Dnyaneshwar", "dpatil@gmail.com", 3));
            contactDao.insertData(new Contact("Abhijit", "abhijit@gmail.com", 4));
            contactDao.insertData(new Contact("Dharmendra", "dharmendra@gmail.com", 5));
            contactDao.insertData(new Contact("Sudipto", "sudipto@gmail.com", 6));
            contactDao.insertData(new Contact("Vikash", "vikash@gmail.com", 7));
            contactDao.insertData(new Contact("Biswatma", "biswatma@gmail.com", 8));
            contactDao.insertData(new Contact("Anuradha", "anuradha@gmail.com", 9));
            contactDao.insertData(new Contact("Amresh", "amresh@gmail.com", 10));
            return null;
        }
    }
}
