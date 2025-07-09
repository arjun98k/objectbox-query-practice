package com.example.practiceobjectbox.ui

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceobjectbox.R
import com.example.practiceobjectbox.data.ObjectBox
import com.example.practiceobjectbox.ui.adapter.NoteAdapter
import com.example.practiceobjectbox.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize ObjectBox
        ObjectBox.init(this)

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        // Initialize adapter with click-to-delete handler
        adapter = NoteAdapter { note ->
            AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Delete note titled \"${note.title}\"?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteNote(note)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Observe LiveData to update UI
        viewModel.notes.observe(this) { notes ->
            adapter.submitList(notes)
        }

        // Set up button listeners
        findViewById<Button>(R.id.btnInsert).setOnClickListener {
            viewModel.insertSample()
            viewModel.loadAll()
        }

        findViewById<Button>(R.id.btnLoadAll).setOnClickListener {
            viewModel.loadAll()
        }

        findViewById<Button>(R.id.btnFiltered).setOnClickListener {
            viewModel.loadFiltered()
        }

        findViewById<Button>(R.id.btnSorted).setOnClickListener {
            viewModel.loadSorted()
        }

        findViewById<Button>(R.id.btnDeleteAll).setOnClickListener {
            viewModel.deleteAll()
        }
    }
}
