package com.titan.apps.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.titan.apps.todolist.R
import com.titan.apps.todolist.db.Note

class TodoListAdapter(
    private val mList: List<Note>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = mList[position]

        holder.title.text = note.title
        holder.description.text = note.description

        holder.delete.setOnClickListener {
            onClickListener.onDelete(position, note)
        }

        if (note.status == 0) {
            holder.checkbox.visibility = View.VISIBLE

            holder.checkbox.isChecked = false
            holder.checkbox.setOnCheckedChangeListener { _, b ->
                if (b) {
                    onClickListener.onChecked(position, note)
                }
            }

        } else if (note.status == 1) {
            holder.checkbox.visibility = View.GONE
            holder.delete.setImageResource(R.drawable.ic_delete_black)
            holder.parentLayout.setBackgroundResource(R.color.red)
        }


        if (note.status == 0) {
            holder.todoItemView.setOnClickListener {
                onClickListener.onClick(position, note)
            }
        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun checkTask(position: Int) {
        notifyItemChanged(position)
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: AppCompatTextView = itemView.findViewById(R.id.title)
        val description: AppCompatTextView = itemView.findViewById(R.id.description)
        val delete: AppCompatImageView = itemView.findViewById(R.id.delete)
        val checkbox: AppCompatCheckBox = itemView.findViewById(R.id.checkbox)
        val todoItemView: View = itemView.findViewById(R.id.todoItem)
        val parentLayout: View = itemView.findViewById(R.id.parentLayout)
    }

    interface OnClickListener {
        fun onClick(position: Int, note: Note)
        fun onDelete(position: Int, note: Note)
        fun onChecked(position: Int, note: Note)
    }
}