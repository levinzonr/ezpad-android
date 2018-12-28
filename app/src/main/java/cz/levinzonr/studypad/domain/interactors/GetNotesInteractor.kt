package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.repository.NotesRepository

class GetNotesInteractor(private val notesRepository: NotesRepository) : BaseInputInteractor<Long, List<Note>>() {

    override suspend fun executeOnBackground(input: Long): List<Note> {
        return notesRepository.getNotesFromNotebook(input)
    }


}