package com.example.model_todo.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.model_todo.response.Todo
import kotlinx.coroutines.flow.Flow

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
    fun getTodo(ID: Int): Todo

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)

    @Query("DELETE FROM todo")
    suspend fun deleteAll()
}