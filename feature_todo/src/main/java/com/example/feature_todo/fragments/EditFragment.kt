package com.example.feature_todo.fragments

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.feature_todo.databinding.FragmentEditBinding
import com.example.feature_todo.util.ViewState
import com.example.feature_todo.viewmodel.TodoViewModel
import com.example.model_todo.response.Todo
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<TodoViewModel>()
    private val args by navArgs<EditFragmentArgs>()
    val TAG = "edit"

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
        initObservers()
        getTodo()
    }.root

    // watch for changes in state
    private fun initObservers()= with(viewModel) {
        viewState.observe(viewLifecycleOwner){state ->
            if (state is ViewState.Success) handleSuccess(state.todo)
            if(state is ViewState.Error) handleError(state.e)
        }
    }

    // if fails display toast
    private fun handleError(e: String) {
        Toast.makeText(context, e, Toast.LENGTH_SHORT).show()
    }

    // if successful display todos info
    private fun handleSuccess(todo: Todo)= with(binding) {
        val editableTitle: Editable = SpannableStringBuilder(todo.title)
        val editableContent: Editable = SpannableStringBuilder(todo.content)

        title.text = editableTitle
        content.text = editableContent
        completed.isChecked = todo.isComplete
    }

    // show current todos info
    private fun initViews(){
        getTodo()
    }

    // get item from ID that was passed
    private fun getTodo() {
        viewModel.getTodo(args.id)
    }

    // initialize buttons
    private fun initButtons() = with(binding){
        close.setOnClickListener{closeEdit()}
        delete.setOnClickListener{deleteTodo()}
        save.setOnClickListener{updateTodo()}
    }

    // delete todos
    private fun deleteTodo() {
        val id = args.id
        viewModel.deleteSingleTodo(id)
        closeEdit()
    }

    // update todos
    private fun updateTodo() {
        val id = args.id
        val title = binding.title.text.toString()
        val content = binding.content.text.toString()
        val isChecked = binding.completed.isChecked

        viewModel.saveEdit(id, title, content, isChecked)
        closeEdit()
    }

    // go back to main page
    private fun closeEdit() {
        val action =
            EditFragmentDirections.actionEditFragmentToListFragment()
        findNavController().navigate(action)
    }
}
