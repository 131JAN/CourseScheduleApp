package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel

    private var startTime = ""
    private var endTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]

        setupViews()
    }

    private fun setupViews() {
        findViewById<View>(R.id.ib_start_time).setOnClickListener {
            showTimePicker("start_time")
        }

        findViewById<View>(R.id.ib_end_time).setOnClickListener {
            showTimePicker("end_time")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_insert -> {
                insertCourse()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun insertCourse() {
        val edCourseName = findViewById<TextInputEditText>(R.id.ed_course_name).text.toString()
        val spinnerDay = findViewById<Spinner>(R.id.spinner_day).selectedItem.toString()
        val spinnerDayNum = getDayNumberByDayName(spinnerDay)
        val edLecturer = findViewById<TextInputEditText>(R.id.ed_lecturer).text.toString()
        val edNote = findViewById<TextInputEditText>(R.id.ed_note).text.toString()

        when {
            edCourseName.isEmpty() -> false
            startTime.isEmpty() -> false
            endTime.isEmpty() -> false
            spinnerDayNum == -1 -> false
            edLecturer.isEmpty() -> false
            edNote.isEmpty() -> false
            else -> {
                viewModel.insertCourse(
                    edCourseName,
                    spinnerDayNum,
                    startTime,
                    endTime,
                    edLecturer,
                    edNote
                )
                finish()
            }
        }
    }

    private fun showTimePicker(tag: String) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, tag)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            "start_time" -> {
                findViewById<TextView>(R.id.tv_start_time).text = timeFormat.format(calendar.time)
                startTime = timeFormat.format(calendar.time)
            }
            "end_time" -> {
                findViewById<TextView>(R.id.tv_end_time).text = timeFormat.format(calendar.time)
                endTime = timeFormat.format(calendar.time)
            }
        }
    }

    private fun getDayNumberByDayName(dayName: String): Int {
        val days = resources.getStringArray(R.array.day)
        return days.indexOf(dayName)
    }
}