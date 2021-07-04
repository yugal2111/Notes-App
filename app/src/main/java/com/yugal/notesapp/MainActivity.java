package com.yugal.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yugal.notesapp.Activity.InsertNotesActivity;
import com.yugal.notesapp.Adapter.NotesAdapter;
import com.yugal.notesapp.Model.Notes;
import com.yugal.notesapp.ViewModel.NotesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton newNotesBtn;
    NotesViewModel notesViewModel;
    RecyclerView notesRecycler;
    NotesAdapter notesAdapter;

    TextView noFilter, highToLow, lowToHigh;
    List<Notes> filterNotesAllList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNotesBtn = findViewById(R.id.newNotesBtn);
        notesRecycler = findViewById(R.id.notesRecycler);

        noFilter = findViewById(R.id.noFilter);
        highToLow = findViewById(R.id.highToLow);
        lowToHigh = findViewById(R.id.lowToHigh);

        noFilter.setBackgroundResource(R.drawable.filter_selected_shape);

        noFilter.setOnClickListener(v -> {
            loadData(0);
            highToLow.setBackgroundResource(R.drawable.filter_un_shape);
            lowToHigh.setBackgroundResource(R.drawable.filter_un_shape);
            noFilter.setBackgroundResource(R.drawable.filter_selected_shape);
        });
        highToLow.setOnClickListener(v -> {
            loadData(1);
            noFilter.setBackgroundResource(R.drawable.filter_un_shape);
            lowToHigh.setBackgroundResource(R.drawable.filter_un_shape);
            highToLow.setBackgroundResource(R.drawable.filter_selected_shape);
        });
        lowToHigh.setOnClickListener(v -> {
            loadData(2);
            highToLow.setBackgroundResource(R.drawable.filter_un_shape);
            noFilter.setBackgroundResource(R.drawable.filter_un_shape);
            lowToHigh.setBackgroundResource(R.drawable.filter_selected_shape);
        });

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        newNotesBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, InsertNotesActivity.class));
        });


        notesViewModel.liveData.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filterNotesAllList = notes;
            }
        });
    }

    private void loadData(int i) {
        if(i==0){
            notesViewModel.liveData.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        }else if(i==1){
            notesViewModel.highToLow.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        }else if(i==2){
            notesViewModel.lowToHigh.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        }
    }

    public void setAdapter(List<Notes> notes){
        notesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
//        notesRecycler.setLayoutManager(new GridLayoutManager(this,2));
        notesAdapter = new NotesAdapter(MainActivity.this,notes);
        notesRecycler.setAdapter(notesAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_notes, menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Notes here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });
        return true;
    }

    private void NotesFilter(String newText) {

//        Log.e("TAG", "NotesFilter: "+newText );
        ArrayList<Notes> FilterNames = new ArrayList<>();

        for (Notes notes : this.filterNotesAllList){
            if (notes.notesTitle.contains(newText) || notes.notesSubTitle.contains(newText)){
                FilterNames.add(notes);
            }
        }
        this.notesAdapter.searchNotes(FilterNames);
    }
}