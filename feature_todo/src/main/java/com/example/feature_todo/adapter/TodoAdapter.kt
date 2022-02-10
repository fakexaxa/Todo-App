package com.example.feature_todo.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.feature_todo.adapter.diffutil.TodoDiffUtil
import com.example.feature_todo.adapter.viewholder.TodoViewHolder
import com.example.model_todo.response.Todo
import com.example.todo.util.listen

class TodoAdapter(
    private val editClicked: (Todo) -> Unit,
    private val todoClicked: (Todo) -> Unit
) : ListAdapter<Todo, TodoViewHolder>(TodoDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = TodoViewHolder.newInstance(parent, editClicked).listen { position, _ ->
        todoClicked(getItem(position))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bindTodo(getItem(position))
    }
}