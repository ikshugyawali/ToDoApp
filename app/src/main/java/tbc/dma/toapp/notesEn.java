package tbc.dma.toapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NotesTable")
public class notesEn {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final String notesTitle;
    private final String notesText;

    public notesEn(String notesTitle, String notesText) {
        this.notesText = notesText;
        this.notesTitle = notesTitle;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNotesText() {
        return notesText;
    }

    public String getNotesTitle() {
        return notesTitle;
    }

}
