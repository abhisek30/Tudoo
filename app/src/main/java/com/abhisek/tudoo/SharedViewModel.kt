package com.abhisek.tudoo

import android.app.Application
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.abhisek.tudoo.data.Priority
import com.abhisek.tudoo.data.Todo
import com.abhisek.tudoo.data.TodoDatabase
import com.abhisek.tudoo.data.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    val readAllTodo: LiveData<List<Todo>>
    val readActiveTodo: LiveData<List<Todo>>
    val readCompleteTodo: LiveData<List<Todo>>
    private val repository: TodoRepository

    init {
        val todoDao = TodoDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(todoDao)
        readAllTodo = repository.readAllTodo
        readActiveTodo = repository.readActiveTodo
        readCompleteTodo = repository.readCompleteTodo
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }

    fun deleteAllTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTodo()
        }
    }

    fun parsePriority(selectedPriority: String): Priority {
        return when (selectedPriority) {
            "High" -> {
                Priority.HIGH
            }
            "Medium" -> {
                Priority.MEDIUM
            }
            "Low" -> {
                Priority.LOW
            }
            else -> Priority.LOW
        }
    }

    fun verifyDataFromUser(title: String, description: String): Boolean {
        //if fields are empty return false else true
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    suspend fun pickDateTime(context: Context): Date {
        var deadline: Date = Calendar.getInstance().time
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)
        DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                deadline = pickedDateTime.time
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
        delay(10000)
        //Log.d("TIME-",str.time.toString())
        return deadline
    }
}