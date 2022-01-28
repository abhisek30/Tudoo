package com.abhisek.tudoo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abhisek.tudoo.data.Priority
import com.abhisek.tudoo.data.Todo
import com.abhisek.tudoo.databinding.FragmentAddBinding
import com.abhisek.tudoo.databinding.FragmentHomeBinding


class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        binding.addTodoButton.setOnClickListener {
            val todo = Todo(0,"Task2",Priority.LOW,"Description2")
            sharedViewModel.addTodo(todo)
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}