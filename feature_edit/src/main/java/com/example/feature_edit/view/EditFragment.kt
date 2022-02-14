package com.example.feature_edit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.feature_edit.databinding.FragmentEditBinding

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentEditBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        confirmDiscard()
        confirmDelete()
    }

    private fun initViews() {
        val todoId = arguments?.getBundle("todoId")?.getString("todoId")

    }

    private fun confirmDelete() = with(binding) {
        this?.deleteButton?.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Confirm delete")
                .setMessage("Are you sure you want to delete?")
                .setPositiveButton("Confirm") { _, _ ->
                    // navigate back to ListFragment with a slide down animation
                    findNavController().navigate(com.example.todo.R.id.todoGraph)
                }
                .setNegativeButton("Cancel") { _, _ ->

                }
                .show()
        }
    }

    private fun confirmDiscard() = with(binding) {
        this?.discardButton?.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Confirm discard")
                .setMessage("Are you sure you want to discard your changes?")
                .setPositiveButton("Confirm") { _, _ ->
                    // navigate back to ListFragment with a slide down animation
                    findNavController().navigate(com.example.todo.R.id.todoGraph)
                }
                .setNegativeButton("Cancel") { _, _ ->

                }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}