
/*Закомментированный код, это код, который использовался раньше без представления с переработкой*/
package com.hfad.tasks


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(val dao: TaskDao): ViewModel() {
    var newTaskName =""
    val tasks = dao.getAll()

    private val _navigateToTask = MutableLiveData<Long?>()
    val navigateToTask: LiveData<Long?>
        get() = _navigateToTask

/*    val taskString = tasks.map{
        tasks -> formatTasks(tasks)
    }*/

/*    private fun formatTasks(tasks: List<Task>) = tasks.fold(""){
        str, item -> str + '\n' + formatTask(item)
    }

    private fun formatTask(task: Task) = StringBuilder().apply {
            appendLine("ID: ${task.taskId}")
            appendLine("Name ${task.taskName}")
            appendLine("Complete: ${task.taskDone}")
    }.toString()*/

    fun onTaskClicked(taskId: Long){
        _navigateToTask.value = taskId
    }
    fun onTaskNavigated(){
        _navigateToTask.value = null
    }

    fun addTask(){
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }
    }


}