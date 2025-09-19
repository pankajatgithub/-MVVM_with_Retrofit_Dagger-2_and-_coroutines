package com.example.mvvm_with_retrofit_dagger_2_and__coroutines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.api.NotesAPI
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.databinding.FragmentMainBinding
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.NoteResponse
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    //Smart cast to 'FragmentMainBinding' is impossible, because '_binding' is a mutable property
    // that could have been changed by this time
    private val binding get() = _binding!!
    private val noteViewModel by viewModels<NoteViewModel>()
    private lateinit var adapter: NoteAdapter

    @Inject
    lateinit var notesAPI: NotesAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = NoteAdapter(::onNoteClicked) //:: using this we can send function as parameter, but in java we were creating interface for this
//kotlin is a first class functional language , we can send function as parameter, store functions in vairable etc
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        noteViewModel.getNotes()
        binding.noteList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.noteList.adapter = adapter
        binding.addNote.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_notesFragment)
        }

    }

    private fun bindObserver() {

        noteViewModel.noteLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    adapter.submitList(it.data)
                }

                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

            }
        })
    }

    private fun onNoteClicked(noteResponse: NoteResponse) {

        val bundle = Bundle()
        bundle.putString("note",Gson().toJson(noteResponse))
        findNavController().navigate(R.id.action_mainFragment_to_notesFragment,bundle)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}