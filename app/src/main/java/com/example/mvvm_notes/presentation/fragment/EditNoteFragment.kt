package com.example.mvvm_notes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mvvm_notes.data.NotesDatabase
import com.example.mvvm_notes.databinding.FragmentEditNoteBinding
import com.example.mvvm_notes.presentation.viewmodel.EditNoteViewModel
import com.example.mvvm_notes.presentation.viewmodel.EditNoteViewModelFactory

class EditNoteFragment : Fragment() {
    private var binding: FragmentEditNoteBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        val view = binding?.root

        val noteId = EditNoteFragmentArgs.fromBundle(requireArguments()).noteId
        val application = requireNotNull(this.activity).application
        val dao = NotesDatabase.getInstance(application).notesDao
        val viewModelFactory = EditNoteViewModelFactory(noteId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[EditNoteViewModel::class.java]

        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        viewModel.navigateToList.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                val action =
                    EditNoteFragmentDirections.actionEditNoteFragmentToNotesFragment()
                view?.findNavController()?.navigate(action)
                viewModel.onNavigatedToList()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
