package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        notes = new ArrayList<>();
        notes.add(new Note("Парикмахер", "Сделать прическу", "понедельник", 2));
        notes.add(new Note("Баскетбол", "Игра со школьной коммандой", "вторник", 2));
        notes.add(new Note("Магазин", "Купить новые джинсы", "понедельник", 2));
        notes.add(new Note("Стоматолог", "Вылечить зуб", "понедельник", 2));
        notes.add(new Note("Парикмахер", "Сделать прическу", "среда", 2));
        notes.add(new Note("Баскетбол", "Игра со школьной коммандой", "вторник", 2));
        notes.add(new Note("Магазин", "Купить новые джинсы", "понедельник", 2));

        NotesAdapter adapter = new NotesAdapter(notes); //создаем адаптер и передаем еме Заметки

      //  recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));  // располагать элементы по ujhbpjynf последовательно
      //  recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));  // располагать элементы по вертикали последовательно, могут быть варианты
        recyclerViewNotes.setLayoutManager(new GridLayoutManager(this,3));  //располагать сеткой 3 столбца
        recyclerViewNotes.setAdapter(adapter);
    }
}