package com.yugal.notesapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.Toast;

import com.yugal.notesapp.Model.Notes;
import com.yugal.notesapp.R;
import com.yugal.notesapp.ViewModel.NotesViewModel;
import com.yugal.notesapp.databinding.ActivityInsertNotesBinding;

import java.util.Date;

public class InsertNotesActivity extends AppCompatActivity {

    ActivityInsertNotesBinding binding;
    String title, subTitle, notes;
    NotesViewModel notesViewModel;
    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        binding.greenPriority.setOnClickListener(v -> {

            binding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);
            priority = "1";
        });
        binding.yellowPriority.setOnClickListener(v -> {
            binding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.greenPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);
            priority = "2";
        });
        binding.redPriority.setOnClickListener(v -> {
            binding.redPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriority.setImageResource(0);
            binding.greenPriority.setImageResource(0);
            priority = "3";
        });

        binding.doneNotesBtn.setOnClickListener(v -> {
            title = binding.notesTitle.getText().toString();
            subTitle = binding.notesSubtitle.getText().toString();
            notes = binding.notesData.getText().toString();

            CreateNotes(title, subTitle, notes);

        });
    }

    private void CreateNotes(String title, String subTitle, String notes) {

        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d,yyyy", date.getTime());


        Notes insertNotes;
        insertNotes = new Notes();
        insertNotes.notesTitle = title;
        insertNotes.notesSubTitle = subTitle;
        insertNotes.notes = notes;
        insertNotes.notesPriority =priority;
        insertNotes.notesDate = sequence.toString();
        notesViewModel.insertNote(insertNotes);

        Toast.makeText(this, "Notes Created Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}