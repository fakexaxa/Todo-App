package com.example.feature_todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.model_todo.TodoRepo
import com.example.model_todo.local.TodoDatabase
import com.example.model_todo.response.Todo
import com.example.model_todo.util.FilterOption
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class TodoViewModel(app: Application) : AndroidViewModel(app) {

    private val todoRepo by lazy {
        TodoRepo(TodoDatabase.getDatabase(app, viewModelScope).todoDao())
    }


    private val filterFlow = MutableStateFlow(FilterOption.ALL)

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val todos: LiveData<List<Todo>> = filterFlow.flatMapLatest { filterOption ->
        todoRepo.getTodosWithFilter(filterOption)
    }.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun add(todo: Todo) = viewModelScope.launch {
        todoRepo.insert(todo)
    }

    fun updateFilter(option: FilterOption) {
        filterFlow.value = option
    }
    fun delete(todo: Todo)=viewModelScope.launch {
        todoRepo.deleteTodo(todo)
    }

}
