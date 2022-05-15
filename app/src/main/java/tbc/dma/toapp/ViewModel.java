package tbc.dma.toapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private AppRepo myRepo;
    private final LiveData<List<notesEn>> allNotes;

    public ViewModel(@NonNull Application application) {
        super(application);
        myRepo = new AppRepo(application);
        allNotes = myRepo.getAllNotes();
    }

    LiveData<List<notesEn>> getAllNotes() {
        return allNotes;
    }

    public void insert(notesEn note) {
        myRepo.insert(note);
    }

    public void deleteNote(notesEn noteToBeDeleted) {
        myRepo.deleteNote(noteToBeDeleted);
    }

    public void update(notesEn noteToBeUpdated) {
        myRepo.updateNote(noteToBeUpdated);
    }
}