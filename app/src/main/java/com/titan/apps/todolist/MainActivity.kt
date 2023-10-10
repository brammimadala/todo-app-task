package com.titan.apps.todolist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.titan.apps.todolist.adapter.TodoListAdapter
import com.titan.apps.todolist.databinding.ActivityMainBinding
import com.titan.apps.todolist.db.Note
import com.titan.apps.todolist.db.NoteDataBase
import com.titan.apps.todolist.repository.MainRepository
import com.titan.apps.todolist.view_model.MainViewModel
import com.titan.apps.todolist.vm_factory.MainViewModelFactory

class MainActivity : AppCompatActivity(), TodoListAdapter.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: MainViewModel
    private lateinit var adapter: TodoListAdapter
    private lateinit var builder: AlertDialog.Builder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dao = NoteDataBase.getInstance(application).noteDao
        val repository = MainRepository(dao)
        val factory = MainViewModelFactory(repository)
        vm = ViewModelProvider(this, factory)[MainViewModel::class.java]

        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
        builder = AlertDialog.Builder(this)
        setObserver()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setObserver(){

        vm.getAllTodoList().observe(this, Observer {
            adapter = TodoListAdapter(it, this)
            binding.recyclerview.adapter = adapter
        })
    }

    override fun onClick(position: Int, note: Note) {

        val intent = Intent(this, AddNoteActivity::class.java)
        intent.putExtra("note", note)
        startActivity(intent)

    }

    override fun onDelete(position: Int, note: Note) {

        builder.setTitle(note.title)
        builder.setMessage(getString(R.string.do_you_want_to_delete))

        builder.setPositiveButton(getString(R.string.yes)) { dialog, _ ->
            dialog.dismiss()
            vm.delete(note)
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onChecked(position: Int, note: Note) {

        builder.setTitle(note.title)
        builder.setMessage(getString(R.string.is_task_completed))

        builder.setPositiveButton(getString(R.string.yes)) { dialog, _ ->
            dialog.dismiss()
            val updateNote = Note(note.id, note.title, note.description, 1)
            vm.update(updateNote)
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            adapter.checkTask(position)
            dialog.dismiss()
        }

        builder.show()

    }
}