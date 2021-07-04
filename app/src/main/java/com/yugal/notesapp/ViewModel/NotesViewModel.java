package com.yugal.notesapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yugal.notesapp.Model.Notes;
import com.yugal.notesapp.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<Notes>> liveData;
    public LiveData<List<Notes>> highToLow;
    public LiveData<List<Notes>> lowToHigh;

    public NotesViewModel(Application application) {
        super(application);

        repository = new NotesRepository(application);
        liveData = repository.liveData;
        highToLow = repository.highToLow;
        lowToHigh = repository.lowToHigh;

    }

   public void insertNote(Notes notes){
        repository.insertNotes(notes);
    }

    public void deleteNote(int id){
        repository.deleteNotes(id);
    }

   public void updateNote(Notes notes){
        repository.updateNotes(notes);
    }
}
