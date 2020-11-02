package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    public static final ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);


        if (notes.isEmpty()){
            notes.add(new Note("Парикмахер", "Сделать прическу", "понедельник", 2));
            notes.add(new Note("Баскетбол", "Игра со школьной коммандой", "вторник", 32));
            notes.add(new Note("Магазин", "Купить новые джинсы", "понедельник", 2));
            notes.add(new Note("Стоматолог", "Вылечить зуб", "понедельник", 3));
            notes.add(new Note("Парикмахер", "Сделать прическу", "среда", 1));
            notes.add(new Note("Баскетбол", "Игра со школьной коммандой", "вторник", 1));
            notes.add(new Note("Магазин", "Купить новые джинсы", "понедельник", 2));
        }



        NotesAdapter adapter = new NotesAdapter(notes); //создаем адаптер и передаем еме Заметки
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));  // располагать элементы по вертикали последовательно, могут быть варианты
        recyclerViewNotes.setAdapter(adapter);

        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {     //устанавливаем слушателя  ТЕМА: РЕАКЦИЯ НА НАЖАТИЯ в RecycleView
            @Override
            public void onNoteClick(int position) {     // здесь основной код реакции на нажатие
                Toast.makeText(MainActivity.this, "Номер позиции: " + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClickAdNote(View view) {

        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);

    }
}