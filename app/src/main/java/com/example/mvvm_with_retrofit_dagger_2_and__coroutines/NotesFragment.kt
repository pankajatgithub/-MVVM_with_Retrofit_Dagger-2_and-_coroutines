package com.example.mvvm_with_retrofit_dagger_2_and__coroutines

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.databinding.FragmentNotesBinding
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.NoteRequest
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.NoteResponse
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel by viewModels<NoteViewModel>()

    private var note: NoteResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        bindHandlers()
        bindObservers()
    }
    private fun bindHandlers() {
        binding.btnDelete.setOnClickListener {

                noteViewModel.deleteNote(note!!._id)

        }
        binding.btnSubmit.setOnClickListener{
            val title = binding.txtTitle.text.toString()
            val description = binding.txtDescription.text.toString()
            val noteRequest = NoteRequest(description, title)
            if (note == null){
                noteViewModel.createNote(noteRequest)
            }else{
                noteViewModel.updateNote(note!!._id,noteRequest)
            }

        }
    }
    private fun bindObservers() {
        noteViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when (it){
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {
Log.e("Error",it.message.toString())
                }
                is NetworkResult.Loading -> {
                    Log.e("Loading",it.message.toString())

                }

            }
        })
    }


    private fun setInitialData() {
        val jsonNote = arguments?.getString("note")
        if (jsonNote != null) {
            note = Gson().fromJson(jsonNote, NoteResponse::class.java)
            note?.let {
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.description)
            }
        } else {
            //when no bundle present
            binding.addEditText.text = "Add Note"
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}