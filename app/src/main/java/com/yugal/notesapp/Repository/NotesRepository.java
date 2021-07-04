package com.yugal.notesapp.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.yugal.notesapp.Dao.NotesDao;
import com.yugal.notesapp.Database.NotesDatabase;
import com.yugal.notesapp.Model.Notes;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<Notes>> liveData;
    public LiveData<List<Notes>> highToLow;
    public LiveData<List<Notes>> lowToHigh;


    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
        liveData = notesDao.liveData();
        highToLow = notesDao.highToLow();
        lowToHigh = notesDao.lowToHigh();
    }

    public void insertNotes(Notes notes) {
        notesDao.insertNotes(notes);
    }

    public void deleteNotes(int id) {
        notesDao.deleteNotes(id);
    }

    public void updateNotes(Notes notes) {
        notesDao.updateNotes(notes);
    }
}
