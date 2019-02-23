package cz.levinzonr.studypad.storage.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete

import cz.levinzonr.studypad.domain.models.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note WHERE notebookId = :notebookId")
    fun getNotesFromNotebook(notebookId: Long) : LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putAll(list: List<Note>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(note: Note)

    @Query("SELECT * FROM note WHERE notebookId = :notebookId")
    fun getList(notebookId: Long) : List<Note>

    @Delete
    fun deleteAll(list: List<Note>)

}