package com.example.feature_edit.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model_todo.TodoRepo
import com.example.model_todo.local.TodoDatabase
import com.example.model_todo.local.dao.TodoDao

class EditViewModel(app: Application) : ViewModel() {
    private val todoDao by lazy { TodoDatabase.getDatabase(app, viewModelScope).todoDao() }
    private val todoRepo by lazy { TodoRepo(todoDao) }
}