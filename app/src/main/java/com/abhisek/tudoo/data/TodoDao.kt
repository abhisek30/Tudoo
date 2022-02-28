package com.abhisek.tudoo.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTodo(todo: Todo)

    @Query("SELECT * FROM todo_table ORDER BY id DESC")
    fun readAllTodo(): LiveData<List<Todo>>

    @Update
    suspend fun updateTodo(todo: Todo)

    @Query("SELECT * FROM todo_table WHERE status = 'COMPLETE' ORDER BY id DESC")
    fun readCompleteTasks(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE status = 'ACTIVE' ORDER BY id DESC")
    fun readActiveTasks(): LiveData<List<Todo>>

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTodo()
}