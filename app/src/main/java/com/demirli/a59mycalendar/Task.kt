package com.demirli.a59mycalendar

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
class Task(

    @PrimaryKey(autoGenerate = true)
    var task_id: Int = 0,

    var dayOfMonth: Int,
    var month: Int,
    var year: Int,
    var task: String
)