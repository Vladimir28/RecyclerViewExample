package com.hfad.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter : RecyclerView.Adapter<TaskItemAdapter.TaskItemViewHolder>() {
    var data = listOf<Task>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size // зачем получаем размер?

    //Вызывается каждый раз, когда потребуется создать держатель представления
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : TaskItemViewHolder =
        TaskItemViewHolder.inflateFrom(parent)

    //Вызывается, когда данные должны отображаться в держателе представления
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int){
        val item = data[position]
        holder.bind(item)
    }

    //Определяет держатель представления
    class TaskItemViewHolder(val rootView: TextView) : RecyclerView.ViewHolder(rootView){

        fun bind(item: Task){
            rootView.text = item.taskName
        }

        companion object{
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.task_item, parent, false) as TextView
                return TaskItemViewHolder(view)
            }
        }
    }
}