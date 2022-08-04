package com.code.tod

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Adapter(val lists: ArrayList<TaskList>, val clickListener: TodoListClickListener) : RecyclerView.Adapter<ViewHolder>() {

    interface TodoListClickListener {
        fun listItemClicked(list: TaskList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var item = LayoutInflater.from(parent.context)
            .inflate(R.layout.view,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemNumberTextView.text = (position + 1).toString()
        holder.itemNameTextView.text = lists[position].name
        holder.itemView.setOnClickListener{
            clickListener.listItemClicked(lists[position])
        }

    }

    override fun getItemCount(): Int {
        return lists.size
    }

    fun addList(list: TaskList) {
        lists.add(list)
        notifyItemInserted(lists.size - 1)
    }
}