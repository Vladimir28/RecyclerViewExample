package com.hfad.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
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
    class TaskItemViewHolder(val rootView: CardView) : RecyclerView.ViewHolder(rootView){

        val taskName = rootView.findViewById<TextView>(R.id.task_name)
        val taskDone = rootView.findViewById<TextView>(R.id.task_done)

        fun bind(item: Task){
            taskName.text = item.taskName
           // taskDone.isDirty = item.taskDone
        }
        //Проверка перехода с MAC

        companion object{
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.task_item, parent, false) as CardView
                return TaskItemViewHolder(view)
            }
        }
    }
}

/*Современная версия
* import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

// Наследуемся от ListAdapter вместо RecyclerView.Adapter
class TaskItemAdapter : ListAdapter<Task, TaskItemAdapter.TaskItemViewHolder>(TaskDiffCallback()) {

    // getItemCount() переопределять НЕ НУЖНО, ListAdapter сделает это сам

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder =
        TaskItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        // Получаем элемент через встроенный метод getItem()
        val item = getItem(position)
        holder.bind(item)
    }

    class TaskItemViewHolder(val rootView: TextView) : RecyclerView.ViewHolder(rootView) {
        fun bind(item: Task) {
            rootView.text = item.taskName
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                // Предположим, R.layout.task_item содержит TextView как корневой элемент
                val view = layoutInflater.inflate(R.layout.task_item, parent, false) as TextView
                return TaskItemViewHolder(view)
            }
        }
    }

    // Класс-помощник для сравнения элементов
    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        // Проверяет, что это один и тот же объект (обычно по ID)
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id // Убедитесь, что у вашего класса Task есть уникальный ID
        }

        // Проверяет, изменилось ли содержимое внутри объекта
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem // Для data class сравнит все поля автоматически
        }
    }
}
* */