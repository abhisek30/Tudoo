package com.abhisek.tudoo.data

import androidx.lifecycle.LiveData

class TodoRepository(private val todoDao : TodoDao) {
    val readAllTodo : LiveData<List<Todo>> = todoDao.readAllTodo()

    suspend fun addTodo(todo:Todo){
        todoDao.addTodo(todo)
    }
}