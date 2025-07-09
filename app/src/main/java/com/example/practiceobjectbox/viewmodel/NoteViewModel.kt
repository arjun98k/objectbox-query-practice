package com.example.practiceobjectbox.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practiceobjectbox.data.local.entity.Note
import com.example.practiceobjectbox.repository.NoteRepository

class NoteViewModel : ViewModel() {
    private val repo = NoteRepository()
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    fun loadAll() {
        _notes.value = repo.getAllNotes()
    }

    fun loadFiltered() {
        _notes.value = repo.getNotesFiltered()
    }

    fun loadSorted() {
        _notes.value = repo.getSortedNotes()
    }

    fun insertSample() {
        val count = repo.insertSampleData()
        Log.d("NoteViewModel", "Notes count after insert: $count")
    }

    fun deleteAll() {
        repo.deleteAllNotes()
        _notes.value = emptyList() // clear UI list too
    }

    fun deleteNote(note: Note) {
        repo.deleteNote(note)
        _notes.value = repo.getAllNotes() // Refresh list
    }
    fun loadLongNotes() {
        _notes.value = repo.getLongNotes()
    }
}


