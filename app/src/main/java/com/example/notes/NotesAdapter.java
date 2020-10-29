package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter  extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

                    ArrayList<Note> notes;

    public NotesAdapter(ArrayList<Note> notes) {
        this.notes = notes;  //инициализирум массив объектами Заметка
    }

    @NonNull
    @Override               //берем макет note_item и передаем в виде аргумента конструктору NotesViewHolder
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent,false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
            Note note = notes.get(position);  //получаем объект из массива
            holder.textViewTitle.setText(note.getTitle());  //заполняем поля данными
            holder.textViewDescription.setText(note.getDescription());
            holder.textViewDayofWeek.setText(note.getDayOfWeek());
            holder.textViewPriority.setText(Integer.toString(note.getPriority()));

    }

    @Override
    public int getItemCount() {    //возвращает кол-во заметок
        return notes.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDayofWeek;
        private TextView textViewPriority;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDayofWeek = itemView.findViewById(R.id.textViewDayOfWeek);
            textViewPriority = itemView.findViewById(R.id.textViewPriority);

        }
    }
}