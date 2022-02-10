package com.example.feature_todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.feature_todo.databinding.FragmentEditBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<TodoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentEditBinding.inflate(
        inflater, container, false
    ).also {
        _binding = it
        initButtons()
    }.root

    private fun initButtons() = with(binding){
        close.setOnClickListener{viewModel.closeEdit()}
        delete.setOnClickListener{println("delte is pressed")}
        checkbox.setOnClickListener{println("close is pressed")}
        save.setOnClickListener{println("save is pressed")}


    }
}
