package com.yugal.notesapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yugal.notesapp.Model.Notes;
import com.yugal.notesapp.R;
import com.yugal.notesapp.ViewModel.NotesViewModel;
import com.yugal.notesapp.databinding.ActivityUpdateNotesBinding;

import java.util.Date;

public class UpdateNotesActivity extends AppCompatActivity {

    ActivityUpdateNotesBinding binding;
    String priority = "1";
    int iid;
    String stitle, ssubtitle, snotes, spriority;
    NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        iid = getIntent().getIntExtra("id", 0);
        stitle = getIntent().getStringExtra("title");
        ssubtitle = getIntent().getStringExtra("subtitle");
        snotes = getIntent().getStringExtra("notes");
        spriority = getIntent().getStringExtra("priority");

        binding.upTitle.setText(stitle);
        binding.upSubTitle.setText(ssubtitle);
        binding.upNotes.setText(snotes);

        switch (spriority) {
            case "1":
                binding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
                break;
            case "2":
                binding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
                break;
            case "3":
                binding.redPriority.setImageResource(R.drawable.ic_baseline_done_24);
                break;
        }

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

        binding.updateNotesBtn.setOnClickListener(v -> {
            String title = binding.upTitle.getText().toString();
            String subTitle = binding.upSubTitle.getText().toString();
            String notes = binding.upNotes.getText().toString();

            UpdateNotes(title, subTitle, notes);

        });
    }

    private void UpdateNotes(String title, String subTitle, String notes) {

        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d,yyyy", date.getTime());

        Notes updateNotes = new Notes();
        updateNotes.id = iid;
        updateNotes.notesTitle = title;
        updateNotes.notesSubTitle = subTitle;
        updateNotes.notes = notes;
        updateNotes.notesPriority = priority;
        updateNotes.notesDate = sequence.toString();

        notesViewModel.updateNote(updateNotes);

        Toast.makeText(this, "Notes Updated Successfully", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.ic_delete) {
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNotesActivity.this, R.style.BottomSheetStyle);
            View view = LayoutInflater.from(UpdateNotesActivity.this)
                    .inflate(R.layout.delete_bottom, (LinearLayout) findViewById(R.id.bottom_Sheet));

            sheetDialog.setContentView(view);

            TextView yes, no;

            yes = view.findViewById(R.id.delete_yes);
            no = view.findViewById(R.id.delete_no);

            yes.setOnClickListener(v -> {
                notesViewModel.deleteNote(iid);
                Toast.makeText(this, "Notes Deleted Successfully !", Toast.LENGTH_SHORT).show();
                finish();
            });
            no.setOnClickListener(v -> sheetDialog.dismiss());

            sheetDialog.show();

        }
        return true;
    }
}