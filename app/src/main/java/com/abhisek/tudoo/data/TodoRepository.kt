package com.abhisek.tudoo.data

import androidx.lifecycle.LiveData

class TodoRepository(private val todoDao: TodoDao) {
    val readAllTodo: LiveData<List<Todo>> = todoDao.readAllTodo()
    val readActiveTodo: LiveData<List<Todo>> = todoDao.readActiveTasks()
    val readCompleteTodo: LiveData<List<Todo>> = todoDao.readCompleteTasks()

    suspend fun addTodo(todo: Todo) {
        todoDao.addTodo(todo)
    }

    suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo)
    }

    suspend fun deleteAllTodo() {
        todoDao.deleteAllTodo()
    }
}