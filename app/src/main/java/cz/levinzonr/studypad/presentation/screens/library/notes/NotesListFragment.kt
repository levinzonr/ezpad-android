package cz.levinzonr.studypad.presentation.screens.library.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.Note
import cz.levinzonr.studypad.presentation.adapters.NotesAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.notebook
import cz.levinzonr.studypad.presentation.screens.showNoteDetail
import cz.levinzonr.studypad.supportActionBar
import kotlinx.android.synthetic.main.fragment_notes_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NotesListFragment : BaseFragment(), NotesAdapter.NotesItemListener {

    private lateinit var adapter: NotesAdapter

    companion object {
        fun newInstance() = NotesListFragment()
    }

    private val viewModel: NotesListViewModel by viewModel { parametersOf(notebook!!.id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        viewModel.dataSource.observe(this, Observer {
            adapter.items  = it
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        supportActionBar?.title = notebook?.name

    }


    private fun setupRecyclerView() {
        adapter = NotesAdapter()
        adapter.listener = this
        notesRv.layoutManager = LinearLayoutManager(context)
        notesRv.adapter = adapter
    }

    override fun onNoteSelected(note: Note) {
        showNoteDetail(note)
    }
}