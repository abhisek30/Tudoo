package com.abhisek.tudoo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.abhisek.tudoo.data.Todo
import kotlinx.android.synthetic.main.custom_row_todo.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private var todoList = emptyList<Todo>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_todo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = todoList[position]
        holder.itemView.textView2.text = currentItem.title.toString()
        holder.itemView.textView3.text = currentItem.description.toString()
        holder.itemView.priority_txt.text = currentItem.priority.toString()
        holder.itemView.status_txt.text = currentItem.status.toString()
        holder.itemView.deadlineTxtview.text = getDeadline(currentItem.deadline)

        holder.itemView.rowLayout.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun setData(todo: List<Todo>) {
        this.todoList = todo
        notifyDataSetChanged()
    }

    fun getDeadline(date: Date?): String {
        var deadline = "No Deadline"
        if (date != null) {
            val timeFormat = SimpleDateFormat("EEE, dd-MM-yyyy, hh:mm a", Locale.ENGLISH)
            val time = timeFormat.format(date.time)
            deadline = time.toString()
        }
        return deadline
    }
}