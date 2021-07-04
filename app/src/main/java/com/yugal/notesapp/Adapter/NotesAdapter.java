package com.yugal.notesapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Update;

import com.yugal.notesapp.Activity.UpdateNotesActivity;
import com.yugal.notesapp.MainActivity;
import com.yugal.notesapp.Model.Notes;
import com.yugal.notesapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewholder> {
    MainActivity mainActivity;
    List<Notes> notes;
    List<Notes> allNotesitem;

    public NotesAdapter(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
        allNotesitem = new ArrayList<>(notes);
    }

    public void searchNotes(List<Notes> filterName){
        this.notes = filterName;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public notesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new notesViewholder(LayoutInflater.from(mainActivity).inflate(R.layout.items_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.notesViewholder holder, int position) {

        Notes note = notes.get(position);

        switch (note.notesPriority) {
            case "1":
                holder.notesPriority.setBackgroundResource(R.drawable.green_shape);
                break;
            case "2":
                holder.notesPriority.setBackgroundResource(R.drawable.yellow_shape);
                break;
            case "3":
                holder.notesPriority.setBackgroundResource(R.drawable.red_shape);
                break;
        }

        holder.title.setText(note.notesTitle);
        holder.subTitle.setText(note.notesSubTitle);
        holder.notesDate.setText(note.notesDate);

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(mainActivity,UpdateNotesActivity.class);
            intent.putExtra("id",note.id);
            intent.putExtra("title",note.notesTitle);
            intent.putExtra("subtitle",note.notesSubTitle);
            intent.putExtra("notes",note.notes);
            intent.putExtra("priority",note.notesPriority);
            mainActivity.startActivity(intent);

        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class notesViewholder extends RecyclerView.ViewHolder{

        TextView title, subTitle, notesDate;
        View notesPriority;

        public notesViewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notesTitle);
            subTitle = itemView.findViewById(R.id.notesSubtitle);
            notesDate = itemView.findViewById(R.id.notesDate);
            notesPriority = itemView.findViewById(R.id.notesPriority);
        }
    }
}
