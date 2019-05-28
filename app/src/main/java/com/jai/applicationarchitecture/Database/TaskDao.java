package com.jai.applicationarchitecture.Database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jai.applicationarchitecture.TaskModelClass;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(TaskModelClass task);

    @Update
    void update(TaskModelClass task);

    @Delete
    void delete(TaskModelClass task);

    @Query("SELECT * FROM note_table ORDER BY priority")
    LiveData<List<TaskModelClass>> getAllNotes();

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

}
