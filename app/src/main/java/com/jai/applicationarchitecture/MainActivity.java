package com.jai.applicationarchitecture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private TaskViewModel taskViewModel;
    EmptyRecyclerView recyclerView;
    View emptyView;
    TextView noremind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton ButtonAddNote = findViewById(R.id.btn_add_note);
        ButtonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);

            }
        });

        emptyView=findViewById(R.id.empty_recycler);


        recyclerView =  findViewById(R.id.recycler_view);
        final TaskRecyclerAdapter taskRecyclerAdapter = new TaskRecyclerAdapter();
        recyclerView.setAdapter(taskRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setEmptyView(emptyView);

        //recyclerView.setHasFixedSize(true);






        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        taskViewModel.getAllNotes().observe(this, new Observer<List<TaskModelClass>>() {

            @Override
            public void onChanged(List<TaskModelClass> task) {

                taskRecyclerAdapter.submitList(task);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;

            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                taskViewModel.delete(taskRecyclerAdapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        taskRecyclerAdapter.setItemClickListener(new TaskRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TaskModelClass task) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, task.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, task.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, task.getPriority());
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, task.getId());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
            TaskModelClass task = new TaskModelClass(title, description, priority);
            taskViewModel.insert(task);


            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Task cannot be Saved", Toast.LENGTH_SHORT).show();
                return;


            } else {
                String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
                String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
                int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

                TaskModelClass task = new TaskModelClass(title, description, priority);
                task.setId(id);
                taskViewModel.update(task);
                Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show();

            }
        } else {
            //update UI
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {


            case R.id.delete_all_note:

                taskViewModel.deleteAllNotes();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
