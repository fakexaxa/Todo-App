package com.example.feature_todo.adapter.viewholder

import android.R.attr.button
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_todo.databinding.ItemTodoBinding
import com.example.model_todo.response.Todo


class TodoViewHolder(
    private val binding: ItemTodoBinding,
    private val editClicked: (Todo) -> Unit,
    private val todoClicked: (Todo) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTodo(todo: Todo) = with(binding) {
        tvTitle.text = todo.title
        tvDescription.text = todo.content
        rbIsComplete.setBackgroundColor(if (todo.isComplete) Color.GREEN else Color.LTGRAY)
        color.setBackgroundColor(todo.color)
        tvEdit.setOnClickListener { editClicked(todo) }


        itemView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> itemView.setBackgroundColor(Color.YELLOW)
                    MotionEvent.ACTION_UP->itemView.setBackgroundColor(Color.WHITE)
                }
                return v?.onTouchEvent(event) ?: true
                todoClicked(todo)
            }
        })

    }


    companion object {
        fun newInstance(parent: ViewGroup, editClicked: (Todo) -> Unit,todoClicked: (Todo)->Unit) = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).let { binding -> TodoViewHolder(binding, editClicked, todoClicked ) }
    }
}