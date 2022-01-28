package com.abhisek.tudoo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.abhisek.tudoo.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    lateinit var navController: NavController
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.addActionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment,null)
        }

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        sharedViewModel.readAllTodo.observe(viewLifecycleOwner, Observer { user->
            for (i in user){
                Log.d("LISTTAG",i.id.toString())
                Log.d("LISTTAG",i.title.toString())
                Log.d("LISTTAG",i.priority.toString())
                Log.d("LISTTAG",i.description.toString())
            }
            /*if(user!=null){
                Log.d("LISTTAG",user[0].id.toString())
                Log.d("LISTTAG",user[0].title.toString())
                Log.d("LISTTAG",user[0].priority.toString())
                Log.d("LISTTAG",user[0].description.toString())
            }*/
        })

        return view

    }
    /*override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/

}