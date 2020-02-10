package com.mukesh.roomassignment.view.dashboard;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mukesh.roomassignment.database.Contact;
import com.mukesh.roomassignment.repositories.ContactRepositories;

import java.util.List;


public class DashboardViewModel extends ViewModel {

    private ContactRepositories contactRepositories;
    private LiveData<List<Contact>> allContactList;

    private void initializeComponents(Context context) {
        if (allContactList == null) {
            contactRepositories = new ContactRepositories(context);
            allContactList = contactRepositories.getAllNotes();
        }
    }

    void insertContact(Contact contact) {
        contactRepositories.insertData(contact);
    }

    void updateContact(Contact contact) {
        contactRepositories.updateData(contact);
    }

    void deleteContact(Contact contact) {
        contactRepositories.deleteData(contact);
    }

    void deleteAllContact() {
        contactRepositories.deleteAllData();
    }

    LiveData<List<Contact>> getAllNoteList(Context context) {
        if (allContactList == null) {
            initializeComponents(context);
        }
        return allContactList;
    }

}
