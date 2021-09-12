package com.demirli.a59mycalendar

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class MainRepository(context: Context) {

    private val db by lazy{ TaskDb.getInstance(context)}
    private val dao by lazy{db.taskDao()}

    fun getAllTasks():LiveData<List<Task>> = dao.getAllTasks()

    fun addTask(task: Task) = AddTaskAsyncTask(dao).execute(task)

    fun deleteTask(task_id: Int) = DeleteTaskAsyncTask(dao).execute(task_id)

    private class AddTaskAsyncTask(val dao: TaskDao): AsyncTask<Task, Void, Void>(){
        override fun doInBackground(vararg params: Task?): Void? {
            dao.addTask(params[0]!!)
            return null
        }
    }

    private class DeleteTaskAsyncTask(val dao: TaskDao): AsyncTask<Int, Void, Void>(){
        override fun doInBackground(vararg params: Int?): Void? {
            dao.deleteTask(params[0]!!)
            return null
        }
    }
}