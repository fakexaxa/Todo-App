package com.example.feature_todo

import android.app.Application
import androidx.lifecycle.*
import com.example.feature_todo.util.ViewState
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
    private val _viewState = MutableLiveData<ViewState>()
    val viewState : LiveData<ViewState> get() = _viewState

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
    fun getTodo(ID: Int){
        viewModelScope.launch {
            val state = try {
                // get favorite items
                val result = todoRepo.getSingleTodo(ID)

                // if success add favorites to state
                _viewState.value = ViewState.Success(result)
            } catch (ex: Exception) {
                // if not success return err msg
                _viewState.value = ViewState.Error(ex.message ?: "Something went wrong")
            }
        }
    }


    // update todos
    fun saveEdit(ID: Int, title: String, content: String, completed: Boolean) {
        viewModelScope.launch {
            todoRepo.updateTodo(ID, title, content, completed)
        }
    }

    fun deleteSingleTodo(id: Int) {
        viewModelScope.launch {
            todoRepo.deleteSingleTodo(id)
        }
    }

}
