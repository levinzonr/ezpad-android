package cz.levinzonr.studypad.presentation.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.loadAuthorImage
import cz.levinzonr.studypad.presentation.common.MaxLinesChipGroup
import cz.levinzonr.studypad.shownText
import kotlinx.android.synthetic.main.item_published_notebook.view.*

class PublishedNotebooksAdapter(val type: AdapterType = AdapterType.Full) : RecyclerView.Adapter<PublishedNotebooksAdapter.ViewHolder>(){

    enum class AdapterType {
        Short, Full
    }

    var items : List<PublishedNotebook.Feed> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: PublishedNotebookItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.layoutInflater
        val view =  when(type) {
            AdapterType.Full -> inflater.inflate(R.layout.item_published_notebook, parent, false)
            AdapterType.Short ->inflater.inflate(R.layout.item_published_notebook_small, parent, false)
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(notebook: PublishedNotebook.Feed) {


            view.notebookTitleTv.text = notebook.title
            view.notebookCommentsCountTv.text = notebook.commentCount.toString()
            view.notebookNotesCountTv.text = notebook.notesCount.toString()
            view.notebookTopicTv.text = notebook.topic

            if (type ==  AdapterType.Full) {
                view.notebookAuthorTv.text = notebook.author.displayName
                view.notebookAuthorIv.loadAuthorImage(notebook.author.photoUrl)
                view.notebookDescriptionTv.text = notebook.description


                view.notebookTagsCg.apply {
                    removeAllViews()
                    notebook.tags.map { Chip(view.context).apply { text = it } }.forEach {
                        addView(it)
                    }
                }


            } else {
                val school = view.findViewById<TextView>(R.id.notebookSchoolTv)
                school.shownText = notebook.university?.fullName
                (view.notebookTagsCg as MaxLinesChipGroup).let { group ->
                    group.removeAllViews()
                    group.addAll(notebook.tags.sortedBy {it.length}.map { if (it.length > 7) "${it.substring(0, 7)}..." else it })
                }
            }

            view.setOnClickListener { listener?.onPublishedNotebookClicked(notebook) }
        }
    }




    interface PublishedNotebookItemListener {

        fun onPublishedNotebookClicked(publishedNotebook: PublishedNotebook.Feed)
    }
}