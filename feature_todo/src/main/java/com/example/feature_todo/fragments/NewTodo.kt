package com.example.feature_todo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.feature_todo.R
import com.example.feature_todo.databinding.TodoNewBinding
import com.example.feature_todo.viewmodel.TodoViewModel
import com.example.model_todo.response.Todo
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class NewTodo : Fragment(R.layout.todo_new) {

    private var _binding: TodoNewBinding? = null
    private val binding get() = _binding!!
    private val todoViewModel by activityViewModels<TodoViewModel>()





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = TodoNewBinding.inflate(inflater, container, false).also {
        _binding = it

    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       addTodos()
    }

    private fun addTodos()= with(binding){

        btnAdd.setOnClickListener {
            val todo=Todo(title = etTitle.text.toString(), content = etDescription.text.toString())
            todoViewModel.add(todo)
            val action = NewTodoDirections.actionNewTodoToListFragment()
            findNavController().navigate(action)

        }
    }


}