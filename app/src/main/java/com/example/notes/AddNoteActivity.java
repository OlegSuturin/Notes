package com.example.notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerDaysOfWeek;
    private RadioGroup radioGroupPriority;
    private Button buttonSaveNote;

    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null)
            actionBar.hide();

        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDaysOfWeek = findViewById(R.id.spinnerDaysOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroopPriority);
        buttonSaveNote = findViewById(R.id.buttonAdNote);

    }

    public void onClickAdNote(View view) {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int dayOfWeek = spinnerDaysOfWeek.getSelectedItemPosition();

        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());

        // MainActivity.notes.add(new Note(title, description, dayOfWeek, priority));
        if (isField(title, description)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, title);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, description);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEAK, dayOfWeek+1);
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, priority);
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);   //пишем в БД


            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else
            Toast.makeText(this,  R.string.worning_fill_fields, Toast.LENGTH_SHORT).show();

    }

    private boolean isField(String title, String description) {    //проверка на то, что вводимые поля заполнены, другие поля выбираемые - не могут быть пустыми

        return !title.isEmpty() && !description.isEmpty();

    }

}