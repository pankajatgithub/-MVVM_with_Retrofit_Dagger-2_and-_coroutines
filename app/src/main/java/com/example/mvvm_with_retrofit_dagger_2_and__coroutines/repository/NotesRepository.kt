package com.example.mvvm_with_retrofit_dagger_2_and__coroutines.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.api.NotesAPI
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.NoteRequest
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.NoteResponse
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

//12 minute done ,10/13
class NotesRepository @Inject constructor(private val notesAPI: NotesAPI) {

    private val _notesLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val notesLiveData: LiveData<NetworkResult<List<NoteResponse>>>
        get() = _notesLiveData

    //String is used to show message of error if response is not successful
    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData : LiveData<NetworkResult<String>>
        get() = _statusLiveData


    suspend fun getNotes() {
        _notesLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.getNotes()
        if (response.isSuccessful && response.body()!=null){
            _notesLiveData.postValue(NetworkResult.Success(response.body()!!))
        }
        else if(response.errorBody()!=null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _notesLiveData.postValue(NetworkResult.Error(errorObj.getString("message"))) //this is from api error response, message key
        }else{
            _notesLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
    suspend fun createNote(noteRequest: NoteRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.createNote(noteRequest)
        handleResponce(response,"Note created")
    }

    private fun handleResponce(response: Response<NoteResponse>,message:String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun deleteNote(noteId: String){
        _statusLiveData.postValue(NetworkResult.Loading())
val response = notesAPI.deleteNote(noteId)
        handleResponce(response,"Note deleted")
    }
    suspend fun updateNote(noteId:String, noteRequest: NoteRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.updateNote(noteId,noteRequest)
        handleResponce(response,"Note updated")

    }
}