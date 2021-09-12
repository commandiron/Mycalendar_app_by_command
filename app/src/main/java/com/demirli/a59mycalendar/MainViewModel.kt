package com.demirli.a59mycalendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository: MainRepository by lazy { MainRepository(application)}

    fun getAllTasks(): LiveData<List<Task>> = repository.getAllTasks()

    fun addTask(task: Task) = repository.addTask(task)

    fun deleteTask(task_id: Int) = repository.deleteTask(task_id)

}