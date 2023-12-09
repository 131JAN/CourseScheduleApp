package com.dicoding.courseschedule.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.ui.ViewModelFactory
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import com.dicoding.courseschedule.ui.list.ListActivity
import com.dicoding.courseschedule.ui.setting.SettingsActivity
import com.dicoding.courseschedule.util.DayName
import com.dicoding.courseschedule.util.QueryType
import com.dicoding.courseschedule.util.timeDifference

//TODO 15 : Write UI test to validate when user tap Add Course (+) Menu, the AddCourseActivity is displayed
class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: DataRepository
    private var queryType = QueryType.CURRENT_DAY

    //TODO 5 : Show nearest schedule in CardHomeView and implement menu action
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.title = resources.getString(R.string.today_schedule)

        repository = DataRepository.getInstance(applicationContext)!!

        viewModel = ViewModelProvider(this, ViewModelFactory(repository)).get(HomeViewModel::class.java)

        viewModel.nearestSchedule.observe(this) { course ->
            showNearestSchedule(course)
        }

        viewModel.loadNearestSchedule()
    }

    private fun showNearestSchedule(course: Course?) {
        checkQueryType(course)
        course?.apply {
            val dayName = DayName.getByNumber(day)
            val time = String.format(getString(R.string.time_format), dayName, startTime, endTime)
            val remainingTime = timeDifference(day, startTime)

            val cardHome = findViewById<CardHomeView>(R.id.view_home)
            cardHome.setCourseName(courseName)
            cardHome.setTime(time)
            cardHome.setRemainingTime(remainingTime)
        }

        findViewById<TextView>(R.id.tv_empty_home).visibility =
            if (course == null) View.VISIBLE else View.GONE
    }

    private fun checkQueryType(course: Course?) {
        if (course == null) {
            val newQueryType: QueryType = when (queryType) {
                QueryType.CURRENT_DAY -> QueryType.NEXT_DAY
                QueryType.NEXT_DAY -> QueryType.PAST_DAY
                else -> QueryType.CURRENT_DAY
            }
            viewModel.setQueryType(newQueryType)
            queryType = newQueryType
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            R.id.action_add -> {
                Log.d("HomeActivity", "Add menu item selected")
                startActivity(Intent(this, AddCourseActivity::class.java))
                return true
            }
            R.id.action_list -> {
                Log.d("HomeActivity", "List menu item selected")
                startActivity(Intent(this, ListActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}