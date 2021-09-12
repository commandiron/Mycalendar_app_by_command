package com.demirli.a59mycalendar

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(task: Task)

    @Query("DELETE FROM tasks WHERE task_id = :task_id")
    fun deleteTask(task_id: Int)
}
