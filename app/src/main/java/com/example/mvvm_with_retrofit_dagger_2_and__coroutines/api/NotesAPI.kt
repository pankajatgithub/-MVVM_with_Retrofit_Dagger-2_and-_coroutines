package com.example.mvvm_with_retrofit_dagger_2_and__coroutines.api

import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.NoteRequest
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.NoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesAPI {

    @GET("/notes")
    suspend fun getNotes(): Response<List<NoteResponse>>
    @POST("/notes")
    suspend fun createNote(@Body noteRequest: NoteRequest):Response<NoteResponse>

   @PUT("/notes/{noteId}") //@path variable should be same as id variable of end point
    suspend fun updateNote(@Path("noteId")noteId:String,@Body noteRequest: NoteRequest): Response<NoteResponse>

    @DELETE("/notes/{noteId}")
    suspend fun deleteNote( @Path("noteId") noteId:String): Response<NoteResponse>
}