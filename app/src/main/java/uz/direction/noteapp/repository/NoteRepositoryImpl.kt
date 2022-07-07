package uz.direction.noteapp.repository

import androidx.lifecycle.LiveData
import uz.direction.noteapp.data.NoteDao
import uz.direction.noteapp.model.Note

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {

    val readAllData: LiveData<List<Note>> = noteDao.getAllData()


    override suspend fun insertNote(note: Note) {
        noteDao.insertText(note)
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateText(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteText(note)
    }

    override suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }
}