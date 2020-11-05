package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
    private final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;
    private NotesDBHelper dbHelper;   //объект помощника БД\
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null)
             actionBar.hide();

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();    // получаем доступ к БД на запись

        getData();  //читаем данные из БД

        adapter = new NotesAdapter(notes); //создаем адаптер и передаем еме Заметки
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
        int id = notes.get(position).getId();
       String where = NotesContract.NotesEntry._ID + " = ?";
       String[] whereArgs = {Integer.toString(id)};

        database.delete(NotesContract.NotesEntry.TABLE_NAME, where, whereArgs );    // удаление нужного элемента по ключевому полю _id
        //notes.remove(position);                 //удаление указанного элемента с RecycleView
        getData();  //читаем массив из БД заново
        adapter.notifyDataSetChanged();         //применить на адапторе

    }

    private void getData(){             //получает данные из БД и присваивает его массиву
        notes.clear(); //каждый раз очищаем массив

        String selection  = NotesContract.NotesEntry.COLUMN_DAY_OF_WEAK + " = ?";
        String[] selectionArgs = new String[] {"6"};
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null, selection, selectionArgs, null, null, NotesContract.NotesEntry.COLUMN_PRIORITY); //запрос на ВСЕ поля, объект cursor хранит все данные и указывает на строку -1 изначально

        while (cursor.moveToNext()) { //читаем все данные из cursor по полям
            int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            int dayOfWeek = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEAK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
            notes.add(new Note(id, title, description, dayOfWeek, priority));    // и сохраняем их в массив
        }
        cursor.close();  //ОБЯЗАТЕЛЬНО ЗАКРЫВАЕМ cursor после окончания работы с ним

    }


    public void onClickAdNote(View view) {

        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);

    }
}