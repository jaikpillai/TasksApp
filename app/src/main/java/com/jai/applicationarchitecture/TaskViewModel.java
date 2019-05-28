package com.jai.applicationarchitecture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;

    private LiveData<List<TaskModelClass>> allNotes;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allNotes = repository.getAllNotes();

    }

    void insert(TaskModelClass task){
        repository.insert(task);

    }
    void delete(TaskModelClass task){
        repository.delete(task);

    }
    void update(TaskModelClass task){
        repository.update(task);

    }
    void deleteAllNotes(){
        repository.deleteAllNotes();

    }



    public LiveData<List<TaskModelClass>> getAllNotes(){
        return allNotes;
    }
}
