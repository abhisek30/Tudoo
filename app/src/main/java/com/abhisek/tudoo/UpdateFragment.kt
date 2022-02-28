package com.abhisek.tudoo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.abhisek.tudoo.data.Priority
import com.abhisek.tudoo.data.Status
import com.abhisek.tudoo.data.Todo
import com.abhisek.tudoo.databinding.FragmentUpdateBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null

    private val binding get() = _binding!!
    private val args: UpdateFragmentArgs by navArgs()
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var date: Date
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        setViews()

        val deadlineButtonGroup = binding.updateDeadlineBtnGroup
        val deadlineSelectedButtons = deadlineButtonGroup.selectedButtons[0]
        deadlineSelectedButtons.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                date = getDateTime()
            }
        }

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        binding.updateTodoButton.setOnClickListener {
            updateTaskInDB(Status.ACTIVE)
        }
        binding.CompleteTodoButton.setOnClickListener {
            updateTaskInDB(Status.COMPLETE)
        }

        return view
    }

    private fun updateTaskInDB(status: Status) {
        val mTitle = binding.updateTitleEditText.text.toString()
        val mDescription = binding.updateDescriptionEdittext.text.toString()
        val mPriority = getPrioritySelected()
        val mDeadline = (date.year != -1)
        val validation = sharedViewModel.verifyDataFromUser(mTitle, mDescription)

        if (validation && mDeadline) {
            val todo = Todo(args.currentItem.id, mTitle, mPriority, mDescription, status, date)
            sharedViewModel.updateTodo(todo)
            Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
        } else {
            Toast.makeText(requireContext(), "Please Fill out all Fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun getPrioritySelected(): Priority {
        val priorityButtonGroup = binding.updatePriorityBtnGroup
        val selectedPriority = priorityButtonGroup.selectedButtons[0].text
        return sharedViewModel.parsePriority(selectedPriority)
    }

    private fun setViews() {
        binding.updateTitleEditText.setText(args.currentItem.title)
        binding.updateDescriptionEdittext.setText(args.currentItem.description)
        parsePriorityToView(args.currentItem.priority)
        date = args.currentItem.deadline
        parseDeadlineToView(args.currentItem.deadline)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parsePriorityToView(selectedPriority: Priority) {
        var view = binding.updateHighBtn
        when (selectedPriority.toString()) {
            "HIGH" -> {
                view = binding.updateHighBtn
            }
            "MEDIUM" -> {
                view = binding.updateMediumBtn
            }
            "LOW" -> {
                view = binding.updateLowBtn
            }
            else -> view = binding.updateHighBtn
        }
        binding.updatePriorityBtnGroup.selectButton(view)
    }

    private suspend fun getDateTime(): Date {
        val dateTime = CoroutineScope(Dispatchers.Main).async {
            sharedViewModel.pickDateTime(requireContext())
        }

        val timeFormat = SimpleDateFormat("EEE, dd-MM-yyyy, hh:mm a", Locale.ENGLISH)
        val time = timeFormat.format(dateTime.await().time)
        binding.updateChooseBtn.text = time.toString()
        return dateTime.await()
    }

    private fun parseDeadlineToView(deadline: Date) {
        var view = binding.updateChooseBtn
        val timeFormat = SimpleDateFormat("EEE, dd-MM-yyyy, hh:mm a", Locale.ENGLISH)
        val time = timeFormat.format(deadline.time)
        view.text = time.toString()
        binding.updateDeadlineBtnGroup.selectButton(view)
    }
}