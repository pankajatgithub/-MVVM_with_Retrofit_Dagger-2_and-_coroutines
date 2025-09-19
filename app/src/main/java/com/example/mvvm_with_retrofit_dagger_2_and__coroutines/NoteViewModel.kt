package com.example.mvvm_with_retrofit_dagger_2_and__coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.NoteRequest
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val notesRepository: NotesRepository) :ViewModel() {

    val noteLiveData get() = notesRepository.notesLiveData
    val statusLiveData get() = notesRepository.statusLiveData

    fun getNotes(){
        viewModelScope.launch {
            notesRepository.getNotes()
        }
    }

    fun createNote(noteRequest: NoteRequest){
        viewModelScope.launch {
            notesRepository.createNote(noteRequest)
        }
    }

    fun updateNote(noteId:String,noteRequest: NoteRequest){
        viewModelScope.launch {
            notesRepository.updateNote(noteId,noteRequest)
        }
    }

    fun deleteNote(noteId: String){
        viewModelScope.launch {
            notesRepository.deleteNote(noteId)
        }
    }

}