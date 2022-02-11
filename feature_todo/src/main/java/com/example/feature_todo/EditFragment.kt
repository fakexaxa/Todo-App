package com.example.feature_todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.feature_todo.databinding.FragmentEditBinding
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
        getTodo()
    }.root

    // get item from ID that was passed
    private fun getTodo() {
        val currentTodo = viewModel.getTodo(args.iD.toInt())
        println(currentTodo.title)

    }

    // initialize buttons
    private fun initButtons() = with(binding){
        close.setOnClickListener{closeEdit()}
        delete.setOnClickListener{println("delete is pressed")}
        checkbox.setOnClickListener{println("checkbox is pressed")}
        save.setOnClickListener{updateTodo()}
    }

    // update todo
    private fun updateTodo() {
        val id = args.iD.toInt()
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
