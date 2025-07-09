package com.example.practiceobjectbox.repository

import com.example.practiceobjectbox.data.ObjectBox
import com.example.practiceobjectbox.data.local.entity.Note
import com.example.practiceobjectbox.data.local.entity.Note_
import io.objectbox.query.QueryBuilder

class NoteRepository {
    private val noteBox = ObjectBox.boxStore.boxFor(Note::class.java)

    fun getAllNotes(): List<Note> = noteBox.all

    fun getNotesFiltered(): List<Note> =
        noteBox.query()
            .contains(Note_.content, "longer", QueryBuilder.StringOrder.CASE_INSENSITIVE)

            .build()
            .find()

    fun getSortedNotes(): List<Note> =
        noteBox.query()
            .order(Note_.content, QueryBuilder.DESCENDING)
            .build()
            .find()

    fun insertSampleData(): Int {
        val note1 = Note(title = "Note A is short", content = "Short")
        note1.contentLength = note1.content.length

        val note2 = Note(title = "bote B", content = "This is a longer note.")
        note2.contentLength = note2.content.length

        val note3 = Note(title = "Time B", content = "This is a longer note item exception.")
        note2.contentLength = note2.content.length

        noteBox.put(note1, note2, note3)

        return noteBox.count().toInt()
    }
    fun deleteAllNotes() {
        noteBox.removeAll()
    }
    fun deleteNote(note: Note) {
        noteBox.remove(note)
    }

    fun getLongNotes(): List<Note> =
        noteBox.query()
            .greater(Note_.contentLength, 10)
            .build()
            .find()




}

