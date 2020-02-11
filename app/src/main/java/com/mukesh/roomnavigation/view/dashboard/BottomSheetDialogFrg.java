package com.mukesh.roomnavigation.view.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mukesh.roomnavigation.R;

public class BottomSheetDialogFrg extends BottomSheetDialogFragment implements
        View.OnClickListener {

    private BottomSheetListener bottomSheetListener;

    BottomSheetDialogFrg(BottomSheetListener bottomSheetListener) {
        this.bottomSheetListener = bottomSheetListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        view.findViewById(R.id.edit_contact).setOnClickListener(this);
        view.findViewById(R.id.delete_contact).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.edit_contact:
                bottomSheetListener.editClicked();

                break;
            case R.id.delete_contact:
                bottomSheetListener.deleteClicked();

                break;
        }
        dismiss();
    }

    public interface BottomSheetListener {

        void editClicked();

        void deleteClicked();

    }
}
