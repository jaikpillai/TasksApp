package com.jai.applicationarchitecture.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jai.applicationarchitecture.TaskModelClass;


@Database(entities = TaskModelClass.class,version = 1)
public abstract class TasksDatabase extends RoomDatabase {

        private static TasksDatabase instance;

        public abstract TaskDao noteDao();

        public static synchronized TasksDatabase getInstance(Context context){

            if(instance==null){

                instance = Room.databaseBuilder(context.getApplicationContext(),
                        TasksDatabase.class, "note_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
            }
            return instance;

        }

        private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new PopulateDbAsyncTask(instance).execute();
            }
        };

        private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

            private TaskDao taskDao;

            private PopulateDbAsyncTask(TasksDatabase db){
                taskDao = db.noteDao();
            }

            @Override
            protected Void doInBackground(Void... voids) {


//                taskDao.insert(new TaskModelClass("Title 1","this is desc of Title 1",10));
//                taskDao.delete(new TaskModelClass("Title 2","this is desc of Title 2",20));
//                taskDao.delete(new TaskModelClass("Title 4","this is desc of Title 3",230));
                return null;
            }
        }


}
