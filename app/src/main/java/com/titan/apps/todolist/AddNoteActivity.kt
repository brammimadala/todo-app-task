package com.titan.apps.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.titan.apps.todolist.databinding.ActivityAddNoteBinding
import com.titan.apps.todolist.db.Note
import com.titan.apps.todolist.db.NoteDataBase
import com.titan.apps.todolist.repository.AddNoteRepository
import com.titan.apps.todolist.view_model.NoteViewModel
import com.titan.apps.todolist.vm_factory.AddNoteViewModelFactory

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var vm: NoteViewModel
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val receivedIntent = intent
        note = receivedIntent.getParcelableExtra<Note>("note")
        if (note != null) {
            binding.pageTitle.setText(R.string.update_task)
            binding.addTodoTask.setText(R.string.update_task)

            binding.noteTitle.setText(note?.title)
            binding.noteDescription.setText(note?.description)
        }

        val dao = NoteDataBase.getInstance(application).noteDao
        val repository = AddNoteRepository(dao)
        val factory = AddNoteViewModelFactory(repository)
        vm = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        binding.backImg.setOnClickListener {
            finish()
        }

        binding.addTodoTask.setOnClickListener {

            val title = binding.noteTitle.text.toString().trim()
            val description = binding.noteDescription.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(this, "Please Enter Title", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                Toast.makeText(this, "Please Enter Description", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (note != null) {
                val id = note?.id
                vm.insert(Note(id!!, title, description))
            } else {
                vm.insert(Note(0, title, description))
            }

            binding.noteTitle.setText("")
            binding.noteDescription.setText("")

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

    }
}