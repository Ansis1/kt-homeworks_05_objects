import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.neotology.*

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
        service.createComment(post.id, Comment(123123213213, 12312312312312, 123123123123123, "32432423432", Donut()))
    }
}
