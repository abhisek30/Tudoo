package com.abhisek.tudoo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhisek.tudoo.databinding.FragmentHomeBinding
import com.shrikanthravi.collapsiblecalendarview.data.Day
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar
import java.util.*


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpRecyclerView()
        setUpCalenderView()
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        binding.addActionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

        sharedViewModel.readActiveTodo.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
            /*for (i in user) {
                val date1 = Calendar.getInstance().time
                val date2 = i.deadline
                if (date1.compareTo(date2) > 0) {
                    Log.d("app", "Date1 is after Date2")
                } else if (date1.compareTo(date2) < 0) {
                    Log.d("app", "Date1 is before Date2")
                }
            }*/
        })

        return view
    }

    private fun setUpCalenderView() {
        var collapsibleCalendar = binding.calendarView
        collapsibleCalendar.setExpandIconVisible(true)
        val today = GregorianCalendar()
        collapsibleCalendar.addEventTag(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(
                Calendar.DAY_OF_MONTH
            )
        )
        today.add(Calendar.DATE, 1)
        collapsibleCalendar.selectedDay = Day(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(
                Calendar.DAY_OF_MONTH
            )
        )
        collapsibleCalendar.addEventTag(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(
                Calendar.DAY_OF_MONTH
            ), Color.BLUE
        )
        collapsibleCalendar.params = CollapsibleCalendar.Params(0, 100)
        binding.calendarView.setCalendarListener(object : CollapsibleCalendar.CalendarListener {
            override fun onDayChanged() {

            }

            override fun onClickListener() {
                if (collapsibleCalendar.expanded) {
                    collapsibleCalendar.collapse(400)
                } else {
                    collapsibleCalendar.expand(400)
                }
            }

            override fun onDaySelect() {
                //val day : Day =
                Log.d("HOME", collapsibleCalendar.selectedDay?.day.toString())
                Log.d("HOME", collapsibleCalendar.selectedDay?.month.toString())
                Log.d("HOME", collapsibleCalendar.selectedDay?.year.toString())
                Log.d("HOME", collapsibleCalendar.selectedDay?.diff.toString())
            }

            override fun onItemClick(v: View) {

            }

            override fun onDataUpdate() {

            }

            override fun onMonthChange() {

            }

            override fun onWeekChange(position: Int) {

            }
        })
    }

    private fun setUpRecyclerView() {
        adapter = HomeAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}