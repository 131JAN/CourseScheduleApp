package com.dicoding.courseschedule.paging

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.util.DayName.Companion.getByNumber

class CourseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private lateinit var course: Course
    private val timeString = itemView.context.resources.getString(R.string.time_format)
    private var clickListener: ((Course) -> Unit)? = null

    init {
        itemView.setOnClickListener {
            clickListener?.invoke(course)
        }
    }

    //TODO 7 : Complete ViewHolder to show item
    fun bind(course: Course, clickListener: (Course) -> Unit) {
        this.course = course
        this.clickListener = clickListener

        val tvCourseName: TextView = itemView.findViewById(R.id.tv_course)
        val tvCourseTime: TextView = itemView.findViewById(R.id.tv_time)


        course.apply {
            tvCourseName.text = course.courseName
            val dayName = getByNumber(day)
            val timeFormat = String.format(timeString, dayName, startTime, endTime)
            tvCourseTime.text = timeFormat
        }
    }

    fun getCourse(): Course = course
}