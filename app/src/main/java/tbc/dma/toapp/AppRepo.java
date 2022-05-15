package tbc.dma.toapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepo {

    private notesDao myNotesDao;
    private LiveData<List<notesEn>> myNotes;

    AppRepo(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        myNotesDao = db.notesdao();
        myNotes = myNotesDao.getAllNotes();
    }

    LiveData<List<notesEn>> getAllNotes() {
        return myNotes;
    }

    void insert(notesEn noteToBeInserted) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            myNotesDao.insert(noteToBeInserted);
        });
    }

    void deleteNote(notesEn noteToBeDeleted) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            myNotesDao.delete(noteToBeDeleted);
        });
    }

    void updateNote(notesEn noteToBeUpdated) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            myNotesDao.update(noteToBeUpdated);
        });
    }
}
