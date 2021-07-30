package com.example.roomdatabaseinandroidusingjava.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.roomdatabaseinandroidusingjava.R;

import java.util.Objects;

public class NoteActivity extends AppCompatActivity {

    private EditText et_title, et_description;
    private NumberPicker numberPicker;
    public static final String EXTRA_ID = "com.example.roomdatabaseinandroidusingjava.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.roomdatabaseinandroidusingjava.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.roomdatabaseinandroidusingjava.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.example.roomdatabaseinandroidusingjava.EXTRA_PRIORITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);
        numberPicker = findViewById(R.id.numberPicker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            et_title.setText(intent.getStringExtra(EXTRA_TITLE));
            et_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPicker.setValue(Integer.parseInt(intent.getStringExtra(EXTRA_PRIORITY)));
        }else{
            setTitle("Add note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;    //True means we want to display the menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = et_title.getText().toString();
        String description = et_description.getText().toString();
        String priority = String.valueOf(numberPicker.getValue());

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Can't add empty note sorry!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_DESCRIPTION,description);
        intent.putExtra(EXTRA_PRIORITY,priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id!=-1){
            intent.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}