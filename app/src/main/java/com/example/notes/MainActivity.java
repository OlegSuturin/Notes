package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    public static final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;
    private NotesDBHelper dbHelper;   //объект помощника БД

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);

        dbHelper = new NotesDBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();    // получаем доступ к БД на запись


  /*      if (notes.isEmpty()) {
            notes.add(new Note("Парикмахер", "Сделать прическу", "понедельник", 2));
            notes.add(new Note("Баскетбол", "Игра со школьной коммандой", "вторник", 32));
            notes.add(new Note("Магазин", "Купить новые джинсы", "понедельник", 2));
            notes.add(new Note("Стоматолог", "Вылечить зуб", "понедельник", 3));
            notes.add(new Note("Парикмахер", "Сделать прическу", "среда", 1));
            notes.add(new Note("Баскетбол", "Игра со школьной коммандой", "вторник", 1));
            notes.add(new Note("Магазин", "Купить новые джинсы", "понедельник", 2));
        }

        for (Note note : notes) {    //пишем данные в БД
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, note.getTitle());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, note.getDescription());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEAK, note.getDayOfWeek());
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, note.getPriority());
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
        }*/
        ArrayList<Note> notesFromDB = new ArrayList<>();   //сюда будем читать данные из БД
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null, null, null, null, null, null); //запрос на ВСЕ поля, объект cursor хранит все данные и указывает на строку -1 изначально

        while (cursor.moveToNext()) { //читаем все данные из cursor по полям
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            String dayOfWeek = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEAK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
            notesFromDB.add(new Note(title, description, dayOfWeek, priority));    // и сохраняем их в массив
        }
        cursor.close();  //ОБЯЗАТЕЛЬНО ЗАКРЫВАЕМ cursor после окончания работы с ним

        adapter = new NotesAdapter(notesFromDB); //создаем адаптер и передаем еме Заметки
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));  // располагать элементы по вертикали последовательно, могут быть варианты
        recyclerViewNotes.setAdapter(adapter);

        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {     //устанавливаем слушателя, передаем объект нашего созданного слушателя ТЕМА: РЕАКЦИЯ НА НАЖАТИЯ в RecycleView
            @Override
            public void onNoteClick(int position) {     // здесь основной код реакции на простое нажатие
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {         // здесь основной код реакции на простое нажатие
                remove(position);             //удаление элемента
            }
        });

        ItemTouchHelper itemTouch = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {   //вариант удаления СВАЙПОМ в сторону
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove(viewHolder.getAdapterPosition());    //удаление в свайпе
            }
        });
        itemTouch.attachToRecyclerView(recyclerViewNotes);   //применяем на конкретном RecycleView

    }

    private void remove(int position) {   // удаление элемента массива в отдельном методе
        notes.remove(position);                 //удаление указанного элемента с RecycleView
        adapter.notifyDataSetChanged();         //применить на адапторе

    }

    public void onClickAdNote(View view) {

        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);

    }
}