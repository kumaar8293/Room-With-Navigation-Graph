package com.mukesh.roomnavigation.view.dashboard;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mukesh.roomnavigation.R;
import com.mukesh.roomnavigation.database.Contact;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class DashboardFragment extends Fragment implements
        DashboardAdapterWithAnimation.CustomOnItemClickListener,
        BottomSheetDialogFrg.BottomSheetListener {


    private DashboardAdapterWithAnimation adapter;
    private DashboardViewModel viewModel;
    private LottieAnimationView noContacts;

    private BottomSheetDialogFrg openBottomSheet;

    private Activity context;
    private View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        context = getActivity();
        initUI();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        viewModel.getAllNoteList(context).observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
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


        getDataFromCallBack();
    }


    private void getDataFromCallBack() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            Contact contact = (Contact) bundle.getSerializable("contactData");

            if (contact != null) {

                viewModel.insertContact(contact);
                /*Log.d(TAG, "getDataFromCallBack: " + contact.getId());
                if (contact.getId() == -1) {
                    viewModel.insertContact(contact);
                } else {
                    viewModel.updateContact(contact);
                }*/
            }
        }
    }

    private void initUI() {
        BottomAppBar appBar = rootView.findViewById(R.id.bottomAppBar);
        appBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle actions based on the menu item
                if (item.getItemId() == R.id.menuSettings) {
                    Toasty.info(context, "Do your work", Toast.LENGTH_SHORT, true).show();
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
                Toasty.info(context, "Do your work", Toast.LENGTH_SHORT, true).show();

            }
        });
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        adapter = new DashboardAdapterWithAnimation(context, this);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteContact(adapter.getContactAtPosition(viewHolder.getAdapterPosition()));

                Toasty.success(context, "Item deleted", Toast.LENGTH_SHORT, true).show();

            }
        }).attachToRecyclerView(recyclerView);

        rootView.findViewById(R.id.addContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(rootView).navigate(R.id.addEditAction);
            }
        });
        noContacts = rootView.findViewById(R.id.no_contact);
        openBottomSheet = new BottomSheetDialogFrg(this);
    }

    private int clickedPosition = 0;

    @Override
    public void onItemClickedPosition(int position) {

        clickedPosition = position;
        openBottomSheet.show(getChildFragmentManager(), "example");
    }

    @Override
    public void editClicked() {
        Contact contact = adapter.getContactAtPosition(clickedPosition);
        openAddOrEditFragment(contact);
    }

    private void openAddOrEditFragment(Contact contact) {
        //    Navigation.findNavController(rootView).navigate(R.id.addEditAction);

        DashboardFragmentDirections.AddEditAction action
                = DashboardFragmentDirections.addEditAction();
        action.setContactDetails(contact);
        Navigation.findNavController(rootView).navigate(action);

    }

    @Override
    public void deleteClicked() {
        viewModel.deleteContact(adapter.getContactAtPosition(clickedPosition));
    }


    private void showDeleteAllDialog() {

        new MaterialAlertDialogBuilder(context)
                .setTitle("Delete All Contact")
                .setMessage("Are you sure? You want to delete all the saved Contact.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.deleteAllContact();
                        Toasty.success(context, "All notes deleted", Toast.LENGTH_SHORT, true).show();

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
}
