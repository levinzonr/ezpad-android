package cz.levinzonr.studypad.rest

import cz.levinzonr.studypad.data.*
import cz.levinzonr.studypad.domain.models.*
import kotlinx.coroutines.Deferred
import retrofit2.http.*

private const val API = "api"
private const val AUTH = "/auth"

interface Api {

    @POST("$AUTH/login")
    fun login(@Query("token") token: String) : Deferred<UserProfile>


    @POST("$AUTH/register")
    fun createAccount(@Body createAccountRequest: CreateAccountRequest) : Deferred<FirebaseResponse>

    @GET("$API/university/find")
    fun getUniversities(@Query("query") query: String) : Deferred<List<University>>

    @POST("$API/users/signup/finish")
    fun updateUniversity(@Body updateUniversityRequest: UpdateUniversityRequest) : Deferred<Any>

    @GET("$API/users/me")
    fun getAuthenticatedUserProfile() : Deferred<UserProfile>

    //-----------------------------------------------------------------------------


    @GET("$API/notebooks")
    fun getNotebooks() : Deferred<List<NotebooksResponse>>

    @POST("$API/notebooks")
    fun postNotebook(@Body createNotebookRequest: CreateNotebookRequest) : Deferred<NotebooksResponse>

    @PATCH("$API/notebooks/{id}")
    fun updateNotebook(@Path("id") id: String, @Body updateNotebookRequest: UpdateNotebookRequest) : Deferred<NotebooksResponse>

    @DELETE("$API/notebooks/{id}")
    fun deleteNotebook(@Path("id") id: String) : Deferred<Unit>

    @POST("$API/notebooks/import")
    fun importPublisheNotebook(@Query("id") id: String) : Deferred<NotebooksResponse>

    //-----------------------------------------------------------------------------

    @GET("$API/notebooks/{id}/notes")
    fun getNotesFromNotebook(@Path("id") notebookId: String) : Deferred<List<Note>>

    @POST("$API/notes")
    fun createNote(@Body createNoteRequest: CreateNoteRequest) : Deferred<Note>

    @PATCH("$API/notes/{id}")
    fun updateNote(@Path("id") noteId: Long, @Body updateNoteRequest: UpdateNoteRequest) : Deferred<Note>

    @DELETE("$API/notes/{id}")
    fun deleteNote(@Path("id") noteId: Long) : Deferred<Unit>


    //-----------------------------------------------------------------------------

    @GET("$API/shared")
    fun getRelevantNotebooks() : Deferred<List<PublishedNotebook.Feed>>

    @GET("$API/shared/{id}")
    fun getPublishedNotebookDetail(@Path("id") id: String) : Deferred<PublishedNotebook.Detail>

    @GET("$API/shared/find")
    fun findNotebooks(@Query("tags") tags: Set<String>, @Query("topic") topic: String) : Deferred<List<PublishedNotebook>>

    @POST("$API/shared")
    fun publishNotebook(@Body publishNotebookRequest: PublishNotebookRequest) : Deferred<PublishedNotebook.Feed>

    @POST("$API/shared/quick")
    fun quickPublish(@Query("id") notebookId: String) : Deferred<PublishedNotebook.Feed>

    @GET("$API/shared/tags")
    fun findTagsByName(@Query("name") name: String) : Deferred<List<String>>

    @GET("$API/config/topics")
    fun getTopics() : Deferred<List<Topic>>


    //-----------------------------------------------------------------------------


    @PATCH("$API/shared/{id}/suggestions/local")
    fun applyLocalChangesAsync(@Path("id") notebookId: String) : Deferred<PublishedNotebook.Detail>

    //-----------------------------------------------------------------------------

    @GET("$API/shared/{id}/comments")
    fun getSharedNotebookComments(@Path("id") notebookId: String) : Deferred<List<PublishedNotebook.Comment>>

    @POST("$API/shared/{id}/comment")
    fun createComment(@Path("id") notebookId: String, @Query("comment") body: String) : Deferred<PublishedNotebook.Comment>

    @DELETE("$API/shared/comments/{id}")
    fun deleteComment(@Path("id") commentId: Long)  : Deferred<Unit>

    @POST("$API/shared/comments/{id}")
    fun editComment(@Path("id") commentId: Long, @Query("comment") body: String) : Deferred<PublishedNotebook.Comment>


}