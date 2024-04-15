package com.example.mvvm_notes.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mvvm_notes.data.NotesDatabase
import com.example.mvvm_notes.databinding.FragmentNotesBinding
import com.example.mvvm_notes.presentation.adapter.NotesItemAdapter
import com.example.mvvm_notes.presentation.viewmodel.NotesViewModel
import com.example.mvvm_notes.presentation.viewmodel.NotesViewModelFactory

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dao = NotesDatabase.getInstance(application).notesDao
        val viewModelFactory = NotesViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[NotesViewModel::class.java]
        val adapter = NotesItemAdapter { noteId ->
            viewModel.onNoteItemClicked(noteId)
        }
        binding.viewModel = viewModel
        binding.notesList.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.notes.observe(viewLifecycleOwner) { notesList ->
            notesList?.let {
                adapter.submitList(it)
            }
        }

        viewModel.navigateToNote.observe(viewLifecycleOwner) { noteId ->
            noteId?.let {
                val action =
                    NotesFragmentDirections.actionNotesFragmentToEditNoteFragment(
                        noteId
                    )
                this.findNavController().navigate(action)
                viewModel.onNoteItemNavigated()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
