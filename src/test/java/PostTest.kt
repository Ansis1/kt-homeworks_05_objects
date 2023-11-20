import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.neotology.*
import java.lang.StringBuilder

class PostTest {

    @Before
    fun clearBeforeTestUpdate0() {
        WallService.clear()
    }

    @Test
    fun updateExisting() {

        val service = ru.neotology.WallService
        val post = service.add(Post(ownerId = 11245711, fromId = 23333456, text = "Test post1"))
        service.add(Post(ownerId = 112457243, fromId = 333123456, text = "Test post2"))
        val update = post.copy(text = "Change text of post1")
        assertTrue(service.update(update))
    }

    @Test
    fun updateNoExisting() {

        val service = WallService
        service.add(Post(ownerId = 11245711, fromId = 23333456, text = "Test post1"))
        service.add(Post(ownerId = 112457243, fromId = 333123456, text = "Test post2"))
        val post = Post(12132133434, ownerId = 57243, fromId = 123456, text = "Test post3")
        val update = post.copy(text = "Change text of post1")
        assertFalse(service.update(update))
    }

    @Test
    fun addPost() {

        val service = WallService
        val newPost = service.add(Post(ownerId = 11245711, fromId = 23333456, text = "Test post1"))
        assertTrue(newPost.id > 0)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val service = WallService
        service.add(Post(ownerId = 11245711, fromId = 23333456, text = "Test post1"))
        service.createComment(
            12312312321312,
            Comment(123123213213, 12312312312312, 123123123123123, "32432423432", Donut())
        )
    }

    @Test
    fun shouldNoThrow() {
        val service = WallService
        val post = service.add(Post(ownerId = 11245711, fromId = 23333456, text = "Test post1"))
        service.createComment(
            post.id, Comment(
                123123213213, 12312312312312, 123123123123123,
                "32432423432", Donut()
            )
        )
    }

    @Test
    fun addNote() {
        val service = NoteService
        val ownerId = 23432432423423432
        assertFalse(service.add(ownerId, "test title0", "txt0") <= 0)
    }

    @Test
    fun addNotesComment() {
        val service = NoteService
        val ownerId = 23432432423423432
        val newNoteId0 = service.add(ownerId, "test title0", "txt0") //создаем заметку
        assertFalse(service.createComment(newNoteId0, ownerId, "first comment") <= 0)
    }

    @Test
    fun addForFakeNoteIDComment() {
        val service = NoteService
        val ownerId = 23432432423423432
        assertTrue(service.createComment(5454545454, ownerId, "first comment") <= 0)
    }

    @Test
    fun lookNotesByArrayIDs() {
        val service = NoteService
        val ownerId = 23432432423423432
        val newNoteId0 = service.add(ownerId, "test title0", "txt0") //создаем заметку
        val newNoteId = service.add(ownerId, "test title1", "txt") //создаем заметку
        val notesIds: Array<Long> = arrayOf(newNoteId0, newNoteId)
        val notes = service.get(notesIds) // получаем комменты к заметке
        assertTrue(notes.isNotEmpty())
    }

    @Test
    fun lookNotesByEmptyArrayIDs() {
        val service = NoteService
        val notes = service.get(emptyArray()) // получаем комменты к заметке
        assertFalse(notes.isNotEmpty())
    }

    @Test
    fun lookNoteByID() {
        val service = NoteService
        val newNoteId0 = service.add(23432432423423432, "test title0", "txt0") //создаем заметку
        val note: Note? = service.getById(newNoteId0)
        assertFalse(note == null)
    }

    @Test
    fun lookNoteByFakeID() {
        val service = NoteService
        val note: Note? = service.getById(455454545454)
        assertTrue(note == null)
    }

    @Test
    fun getNoteComments() {
        val service = NoteService
        val newNoteId0 = service.add(23432432423423432, "test title0", "txt0") //создаем заметку
        var newComment = service.createComment(
            newNoteId0, 23432432423423432,
            "first comment"
        ) // создаем коммент к заметке
        newComment = service.createComment(
            newNoteId0, 23432432423423432,
            "second comment"
        ) // создаем ещё коммент к заметке
        val noteComments = service.getComments(newNoteId0)
        assertFalse(noteComments.isNullOrEmpty())
    }

    @Test
    fun getNoteCommentsByFakeNoteID() {
        val service = NoteService
        val noteComments = service.getComments(4545454545454)
        assertTrue(noteComments.isNullOrEmpty())
    }

    @Test
    fun editNote() {
        val service = NoteService
        val newNoteId0 = service.add(23432432423423432, "test title0", "txt0") //создаем заметку
        val editNoteResult = service.edit(
            newNoteId0, "changed title", "", -1,
            -1, "", ""
        )
        assertTrue(editNoteResult == 1)
    }

    @Test
    fun editNoteWithEmptyParams() {
        val service = NoteService
        val newNoteId0 = service.add(23432432423423432, "test title0", "txt0") //создаем заметку
        val editNoteResult = service.edit(
            newNoteId0, "", "", -1, -1,
            "", ""
        )
        assertTrue(editNoteResult == -1)
    }

    @Test
    fun editNoteWithFakeNoteID() {
        val service = NoteService
        val editNoteResult = service.edit(
            5656565656565, "", "", -1, -1,
            "", ""
        )
        assertTrue(editNoteResult == -2)
    }

    @Test
    fun editNoteComment() {
        val service = NoteService
        val newNoteId0 = service.add(23432432423423432, "test title0", "txt0") //создаем заметку
        val nComment = service.createComment(
            newNoteId0, 23432432423423432,
            "first comment"
        ) // создаем коммент к заметке
        assertTrue(service.editComment(nComment, "new message") == 1)
    }

    @Test
    fun editNoteCommentWithEmptyParams() {
        val service = NoteService
        val newNoteId0 = service.add(23432432423423432, "test title0", "txt0") //создаем заметку
        service.createComment(
            newNoteId0, 23432432423423432,
            "first comment"
        ) // создаем коммент к заметке
        assertTrue(service.editComment(newNoteId0, "") == -1)
    }

    @Test
    fun editNoteCommentWithFakeID() {
        val service = NoteService
        assertTrue(service.editComment(44454545454545, "test fake ID") == -2)
    }

    @Test
    fun deleteNote() {
        val service = NoteService
        val newNoteId0 = service.add(23432432423423432, "test title0", "txt0") //создаем заметку
        assertTrue(service.delete(newNoteId0) == 1)
    }

    @Test
    fun deleteNoteWithFakeID() {
        val service = NoteService
        assertTrue(service.delete(445454545454) == -1)
    }

    @Test
    fun removeNoteComment() {
        val service = NoteService
        val newNoteId0 = service.add(23432432423423432, "test title0", "txt0") //создаем заметку
        val nCommnet = service.createComment(
            newNoteId0, 23432432423423432,
            "first comment"
        ) // создаем коммент к заметке
        assertTrue(service.deleteComment(nCommnet) == 1)
    }

    @Test
    fun removeNoteCommentWithFakeID() {
        val service = NoteService
        assertTrue(service.deleteComment(554545454545454) == -1)
    }

    @Test
    fun restoreNoteComment() {
        val service = NoteService
        val newNoteId0 = service.add(23432432423423432, "test title0", "txt0") //создаем заметку
        val nComment = service.createComment(
            newNoteId0, 23432432423423432,
            "first comment"
        ) // создаем коммент к заметке
        service.deleteComment(nComment)
        assertTrue(service.restoreComment(nComment) == 1)
    }

    @Test
    fun restoreNoteCommentWithFakeID() {
        val service = NoteService
        assertTrue(service.restoreComment(5454545454) == -1)
    }

}
