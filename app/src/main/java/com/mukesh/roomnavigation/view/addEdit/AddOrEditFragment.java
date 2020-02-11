package com.mukesh.roomnavigation.view.addEdit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.mukesh.roomnavigation.R;
import com.mukesh.roomnavigation.database.Contact;
import com.mukesh.roomnavigation.utils.CommonMethod;

import es.dmoral.toasty.Toasty;

public class AddOrEditFragment extends Fragment {

    private TextInputEditText editName, editEmailId;
    private NumberPicker priorityNumber;
    private Contact contact;
    private Context context;
    private View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_edit, container, false);

        context = requireContext();
        initView();
        return rootView;
    }

    private void initView() {

        editEmailId = rootView.findViewById(R.id.editEmail);
        editName = rootView.findViewById(R.id.editName);
        priorityNumber = rootView.findViewById(R.id.priorityNumber);
        priorityNumber.setMinValue(1);
        priorityNumber.setMaxValue(20);

        rootView.findViewById(R.id.saveContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            AddOrEditFragmentArgs args = AddOrEditFragmentArgs.fromBundle(bundle);
            contact = args.getContactDetails();
            if (contact != null)
                setDataToViews();
            else contact = new Contact();
        } else contact = new Contact();
    }

    private void setDataToViews() {
        editName.setText(contact.getName());
        editEmailId.setText(contact.getEmailId());
        priorityNumber.setValue(contact.getPriority());
    }

    private void saveNote() {
        if (editName.getText() == null || editEmailId.getText() == null)
            return;
        String title = editName.getText().toString().trim();
        String description = editEmailId.getText().toString().trim();
        int priorityNo = priorityNumber.getValue();

        if (title.trim().isEmpty() || description.isEmpty()) {
            Toasty.error(context, "Please enter Name and Email id", Toast.LENGTH_SHORT, true).show();

            return;
        }
        if (!CommonMethod.isValidEmail(description)) {
            Toasty.error(context, "Please enter valid Email id", Toast.LENGTH_SHORT, true).show();

            return;
        }
        contact.setName(title);
        contact.setPriority(priorityNo);
        contact.setEmailId(description);


        //Sending data back to Start (Dashboard) class

        Bundle bundleData = new Bundle();
        bundleData.putSerializable("contactData", contact);
        Navigation.findNavController(rootView).setGraph(R.navigation.nav_graph, bundleData);
    }
}
