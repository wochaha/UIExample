package com.example.uiexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class CheckBoxAdapter(private var content: ArrayList<String>,
                      private val action: (Boolean, String) -> Unit) : RecyclerView.Adapter<CheckboxViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckboxViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_check_box, parent, false)
        return CheckboxViewHolder(view, action)
    }

    override fun onBindViewHolder(holder: CheckboxViewHolder, position: Int) {
        holder.bindView(content[position])
    }

    override fun getItemCount(): Int {
        return content.size
    }

    public fun updateData(list: ArrayList<String>) {
        content.clear()
        content = list
        notifyDataSetChanged()
    }
}

class CheckboxViewHolder(private val view: View,
                         private val action: (Boolean, String) -> Unit) : RecyclerView.ViewHolder(view) {

    public fun bindView(str: String) {
        val checkbox = itemView.findViewById<CheckBox>(R.id.check_box_content)
        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            action.invoke(isChecked, str)
        }
        checkbox.text = str
    }
}