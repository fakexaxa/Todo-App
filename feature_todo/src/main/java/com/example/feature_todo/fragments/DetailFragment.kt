package com.example.feature_todo.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.feature_todo.R
import com.example.feature_todo.databinding.FragmentDetailBinding
import com.example.feature_todo.util.ViewState
import com.example.feature_todo.viewmodel.TodoViewModel
import com.example.model_todo.response.Todo
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class DetailFragment: Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<TodoViewModel>()
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(
        inflater, container, false
    ).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
    }

    private fun initObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            if (state is ViewState.Success) handleSuccess(state.todo)
            if(state is ViewState.Error) handleError(state.e)
        }
    }

    private fun handleSuccess(todo: Todo) = with(binding) {
        title.text = todo.title
        content.text = todo.content
        val image: Drawable?
        image = when(todo.isComplete){
            true -> {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_check_box, null)
            }
            false -> {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_cancel_24, null)
            }
        }
        isComplete.setImageDrawable(image)
    }

    private fun handleError(e: String) {
        Toast.makeText(context, e, Toast.LENGTH_LONG).show()
    }

    private fun initViews() {
        viewModel.getTodo(args.id)
        binding.delete.setOnClickListener { deleteTodo() }
        binding.close.setOnClickListener { closeDetail() }
    }

    private fun deleteTodo() {
        AlertDialog.Builder(context)
            .setTitle("Delete Confirmation")
            .setMessage("Are you sure you want to delete this item?")
            .setIcon(R.drawable.ic_baseline_priority_high_24)
            .setPositiveButton(R.string.yes, DialogInterface.OnClickListener { _, _ ->
                viewModel.deleteSingleTodo(args.id)
                closeDetail()
            }).setNegativeButton(R.string.no, null).show()
    }

    private fun closeDetail() {
        val action = DetailFragmentDirections.actionDetailFragmentToListFragment()
        findNavController().navigate(action)
    }

}
