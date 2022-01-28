package com.abhisek.tudoo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.abhisek.tudoo.data.Todo
import com.abhisek.tudoo.data.TodoDatabase
import com.abhisek.tudoo.data.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    val readAllTodo : LiveData<List<Todo>>
    private val repository : TodoRepository

    init{
        val todoDao = TodoDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(todoDao)
        readAllTodo = repository.readAllTodo
    }

    fun addTodo(todo:Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodo(todo)
        }
    }
}