package com.example.feature_todo.util

import com.example.model_todo.response.Todo

sealed class ViewState {
    data class Success(val todo: Todo) : ViewState()
    data class Error(val e: String) : ViewState()
}