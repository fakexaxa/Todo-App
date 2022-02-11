package com.example.feature_todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.feature_todo.adapter.TodoAdapter
import com.example.feature_todo.databinding.FragmentListBinding
import com.example.model_todo.response.Todo
import com.example.model_todo.util.FilterOption
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val todoAdapter by lazy { TodoAdapter(::editClicked, ::todoClicked) }
    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentListBinding.inflate(
        inflater, container, false
    ).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        todoViewModel.todos.observe(viewLifecycleOwner) { todos -> todoAdapter.submitList(todos) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        rvTodos.adapter = todoAdapter

        chipCompleted.setOnCheckedChangeListener { _, isChecked ->
            // Responds to chip checked/unchecked
            todoViewModel.updateFilter(if (isChecked) FilterOption.COMPLETED else FilterOption.ALL)
        }
    }

    private fun editClicked(todo: Todo) {
        findNavController().navigate(
            resId = com.example.todo.R.id.editGraph,
            args = bundleOf("todoId" to todo.id)
        )
    }

    private fun todoClicked(todo: Todo) {
        // do something...
    }
}