package com.code.tod

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val itemNumberTextView = itemView.findViewById<TextView>(R.id.itemNumber)
    val itemNameTextView = itemView.findViewById<TextView>(R.id.itemName)
}