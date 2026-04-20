package com.hfad.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hfad.tasks.databinding.FragmentTasksBinding


/**
 * A simple [Fragment] subclass.
 * Use the [TasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =  FragmentTasksBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao
        val viewModelFactory = TaskViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val instruction = { taskId: Long ->
            viewModel.onTaskClicked(taskId)
            Toast.makeText(context, "Clicked task $taskId", Toast.LENGTH_SHORT).show()
        }
        val adapter = TaskItemAdapter(instruction)

        binding.tasksList.adapter = adapter

        viewModel.navigateToTask.observe(viewLifecycleOwner, Observer{ taskId ->
            taskId?.let{
                val action = TasksFragmentDirections.actionTasksFragmentToEditTaskFragment(taskId)
                this.findNavController().navigate(action)
                viewModel.onTaskNavigated()
            }
        })

        viewModel.tasks.observe(viewLifecycleOwner){
            it?.let {
                adapter.submitList(it)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}