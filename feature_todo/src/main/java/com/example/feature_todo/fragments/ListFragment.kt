package com.example.feature_todo.fragments
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.feature_todo.viewmodel.TodoViewModel
import com.example.feature_todo.adapter.TodoAdapter
import com.example.feature_todo.databinding.FragmentListBinding
import com.example.model_todo.response.Todo
import com.example.model_todo.util.FilterOption
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class ListFragment : Fragment(){

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val todoAdapter by lazy { TodoAdapter(::editClicked, ::todoClicked) }
    private val todoViewModel by activityViewModels<TodoViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentListBinding.inflate(
        inflater, container, false
    ).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        todoViewModel.todos.observe(viewLifecycleOwner) { todos -> todoAdapter.submitList(todos) }

        initViews()
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
        binding.fabAdd.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToNewTodo()
            findNavController().navigate(action)
        }

    }

    private fun editClicked(todo: Todo): Unit {
        // do something...
    }

    private fun todoClicked(todo: Todo) {


    }





}