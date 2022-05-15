package tbc.dma.toapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static ViewModel myViewModel;
    FloatingActionButton floatingActionButton;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView delete;
    Adapter newAdapter;
    static AlertDialog.Builder builder;
    boolean wantEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.createInstanceIcon);
        delete = findViewById(R.id.delete);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_container);
        builder = new AlertDialog.Builder(this);

        newAdapter = new Adapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(newAdapter);

        myViewModel = new ViewModelProvider(this).get(ViewModel.class);

        myViewModel.getAllNotes().observe(this, new Observer<List<notesEn>>() {
            @Override
            public void onChanged(List<notesEn> notesEns) {
                newAdapter.setNotesData(notesEns);
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (!wantEdit) {
                    if (result.getResultCode() == RESULT_OK) {
                        String nTitle = result.getData().getStringExtra(CreateInstanceActivity.Title);
                        String nText = result.getData().getStringExtra(CreateInstanceActivity.Description);
                        notesEn insertNote = new notesEn(nTitle, nText);
                        myViewModel.insert(insertNote);
                        Toast.makeText(MainActivity.this, "Note Created", Toast.LENGTH_SHORT).show();
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                    } else {
                        Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (result.getResultCode() == RESULT_OK) {
                        int id = result.getData().getIntExtra("Key", -1);
                        if (id == -1) {
                            Toast.makeText(MainActivity.this, "Something went wrong while updating!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String nTitle = result.getData().getStringExtra(CreateInstanceActivity.Title);
                        String nText = result.getData().getStringExtra(CreateInstanceActivity.Description);
                        notesEn updateNote = new notesEn(nTitle, nText);
                        updateNote.setId(id);
                        myViewModel.update(updateNote);
                        Toast.makeText(MainActivity.this, "Note Updated", Toast.LENGTH_SHORT).show();
                        wantEdit = false;
                    }
                }
            }
        });

        newAdapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(notesEn note) {
                wantEdit = true;
                Intent intent = new Intent(MainActivity.this, CreateInstanceActivity.class);
                intent.putExtra("Key", note.getId());
                intent.putExtra("Title", note.getNotesTitle());
                intent.putExtra("Description", note.getNotesText());
                intent.putExtra("Update", "true");
                activityResultLauncher.launch(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this, CreateInstanceActivity.class);
                activityResultLauncher.launch(newIntent);
            }
        });
    }

    public static void deleteNote(notesEn note) {
        builder.setMessage("Are you sure you want to delete note?").setTitle("Delete Note")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myViewModel.deleteNote(note);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}