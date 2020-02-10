package com.mukesh.roomassignment.view.dashboard;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mukesh.roomassignment.R;
import com.mukesh.roomassignment.database.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.NoteHolder> {

    private List<Contact> contacts = new ArrayList<>();
    private CustomOnItemClickListener listener;

    // create instance of Random class
    private Random rand = new Random();
    private Drawable[] background;

    DashboardAdapter(Context context, CustomOnItemClickListener listener) {
        this.listener = listener;
        initBackground(context);
    }

    private void initBackground(Context context) {

        background = new Drawable[]{context.getDrawable(R.drawable.dashboard_item1_background),
                context.getDrawable(R.drawable.dashboard_item2_background),
                context.getDrawable(R.drawable.dashboard_item3_background),
                context.getDrawable(R.drawable.dashboard_item4_background)};
    }

    Contact getContactAtPosition(int position) {
        return contacts.get(position);
    }

    void setData(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dash_board_single_row, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Contact currentContact = contacts.get(position);
        holder.noteTitle.setText(currentContact.getName());
        holder.noteDescription.setText(currentContact.getEmailId());
        holder.parentLayout.setBackground(getRandomNumber());
        holder.priorityNumber.setText(("" + currentContact.getPriority()));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        TextView noteTitle, noteDescription, priorityNumber;
        ConstraintLayout parentLayout;

        NoteHolder(@NonNull final View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.name);
            noteDescription = itemView.findViewById(R.id.email);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            priorityNumber = itemView.findViewById(R.id.priorityNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClick(contacts.get(getAdapterPosition()), itemView);
                    }
                }
            });
        }
    }

    public interface CustomOnItemClickListener {
        void onItemClick(Contact contact, View itemView);
    }

    private Drawable getRandomNumber() {
        // Generate random integers in range 0 to length of array-1
        int randomNumber = rand.nextInt(background.length);
        return background[randomNumber];
    }

}
