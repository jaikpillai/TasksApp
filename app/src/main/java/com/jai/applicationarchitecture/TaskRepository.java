package com.jai.applicationarchitecture;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jai.applicationarchitecture.Database.TaskDao;
import com.jai.applicationarchitecture.Database.TasksDatabase;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<TaskModelClass>> allNotes;

    public TaskRepository(Application application){

        TasksDatabase database = TasksDatabase.getInstance(application);

        taskDao = database.noteDao();

        allNotes = taskDao.getAllNotes();

    }

    public void isEmpty(TaskModelClass task){
        new InsertNoteAsyncTask(taskDao).execute(task);

    }

    public void insert(TaskModelClass task){
        new InsertNoteAsyncTask(taskDao).execute(task);

    }
    public void update(TaskModelClass task){
        new UpdateNoteAsyncTask(taskDao).execute(task);

    }
    public void delete(TaskModelClass task){
        new DeleteNoteAsyncTask(taskDao).execute(task);

    }
    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(taskDao).execute();

    }
    public LiveData<List<TaskModelClass>> getAllNotes(){
        return allNotes;
    }

    private class InsertNoteAsyncTask extends AsyncTask<TaskModelClass,Void,Void>{
        private TaskDao taskDao;

        private InsertNoteAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskModelClass... task) {
            taskDao.insert(task[0]);
            return null;
        }
    }
    private class UpdateNoteAsyncTask extends AsyncTask<TaskModelClass,Void,Void>{
        private TaskDao taskDao;

        private UpdateNoteAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskModelClass... task) {
            taskDao.update(task[0]);
            return null;
        }
    }
    private class DeleteNoteAsyncTask extends AsyncTask<TaskModelClass,Void,Void>{
        private TaskDao taskDao;

        private DeleteNoteAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskModelClass... task) {
            taskDao.delete(task[0]);
            return null;
        }
    }
    private class DeleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void>{
        private TaskDao taskDao;

        private DeleteAllNotesAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            taskDao.deleteAllNotes();
            return null;
        }
    }

}
