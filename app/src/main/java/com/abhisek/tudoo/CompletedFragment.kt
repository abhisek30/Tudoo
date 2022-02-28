package com.abhisek.tudoo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abhisek.tudoo.databinding.FragmentCompletedBinding


class CompletedFragment : Fragment() {
    private var _binding: FragmentCompletedBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompletedBinding.inflate(inflater, container, false)
        val view = binding.root

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        /*val adapter = HomeAdapter()
        val recyclerView = binding.recyclerViewComplete
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        sharedViewModel.readCompleteTodo.observe(viewLifecycleOwner, Observer { user->
            adapter.setData(user)
            for (i in user){
                Log.d("LISTTAG",i.id.toString())
                Log.d("LISTTAG",i.title.toString())
                Log.d("LISTTAG",i.priority.toString())
                Log.d("LISTTAG",i.description.toString())
            }
        })*/


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}