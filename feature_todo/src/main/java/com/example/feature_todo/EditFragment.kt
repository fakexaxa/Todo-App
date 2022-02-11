package com.example.feature_todo

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.feature_todo.databinding.FragmentEditBinding
import com.example.feature_todo.viewmodel.TodoViewModel
import com.example.model_todo.response.Todo
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<TodoViewModel>()
    private val args by navArgs<EditFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentEditBinding.inflate(
        inflater, container, false
    ).also {
        _binding = it
        initButtons()
        initViews()
        getTodo()
    }.root

    // show current todos info
    private fun initViews() = with(binding){
        val todo = getTodo()
        val editableTitle: Editable = SpannableStringBuilder(todo.title)
        val editableContent: Editable = SpannableStringBuilder(todo.content)
        title.text = editableTitle
        content.text = editableContent
    }

    // get item from ID that was passed
    private fun getTodo(): Todo {
        return viewModel.getTodo(args.id.toInt())
    }

    // initialize buttons
    private fun initButtons() = with(binding){
        close.setOnClickListener{closeEdit()}
        delete.setOnClickListener{deleteTodo()}
        save.setOnClickListener{updateTodo()}
    }

    private fun deleteTodo() {
        val id = args.id.toInt()
        viewModel.deleteSingleTodo(id)
    }

    // update todos
    private fun updateTodo() {
        val id = args.id.toInt()
        val title = binding.title.toString()
        val content = binding.content.toString()
        viewModel.saveEdit(id, title, content)
    }

    // go back to main page
    private fun closeEdit() {
        val action =
            EditFragmentDirections.actionEditFragmentToListFragment()
        findNavController().navigate(action)
        println("close was pressed")
    }
}
