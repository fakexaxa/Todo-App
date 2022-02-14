package com.example.model_todo

import androidx.annotation.WorkerThread
import com.example.model_todo.local.dao.TodoDao
import com.example.model_todo.response.Todo
import com.example.model_todo.util.FilterOption
import kotlinx.coroutines.flow.Flow

class TodoRepo(private val todoDao: TodoDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    fun getTodosWithFilter(
        option: FilterOption
    ): Flow<List<Todo>> = when (option) {
        FilterOption.ALL -> todoDao.getTodos()
        FilterOption.COMPLETED -> todoDao.getTodos(true)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @WorkerThread
    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    // get a single todos
    suspend fun getSingleTodo(ID: Int) : Todo{
        return todoDao.getTodo(ID)
    }

    // update todo
    suspend fun updateTodo(ID: Int, title: String, content: String, completed: Boolean){
        todoDao.updateTodo(ID, title,content, completed)

    }

    suspend fun deleteSingleTodo(id: Int) {
        todoDao.deleteTodo(id)
    }

    suspend fun deleteTodo(todo: Todo){
        todoDao.delete(todo)
    }

}