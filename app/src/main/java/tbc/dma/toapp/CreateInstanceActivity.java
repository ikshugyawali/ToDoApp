package tbc.dma.toapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateInstanceActivity extends AppCompatActivity {

    public static final String Title = "tbc.dma.toapp.Title";
    public static final String Description = "tbc.dma.toapp.Description";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_instance);
        EditText EditTitle = findViewById(R.id.Title);
        EditText EditDescription = findViewById(R.id.Description);
        Button CreateInstance = findViewById(R.id.save);
        EditTitle.requestFocus();

        Intent editIntent = getIntent();
        if (editIntent.hasExtra("Update")) {
            setTitle("Update");
            EditTitle.setText(editIntent.getStringExtra("Title"));
            EditDescription.setText(editIntent.getStringExtra("Description"));

        } else {
            setTitle("Create");
        }

        CreateInstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nTitle = EditTitle.getText().toString().trim();
                String nText = EditDescription.getText().toString().trim();
                Intent NewIntent = new Intent();

                if (!nText.isEmpty()) {
                    int id = getIntent().getIntExtra("Key", -1);
                    if (id != -1) NewIntent.putExtra("Key", id);
                    NewIntent.putExtra(Title, nTitle);
                    NewIntent.putExtra(Description, nText);
                    setResult(RESULT_OK, NewIntent);
                    finish();
                } else {
                    Toast.makeText(CreateInstanceActivity.this, "Input field's cannot be empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}