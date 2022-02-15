package com.example.feature_todo.adapter.viewholder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_todo.databinding.ItemTodoBinding
import com.example.model_todo.response.Todo

class TodoViewHolder(
    private val binding: ItemTodoBinding,
    private val editClicked: (Todo) -> Unit,
    private val todoLongClicked: (Todo)-> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTodo(todo: Todo) = with(binding) {
        tvTitle.text = todo.title
        tvDescription.text = todo.content
        rbIsComplete.setBackgroundColor(if (todo.isComplete) Color.GREEN else Color.LTGRAY)
        color.setBackgroundColor(todo.color)
        tvEdit.setOnClickListener { editClicked(todo) }
    }.also {
             itemView.setOnLongClickListener {
           todoLongClicked(todo)
            true
        }
 }
    companion object {
        fun newInstance(parent: ViewGroup, editClicked: (Todo) -> Unit,todoLongClicked: (Todo) -> Unit) = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).let { binding -> TodoViewHolder(binding, editClicked,todoLongClicked) }
    }
}