package com.demirli.a59mycalendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(var taskList: List<Task>, var onUserClickDeleteButton: OnUserClickDeleteButton): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recyclerTaskTv.setText(taskList[position].task)
        holder.recyclerDateTv.setText(taskList[position].dayOfMonth.toString() + "." + (taskList[position].month+1).toString() + "." + taskList[position].year.toString())

        holder.recyclerDeleteBtn.setOnClickListener {
            onUserClickDeleteButton.onUserClick(taskList[position])
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val recyclerTaskTv = view.findViewById<TextView>(R.id.recycler_task_tv)
        val recyclerDateTv = view.findViewById<TextView>(R.id.recycler_date_tv)
        val recyclerDeleteBtn = view.findViewById<Button>(R.id.recyler_delete_tv)
    }

    interface OnUserClickDeleteButton{
        fun onUserClick(task: Task)
    }
}