package com.abhisek.tudoo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abhisek.tudoo.data.Priority
import com.abhisek.tudoo.data.Status
import com.abhisek.tudoo.data.Todo
import com.abhisek.tudoo.databinding.FragmentAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel
    val calendar = Calendar.getInstance()
    private lateinit var date: Date
    private var choosenDate: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        date = calendar.time
        //Log.d("DATE",date.toString())
        //Log.d("DATE",date.year.toString())
        calendar.set(Calendar.YEAR, 1899)
        date = calendar.time
        //Log.d("DATE",date.toString())
        //Log.d("DATE",date.year.toString())

        val priorityButtonGroup = binding.priorityBtnGroup
        priorityButtonGroup.selectButton(binding.highBtn)
        val deadlineButtonGroup = binding.deadlineBtnGroup
        deadlineButtonGroup.setOnSelectListener { button: ThemedButton ->
            val deadlineSelectedButtons = deadlineButtonGroup.selectedButtons[0]
            deadlineSelectedButtons.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    date = getDateTime()
                }
            }
            choosenDate = true
        }

        binding.addTodoButton.setOnClickListener {
            insertTaskToDB()
        }
        return view
    }

    private fun insertTaskToDB() {
        val mTitle = binding.titleEditText.text.toString()
        val mDescription = binding.descriptionEdittext.text.toString()
        val mPriority = getPrioritySelected()
        val mDeadline = choosenDate && (date.year != -1)
        val validation = sharedViewModel.verifyDataFromUser(mTitle, mDescription)

        if (validation && mDeadline) {
            val todo = Todo(0, mTitle, mPriority, mDescription, Status.ACTIVE, date)
            sharedViewModel.addTodo(todo)
            Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        } else {
            Toast.makeText(requireContext(), "Please Fill out all Fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun getPrioritySelected(): Priority {
        val priorityButtonGroup = binding.priorityBtnGroup
        val selectedPriority = priorityButtonGroup.selectedButtons[0].text
        return sharedViewModel.parsePriority(selectedPriority)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun getDateTime(): Date {
        val dateTime = CoroutineScope(Dispatchers.Main).async {
            sharedViewModel.pickDateTime(requireContext())
        }

        val timeFormat = SimpleDateFormat("EEE, dd-MM-yyyy, hh:mm a", Locale.ENGLISH)
        val time = timeFormat.format(dateTime.await().time)
        binding.chooseBtn.text = time.toString()
        return dateTime.await()
    }
}