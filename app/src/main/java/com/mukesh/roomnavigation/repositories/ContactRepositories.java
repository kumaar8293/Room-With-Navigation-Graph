package com.mukesh.roomnavigation.repositories;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.mukesh.roomnavigation.database.Contact;
import com.mukesh.roomnavigation.database.ContactDao;
import com.mukesh.roomnavigation.database.ContactDatabase;

import java.util.List;

/*
 * Room doesn't allow database operation in main thread
 * This is the repository class where we are calling to DOA class
 */

public class ContactRepositories {
    private ContactDao contactDao;
    private LiveData<List<Contact>> allNotes;

    public ContactRepositories(Context application) {
        ContactDatabase contactDatabase = ContactDatabase.getNoteDatabase(application);
        //We can call the below method because room generate all the necessary codes
        contactDao = contactDatabase.noteDao();
        allNotes = contactDao.getAllDataFromTable();
    }

    public void insertData(Contact contact) {
        new DatabaseTask(contactDao, 1).execute(contact);
    }

    public void updateData(Contact contact) {
        new DatabaseTask(contactDao, 2).execute(contact);
    }

    public void deleteData(Contact contact) {
        new DatabaseTask(contactDao, 3).execute(contact);
    }

    public void deleteAllData() {
        new DatabaseTask(contactDao, 4).execute((Contact) null);
    }

    public LiveData<List<Contact>> getAllNotes() {
        return allNotes;
    }

    private static class DatabaseTask extends AsyncTask<Contact, Void, Void> {
        ContactDao contactDao;
        int type;

        /**
         * If type 1 : Insert
         * type 2= update
         * type 3= delete
         * type 4= delete all
         **/
        DatabaseTask(ContactDao contactDao, int type) {
            this.contactDao = contactDao;
            this.type = type;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {

            switch (type) {
                case 1:
                    contactDao.insertData(contacts[0]);
                    break;
                case 2:
                    contactDao.updateData(contacts[0]);
                    break;
                case 3:
                    contactDao.delete(contacts[0]);
                    break;
                case 4:
                    contactDao.deleteAllData();
                    break;
            }

            return null;
        }
    }
}
