package com.demirli.a59mycalendar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Task::class), version = 1)
abstract class TaskDb: RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {

        var INSTANCE: TaskDb? = null

        fun getInstance(context: Context): TaskDb{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDb::class.java,
                    "task_db")
                    .build()
            }
            return INSTANCE as TaskDb
        }
    }

}