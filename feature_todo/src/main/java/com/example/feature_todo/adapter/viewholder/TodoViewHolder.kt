package com.example.feature_todo.adapter.viewholder

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_todo.databinding.ItemTodoBinding
import com.example.model_todo.response.Todo


class TodoViewHolder(
    private val binding: ItemTodoBinding,
    private val editClicked: (Todo) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("ClickableViewAccessibility")
    fun bindTodo(todo: Todo) = with(binding) {
        tvTitle.text = todo.title
        tvDescription.text = todo.content
        rbIsComplete.setBackgroundColor(if (todo.isComplete) Color.GREEN else Color.LTGRAY)
        color.setBackgroundColor(todo.color)
        tvEdit.setOnClickListener { editClicked(todo) }


        itemView.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> itemView.setBackgroundColor(Color.YELLOW)
                MotionEvent.ACTION_UP -> itemView.setBackgroundColor(Color.WHITE)
            }
            v?.onTouchEvent(event) ?: true
        }

    }


    companion object {
        fun newInstance(parent: ViewGroup, editClicked: (Todo) -> Unit) = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).let { binding -> TodoViewHolder(binding, editClicked) }
    }
}