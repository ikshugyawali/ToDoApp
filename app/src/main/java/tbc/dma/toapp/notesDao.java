package tbc.dma.toapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface notesDao {

    @Insert
    void insert(notesEn noteToBeInserted);

    @Delete
    void delete(notesEn noteToBeDeleted);

    @Query("SELECT * FROM NotesTable")
    LiveData<List<notesEn>> getAllNotes();

    @Update
    void update(notesEn note);

}

