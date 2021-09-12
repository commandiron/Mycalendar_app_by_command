package com.demirli.a59mycalendar

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), TaskAdapter.OnUserClickDeleteButton {

    private lateinit var viewModel: MainViewModel

    private lateinit var taskAdapter: TaskAdapter

    private lateinit var bsBehavior: BottomSheetBehavior<LinearLayout>

    private var dayOfMonthForData: Int? = null
    private var monthForData: Int? = null
    private var yearForData: Int? = null

    private var calendarPickFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // keyboard kapanmıyor halledilecek.

        setUi()
    }

    private fun setUi(){

        getAllTasks()

        setBottomSheetBehavior()

        setCalendarListener()

        setAddButtonListener()
    }

    private fun getAllTasks(){

        viewModel = MainViewModel(application)

        viewModel.getAllTasks().observe(this, androidx.lifecycle.Observer {

            task_recyclerView.layoutManager = LinearLayoutManager(this)
            taskAdapter = TaskAdapter(it, this)
            task_recyclerView.adapter = taskAdapter
        })
    }

    private fun setBottomSheetBehavior(){

        bsBehavior = BottomSheetBehavior.from(bottom_sheet)

        add_event_bottom_sheet.setOnClickListener {
            if(bsBehavior.state != BottomSheetBehavior.STATE_EXPANDED){
                bsBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                add_event_bottom_sheet.setText("CLOSE")
            }else{
                bsBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                add_event_bottom_sheet.setText("ADD EVENT")
            }

            if(calendarPickFlag == false){
                val c = Calendar.getInstance()
                dayOfMonthForData = c.get(Calendar.DAY_OF_MONTH)
                monthForData = c.get(Calendar.MONTH)
                yearForData = c.get(Calendar.YEAR)

                date_tv.setText(dayOfMonthForData.toString() + "." + (monthForData!!+1).toString() + "." + yearForData.toString())
            }
        }
    }

    private fun setCalendarListener(){

        calendar.setOnDateChangeListener(object: CalendarView.OnDateChangeListener{
            override fun onSelectedDayChange(
                view: CalendarView,
                year: Int,
                month: Int,
                dayOfMonth: Int
            ) {

                calendarPickFlag = true

                if(bsBehavior.state != BottomSheetBehavior.STATE_EXPANDED){
                    bsBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    add_event_bottom_sheet.setText("CLOSE")
                }

                dayOfMonthForData = dayOfMonth
                monthForData = month
                yearForData = year

                date_tv.setText(dayOfMonthForData.toString() + "." + (monthForData!!+1).toString() + "." + yearForData.toString())
            }
        })
    }

    private fun setAddButtonListener(){

        add_btn.setOnClickListener {

            if(task_ed.text.toString() == ""){
                Toast.makeText(this, "Task area is empty.", Toast.LENGTH_SHORT).show()
            }else{
                val task = Task(dayOfMonth = dayOfMonthForData!!, month = monthForData!!, year = yearForData!!, task = task_ed.text.toString())
                viewModel.addTask(task)

                bsBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                add_event_bottom_sheet.setText("ADD EVENT")
            }
        }
    }

    override fun onUserClick(task: Task) {
        viewModel.deleteTask(task.task_id)
    }
}
