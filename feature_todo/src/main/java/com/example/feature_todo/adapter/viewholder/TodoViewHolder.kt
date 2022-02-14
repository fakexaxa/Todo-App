package com.example.feature_todo.adapter.viewholder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_todo.databinding.ItemTodoBinding
import com.example.feature_todo.fragments.DetailFragmentDirections
import com.example.model_todo.response.Todo

class TodoViewHolder(
    private val binding: ItemTodoBinding,
    private val editClicked: (Todo) -> Unit,
    private val listener: OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {



    interface OnItemClickListener {
        fun clickedTodo(todo: Todo)
    }
    fun bindTodo(todo: Todo) = with(binding) {
        tvTitle.text = todo.title
        tvDescription.text = todo.content
        rbIsComplete.setBackgroundColor(if (todo.isComplete) Color.GREEN else Color.LTGRAY)
        color.setBackgroundColor(todo.color)
        tvEdit.setOnClickListener { editClicked(todo) }
    }.also {
             itemView.setOnLongClickListener {
            listener.clickedTodo(todo)
            true
        }
 }

    companion object {
        fun newInstance(parent: ViewGroup, editClicked: (Todo) -> Unit,listener: OnItemClickListener) = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).let { binding -> TodoViewHolder(binding, editClicked,listener) }
    }
}