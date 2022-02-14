package com.example.feature_todo.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_todo.R
import com.example.feature_todo.adapter.SwipeDeleteCallback
import com.example.feature_todo.viewmodel.TodoViewModel
import com.example.feature_todo.adapter.TodoAdapter
import com.example.feature_todo.adapter.viewholder.TodoViewHolder
import com.example.feature_todo.databinding.FragmentListBinding
import com.example.model_todo.response.Todo
import com.example.model_todo.util.FilterOption
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ListFragment : Fragment(),TodoViewHolder.OnItemClickListener{

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val todoAdapter by lazy { TodoAdapter(::editClicked, ::todoClicked,this) }
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
        binding.fabAdd.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToNewTodo()
            findNavController().navigate(action)
        }

        val itemTouchHelper = ItemTouchHelper(swipeDeleteCallback())
        itemTouchHelper.attachToRecyclerView(rvTodos)
    }

    private fun editClicked(todo: Todo) = with(binding) {
        val action = ListFragmentDirections.actionListFragmentToEditFragment(todo.id)
        findNavController().navigate(action)


    }
    private fun todoClicked(todo: Todo) {

        binding.toolbar.setOnClickListener {
            deleteTodo(todo)
        }
    }

    private fun swipeDeleteCallback(): SwipeDeleteCallback {

        val swipeCallBack = object : SwipeDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                todoViewModel.delete(todoAdapter.currentList[position])
                binding.rvTodos.adapter?.notifyItemChanged(position)
            }
        }
        return swipeCallBack
    }

    override fun clickedTodo(todo: Todo) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(todo.id)
        findNavController().navigate(action)
    }
    private fun deleteTodo(todo: Todo){
        val builder= AlertDialog.Builder(activity)
        builder.setTitle("Confirm Delete")
        builder.setMessage("Are you sure you want to delete this todo?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            todoViewModel.delete(todo)
            dialog.cancel()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.cancel()
        }
        val alert: AlertDialog=builder.create()
        alert.show()
    }

}
