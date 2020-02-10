package com.mukesh.roomassignment.view.addEdit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.mukesh.roomassignment.R;
import com.mukesh.roomassignment.database.Contact;
import com.mukesh.roomassignment.utils.CommonMethod;

import es.dmoral.toasty.Toasty;

public class AddOrEditContactActivity extends AppCompatActivity {
    private TextInputEditText editName, editEmailId;
    private NumberPicker priorityNumber;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        //in manifest need to declare android:parentActivityName=".view.MainActivity" to get X button visible
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        initView();
    }

    private void initView() {

        editEmailId = findViewById(R.id.editEmail);
        editName = findViewById(R.id.editName);
        priorityNumber = findViewById(R.id.priorityNumber);
        priorityNumber.setMinValue(1);
        priorityNumber.setMaxValue(20);

        Intent intent = getIntent();
        contact = (Contact) intent.getSerializableExtra("bundle");
        if (contact != null) {
            setTitle("Edit Contact");
            editName.setText(contact.getName());
            editEmailId.setText(contact.getEmailId());
            priorityNumber.setValue(contact.getPriority());
        } else {
            setTitle("Add Contact");
            contact = new Contact();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu, menu);
        //Return false if you don't want to show the menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up/Home button
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        } else if (item.getItemId() == R.id.saveNote) {
            saveNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        if (editName.getText() == null || editEmailId.getText() == null)
            return;
        String title = editName.getText().toString().trim();
        String description = editEmailId.getText().toString().trim();
        int priorityNo = priorityNumber.getValue();

        if (title.trim().isEmpty() || description.isEmpty()) {
            Toasty.error(this, "Please enter Name and Email id", Toast.LENGTH_SHORT, true).show();

            return;
        }
        if (!CommonMethod.isValidEmail(description)){
            Toasty.error(this, "Please enter valid Email id", Toast.LENGTH_SHORT, true).show();

            return;
        }
        contact.setName(title);
        contact.setPriority(priorityNo);
        contact.setEmailId(description);

        Intent intent = new Intent();
        intent.putExtra("bundle", contact);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
