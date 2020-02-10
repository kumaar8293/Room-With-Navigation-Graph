package com.mukesh.roomassignment.view.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mukesh.roomassignment.R;
import com.mukesh.roomassignment.database.Contact;
import com.mukesh.roomassignment.view.addEdit.AddOrEditContactActivity;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class DashboardActivity extends AppCompatActivity implements
        DashboardAdapterWithAnimation.CustomOnItemClickListener, BottomSheetDialogFrg.BottomSheetListener {
    public final int ADD_REQUEST_CODE = 1;
    public final int EDIT_REQUEST_CODE = 2;
    //    private DashboardAdapter adapter;
    private DashboardAdapterWithAnimation adapter;
    private DashboardViewModel viewModel;
    private LottieAnimationView noContacts;

    private BottomSheetDialogFrg openBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        viewModel.getAllNoteList(this).observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                //Custom Datapass
                // adapter.setData(contacts);
                //Adapter with animation
                adapter.submitList(contacts);
                if (contacts != null && contacts.size() == 0) {
                    noContacts.setVisibility(View.VISIBLE);
                } else {
                    noContacts.setVisibility(View.GONE);

                }
            }
        });
    }

    private void initUI() {
        BottomAppBar appBar = findViewById(R.id.bottomAppBar);
        appBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle actions based on the menu item
                if (item.getItemId() == R.id.menuSettings) {
                    Toasty.info(DashboardActivity.this, "Do your work", Toast.LENGTH_SHORT, true).show();
                } else if (item.getItemId() == R.id.menuDelete) {
                    showDeleteAllDialog();
                }
                return true;
            }
        });
        appBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the navigation click by showing a BottomDrawer etc.
                Toasty.info(DashboardActivity.this, "Do your work", Toast.LENGTH_SHORT, true).show();

            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new DashboardAdapterWithAnimation(this, this);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteContact(adapter.getContactAtPosition(viewHolder.getAdapterPosition()));

                Toasty.success(DashboardActivity.this, "Item deleted", Toast.LENGTH_SHORT, true).show();

            }
        }).attachToRecyclerView(recyclerView);

        findViewById(R.id.addContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,
                        AddOrEditContactActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });

        noContacts = findViewById(R.id.no_contact);
        openBottomSheet = new BottomSheetDialogFrg();
    }


    private int clickedPosition = 0;

    @Override
    public void onItemClickedPosition(int position) {

        clickedPosition = position;
        openBottomSheet.show(getSupportFragmentManager(), "example");

    }

    @Override
    public void editClicked() {

        openAddOrEditActivity();
    }

    @Override
    public void deleteClicked() {
        viewModel.deleteContact(adapter.getContactAtPosition(clickedPosition));
    }

    private void openAddOrEditActivity() {

        Contact contact = adapter.getContactAtPosition(clickedPosition);
        Intent intent = new Intent(this, AddOrEditContactActivity.class);
        intent.putExtra("bundle", contact);
        startActivityForResult(intent, EDIT_REQUEST_CODE);

    }

    private void showDeleteAllDialog() {

        new MaterialAlertDialogBuilder(this)
                .setTitle("Delete All Contacts")
                .setMessage("Are you sure? You want to delete all the saved Contacts.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.deleteAllContact();
                        Toasty.success(DashboardActivity.this, "All notes deleted", Toast.LENGTH_SHORT, true).show();

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Contact contact = (Contact) data.getSerializableExtra("bundle");
            viewModel.insertContact(contact);
            Toasty.success(this, "Contact saved", Toast.LENGTH_SHORT, true).show();

        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Contact contact = (Contact) data.getSerializableExtra("bundle");
            viewModel.updateContact(contact);
            Toasty.success(this, "Contact updated", Toast.LENGTH_SHORT, true).show();

        } else {
            Toasty.error(this, "Contact not saved", Toast.LENGTH_SHORT, true).show();
        }
    }
}
