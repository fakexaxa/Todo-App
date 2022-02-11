package com.example.model_todo.local.dao

import androidx.room.*
import com.example.model_todo.response.Todo
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface TodoDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM todo")
    fun getTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE isComplete IN (:complete)")
    fun getTodos(complete: Boolean): Flow<List<Todo>>

    // return item that matches the id passed in
    @Query("SELECT * FROM todo WHERE id IN (:ID)")
    suspend fun getTodo(ID: Int): Todo

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)

    @Query("DELETE FROM todo")
    suspend fun deleteAll()

    // query to update to do
    @Query("UPDATE todo SET id = :ID, title = :title, content = :content WHERE id = :ID")
    suspend fun updateTodo(ID: Int, title: String, content: String)

    @Query("DELETE FROM todo WHERE id IN (:id)")
    suspend fun deleteTodo(id: Int)

    @Delete
    suspend fun delete(todo: Todo)
}