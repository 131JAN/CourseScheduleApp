package com.dicoding.courseschedule.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

//TODO 2 : Define data access object (DAO)
@Dao
interface CourseDao {

    @Query("SELECT * FROM course ORDER BY ABS(day - strftime('%w', 'now')) ASC LIMIT 1")
    fun getNearestSchedule(): LiveData<Course?>

    @RawQuery(observedEntities = [Course::class])
    fun getAll(query: SupportSQLiteQuery): DataSource.Factory<Int, Course>

    @Query("SELECT * FROM course WHERE id = :id")
    fun getCourse(id: Int): LiveData<Course>

    @Query("SELECT * FROM course WHERE day = :day")
    fun getTodaySchedule(day: Int): List<Course>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(course: Course)

    @Query("DELETE FROM course WHERE id = :id")
    fun delete(id: Int)
}