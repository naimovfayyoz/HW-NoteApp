package uz.direction.noteapp.ui.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uz.direction.noteapp.R
import uz.direction.noteapp.databinding.UpdateFragmentBinding
import uz.direction.noteapp.data.model.Note
import uz.direction.noteapp.ui.viewModel.NoteViewModel

class UpdateFragment : Fragment(R.layout.update_fragment) {

    private var _binding: UpdateFragmentBinding? = null
    private val binding: UpdateFragmentBinding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = UpdateFragmentBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val content = binding.editContentEt
        val title = binding.editTitleEt

        title.text = Editable.Factory.getInstance().newEditable(args.currentNote.title)
        content.text = Editable.Factory.getInstance().newEditable(args.currentNote.text)
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_menu, menu)
        menu.findItem(R.id.menu_save).isVisible = isHidden
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
            }
            R.id.menu_edit -> {
                updateItem()
            }


        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {

        val title = binding.editTitleEt.text.toString()
        val content = binding.editContentEt.text.toString()

        if (title.isNotBlank()) {
            val updatedNote = Note(id = args.currentNote.id, title = title, text = content)
            noteViewModel.updateUser(updatedNote)
            findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            noteViewModel.deleteUser(args.currentNote)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentNote.title}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentNote.title}?")
        builder.setMessage("Are you sure you want to delete ${args.currentNote.title}?")
        builder.create().show()
    }


}