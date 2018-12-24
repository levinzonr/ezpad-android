package cz.levinzonr.studypad.presentation.screens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.Note
import cz.levinzonr.studypad.domain.Notebook
import cz.levinzonr.studypad.presentation.screens.library.notes.NotesListFragment

private const val ARG_NOTEBOOK = "NOTEBOOK"
private const val ARG_NOTE = "NOTE"

fun Fragment.showNotes(notebook: Notebook) {
    view?.findNavController()?.navigate(R.id.action_notebookListFragment_to_notesListFragment,
        Bundle().apply { putParcelable(ARG_NOTEBOOK, notebook) }
    )
}

fun Fragment.showNoteEdit(note: Note?) {
    view?.findNavController()?.navigate(R.id.action_notesListFragment_to_editNoteFragment,
        Bundle().apply { putParcelable(ARG_NOTE, note) })
}

fun Fragment.showNoteDetail(note: Note) {
    view?.findNavController()?.navigate(R.id.action_notesListFragment_to_noteDetailFragment,
        Bundle().apply { putParcelable(ARG_NOTE, note) })
}

val NotesListFragment.notebook: Notebook?
    get() =  arguments?.getParcelable(ARG_NOTEBOOK)


fun Fragment.showMain() {
    startActivity(Intent(activity, MainActivity::class.java))
    activity?.finish()
}

