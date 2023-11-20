package ru.neotology

import java.lang.Exception
import java.lang.StringBuilder

/**
 * Пост
 *
 * @property id Идентификатор записи.
 * @property ownerId ID владельца стены, на которой размещена запись
 * @property fromId ID автора записи (от чьего имени опубликована запись).
 * @property createdBy ID администратора, опубликовавшего запись (возвращается только для сообществ при запросе с ключом доступа администратора). Возвращается в записях, опубликованных менее 24 часов назад.
 * @property date Время публикации записи в формате unixtime.
 * @property text Текст записи.
 * @property replyOwnerId Идентификатор владельца записи, в ответ на которую была оставлена текущая.
 * @property replyPostId Идентификатор записи, в ответ на которую была оставлена текущая.
 * @property friendsOnly запись была создана с опцией «Только для друзей».
 * @property postType Тип записи, может принимать следующие значения: post, copy, reply, postpone, suggest.
 * @property attachments Массив объектов, соответствующих медиаресурсам, прикреплённым к записи: фотографиям, документам, видеофайлам и другим.
 * @property signerId Идентификатор автора, если запись была опубликована от имени сообщества и подписана пользователем.
 * @property copyHistory Массив, содержащий историю репостов для записи. Возвращается только в том случае, если запись является репостом. Каждый из объектов массива, в свою очередь, является объектом-записью стандартного формата.
 * @property canPin Информация о том, может ли текущий пользователь закрепить запись (1 — может, 0 — не может).
 * @property canDelete может ли текущий пользователь удалить запись (1 — может, 0 — не может).
 * @property canEdit может ли текущий пользователь редактировать запись (1 — может, 0 — не может).
 * @property isPinned запись закреплена.
 * @property markedAsAds содержит ли запись отметку «реклама» (1 — да, 0 — нет).
 * @property isFavorite true, если объект добавлен в закладки у текущего пользователя.
 * @property postponedId Идентификатор отложенной записи. Это поле возвращается тогда, когда запись стояла на таймере.
 * @property likes Информация о лайках к записи.
 * @property comments Информация о комментариях к записи
 * @constructor Creates a new post.
 *
 */
data class Post(
    val id: Long = 0,
    val ownerId: Int,
    val fromId: Int,
    val createdBy: Int = 0,
    val date: Long = System.currentTimeMillis(),
    val text: String,
    val replyOwnerId: Int = 0,
    val replyPostId: Int = 0,
    val friendsOnly: Boolean = false,
    val postType: String = "post",
    val attachments: Array<Attachments>? = emptyArray(),
    val signerId: Int = 0,
    val copyHistory: Array<Post>? = emptyArray(),
    val canPin: Boolean = false,
    val canDelete: Boolean = false,
    val canEdit: Boolean = false,
    val isPinned: Boolean = false,
    val markedAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val postponedId: Int = 0,
    val likes: Likes? = null,
    val comments: Comments? = null
) {
    data class Comments( //Информация о комментариях к записи

        val count: Int = 0, //— количество комментариев;
        val canPost: Boolean =
            false, //информация о том, может ли текущий пользователь комментировать запись (1 — может, 0 — не может);
        val groupsCanPost: Boolean = false, //информация о том, могут ли сообщества комментировать запись;
        val canClose: Boolean = false, // — может ли текущий пользователь закрыть комментарии к записи;
        val canOpen: Boolean = false //— может ли текущий пользователь открыть комментарии к записи.

    )

    data class Copyright( //TODO Источник материала, объект с полями:
        val id: Int = 0,
        val link: String = "",
        val name: String = "",
        val type: String = ""
    )

    data class Likes( //Информация о лайках к записи,

        val count: Int = 0, // — число пользователей, которым понравилась запись;
        val userReposted: Boolean =
            false, // наличие отметки «Мне нравится» от текущего пользователя (1 — есть, 0 — нет);
        val canLike: Boolean =
            false, // может ли текущий пользователь поставить отметку «Мне нравится» (1 — может, 0 — не может);
        val canPublish: Boolean =
            false // мможет ли текущий пользователь сделать репост записи (1 — может, 0 — не может).

    )

    data class Reposts( //TODO Информация о репостах записи («Рассказать друзьям»),

        val count: Long = 0, //число пользователей, скопировавших запись;
        val wallCount: Long,
        val mailCount: Long,
        val userReposted: Boolean = false //наличие репоста от текущего пользователя (1 — есть, 0 — нет).
    )

    data class Views( //TODO Информация о просмотрах записи.

        val count: Int = 0 //число просмотров записи.

    )

    data class PostSource(  //TODO способ размещения записи на стене,
        val type: String = "", //Тип источника.

        /* Возможные значения:
    vk — запись создана через основной интерфейс сайта https://vk.com/;
    widget — запись создана через виджет на стороннем сайте;
    api — запись создана приложением через API;
    rss — запись создана посредством импорта RSS-ленты со стороннего сайта;
    sms — запись создана посредством отправки SMS-сообщения на специальный номер. */

        val platform: String = "", //Тип источника. Название платформы, если доступно: android; iphone; wphone.
        val data: String = "", // Тип действия (только для type = vk или widget). Возможные значения:

        /*profile_activity — изменение статуса под именем пользователя (для type = vk);
    profile_photo — изменение профильной фотографии пользователя (для type = vk);
    comments — виджет комментариев (для type = widget);
    like — виджет «Мне нравится» (для type = widget);
    poll — виджет опросов (для type = widget).*/

        val url: String = "" // URL ресурса, с которого была опубликована запись.

    )

    data class Geo(  //TODO Информация о местоположении
        var type: String = "", //тип места;
        var coordinates: String = "" //координаты
    ) {
        data class Place( //TODO описание места

            val id: Int = 0, //Идентификатор места.
            val title: String = "", //Название места.
            val latitude: Double = 0.0, //Географическая широта, заданная в градусах (от -90 до 90).
            val longitude: Double = 0.0, // Географическая широта, заданная в градусах (от -90 до 90).
            val created: Long = System.currentTimeMillis(), //Дата создания места.
            val icon: String = "", //Иконка места, URL изображения.
            val checkins: Int = 0, //Число отметок в этом месте.
            val updated: Long = System.currentTimeMillis(), //Дата обновления места.
            val type: Int = 0, //Тип места
            val country: Int = 0, //Идентификатор страны.
            val city: Int = 0, //Идентификатор города.
            val address: String = "" // Адрес места.
        )
    }

}

data class Comment(
    val id: Long,
    val fromId: Long,
    val date: Long,
    val text: String,
    val donut: Donut,
    val replyToUser: Long? = null,
    val replyToComment: Long? = null,
    val attachments: Attachments? = null,
    val parentsStack: Array<Long> = emptyArray(),
    val thread: ThreadComm? = null,
) {
    data class ThreadComm(
        val count: Long,
        val items: Array<Comment>? = null,
        val canPost: Boolean,
        val showReplyButton: Boolean,
        val groupsCanPost: Boolean,

        )
}

interface Attachments {

    val type: String
    val id: Long
    val date: Long

}

interface Sizes {

    val type: String
    val url: String
    val width: Long
    val height: Long

}

class ImageAttachment(
    override val type: String = "", //не исп.
    override val id: Long,
    override val date: Long,
    val image: Image
) : Attachments {
    data class Image(
        val albumId: Long,
        val ownerId: Long,
        val date: Long,
        val userId: Long,
        val text: String = "",
        val sizes: Array<ImageSize>,
        val width: Long,
        val height: Long,
        val postId: Long = 0 //дополнительно для медиавложений на стене.
    ) {
    }

    data class ImageSize(
        override val type: String,
        override val url: String,
        override val width: Long,
        override val height: Long
    ) : Sizes
}

data class AudioAttachment(
    override val type: String,
    override val id: Long,
    override val date: Long,
    val audio: Audio
) : Attachments {

    data class Audio(
        val artist: String,
        val title: String,
        val duration: Long,
        val url: String,
        val lyricsId: Long = 0,
        val ownerId: Long,
        val albumId: Long = 0,
        val genreId: Int,
        val noSearch: Boolean,
        val isHQ: Boolean

    ) {
        fun getGenreById(id: Int): String {

            return when (id) {

                1 -> "Rock"
                2 -> "Pop"
                3 -> "Rap & Hip -Hop"
                4 -> "Easy Listening"
                5 -> "House & Dance"
                6 -> "Instrumental"
                7 -> "Metal"
                21 -> "Alternative"
                8 -> "Dubstep"
                1001 -> "Jazz & Blues"
                10 -> "Drum & Bass"
                11 -> "Trance"
                12 -> "Chanson"
                13 -> "Ethnic"
                14 -> "Acoustic & Vocal"
                15 -> "Reggae"
                16 -> "Classical"
                17 -> "Indie Pop"
                19 -> "Speech"
                22 -> "Electropop & Disco"
                else -> {
                    "Other"
                }
            }
        }
    }

}

data class VideoAttachment(
    override val type: String, // video, music_video, movie
    override val id: Long,
    override val date: Long,
    val video: Video,
) : Attachments {

    data class Video(

        val ownerId: Long,
        val title: String,
        val description: String,
        val duration: Long = 0,
        val image: Image,
        val firstFrame: FirstFrame,
        val addingDate: Long,
        val views: Long = 0,
        val localViews: Long = 0,
        val comments: Int,
        val player: String,
        val platform: String,
        val canAdd: Boolean,
        val isPrivate: Boolean,
        val accessKey: String,
        val processing: Boolean,
        val isFavorite: Boolean,
        val canComment: Boolean,
        val canEdit: Boolean,
        val canLike: Boolean,
        val canRepost: Boolean,
        val canSubscribe: Boolean,
        val canAddToFaves: Boolean,
        val canAttachLink: Boolean,
        val width: Long,
        val height: Long,
        val userId: Long,
        val converting: Boolean,
        val added: Boolean,
        val isSubscribed: Boolean,
        val repeat: Int = 1,
        val balance: Long,
        val liveStatus: String, //waiting, started, finished, failed, upcoming.
        val live: Boolean = true,
        val upComing: Boolean = true,
        val spectators: Long,
        val likes: Post.Likes? = null,
        val reposts: Post.Reposts? = null,
    ) {
        data class Image(

            val height: Long,
            val url: String,
            val width: Long,
            val withPadding: Boolean,
        ) {}

        data class FirstFrame(

            val height: Long,
            val url: String,
            val width: Long
        ) {}

    }
}

data class FileAttachment(
    override var type: String = "",
    override val id: Long,
    override val date: Long,
    val file: File,
) : Attachments {

    data class File(
        var typeInternal: Int = 0, //доп. поле т. к. у нас наследование от интерфейса String.
        val ownerId: Long,
        val title: String,
        val size: Long,
        val ext: String,
        val url: String,
        val preview: Preview? = null,

        ) {

        data class Preview(
            val photo: Photo? = null,
            val graffiti: Graffiti? = null,
            val audioMessage: AudioMessage? = null,
        ) {
            data class Photo(
                val sizes: Array<Sizes>
            ) {
                data class Sizes(
                    override val type: String,
                    override val url: String,
                    override val width: Long,
                    override val height: Long
                ) : ru.neotology.Sizes
            }

            data class Graffiti(
                val src: String,
                val width: Long,
                val height: Long,
            )

            data class AudioMessage(
                val duration: Long,
                val waveForm: Array<Int>,
                val linkOgg: String,
                val linkMp3: String,
            )
        }
    }

    fun setTypeById(id: Int) {

        file.typeInternal = id
        this.type = when (id) {

            1 -> "текстовые документы"
            2 -> "архивы"
            3 -> "gif"
            4 -> "изображения"
            5 -> "аудио"
            6 -> "видео"
            7 -> "электронные книги"
            else -> {
                "неизвестно"
            }
        }

    }
}

data class AttachmentsPost(
    val type: String,
    val image: ImageAttachment? = null,
    val postedPhoto: PostedPhoto? = null,
    val video: VideoAttachment? = null,
    val audio: AudioAttachment? = null,
    val file: FileAttachment.File? = null, //Doc
    val pool: Poll? = null,
    val page: WikiPage? = null,
    val album: Album? = null,
    val photoList: Array<Long>? = null, //not null, если > 10 шт фото.
    val sticker: Sticker? = null,
    val market: Market? = null,
    val marketAlbum: MarketAlbum? = null,
    val prettyCards: PrettyCards,
    val event: Event? = null,
) {
    data class PostedPhoto(
        val id: Long,
        val ownerId: Long,
        val photo130: String,
        val photo604: String,
    )

    data class PrettyCards(
        val cardId: Long,
        val linkUrl: String,
        val title: String,
        val images: Array<Images>
    ) {}

    data class Event(
        val id: Long,
        val time: Long,
        val memberStatus: Int, // 1 — точно идёт; 2 — возможно пойдёт 3 — не идёт.
        val isFavourite: Boolean = false,
        val address: String,
        val text: String,
        val buttonText: String,
        val friends: Array<Long>? = null,
    ) {}

    data class Images(
        val url: String,
        val width: Long,
        val height: Long,
    ) {}
}

data class Poll(
    //Опрос
    val id: Long,
    val ownerId: Long,
    val created: Long,
    val question: String,
    val votes: Long,
    val answers: Array<Answers>,
    val anonymous: Boolean = false,
    val multiple: Boolean = false,
    val answersId: Long,
    val endTime: Long = 0,
    val closed: Boolean = false,
    val isBoard: Boolean = false,
    val canEdit: Boolean = false,
    val canVote: Boolean = false,
    val canReport: Boolean = false,
    val canShare: Boolean = false,
    val authorId: Long,
    val photo: ImageAttachment? = null,
    val background: Background,
    val frends: Array<Long>? = null, // Идентификаторы 3 друзей, которые проголосовали в опросе.
) {

    data class Answers(
        val id: Long,
        val text: String,
        val votes: Long,
        val rate: Int,
    )

    data class Background(
        // Фон сниппета опроса
        val id: Long,
        val type: String, // gradient, tile
        val angle: Long, // для type = gradient
        val color: String,
        val width: Long, //для type = tile
        val height: Long, // для type = tile
        val images: ImageAttachment, // для type = tile
        val points: Points, // для type = gradient

    )

    data class Points(
        val position: Int,
        val color: String,
    )

}

data class WikiPage(
    val id: Long,
    val groupId: Long,
    val creatorId: Long,
    val title: String,
    val currentUserCanEdit: Boolean = false,
    val currentUserCanEditAccess: Boolean = false,
    val whoCanView: Int, //2 — могут все; 1 — только участники сообщества; 0 — только руководители сообщества.
    val whoCanEdit: Int, //2 — могут все; 1 — только участники сообщества; 0 — только руководители сообщества.
    val edited: Long,
    val created: Long,
    val editorId: Long,
    val views: Long,
    val parent: String = "",
    val parent2: String = "",
    val source: String,
    val html: String,
    val viewUrl: String,
) {}

data class Album(
    val id: Long,
    val thumb: ImageAttachment,
    val ownerId: Long,
    val title: String,
    val description: String = "",
    val created: Long,
    val updated: Long,
    val size: Int,


    ) {}

data class Sticker(
    val id: Long,
    val stickerId: Long,
    val images: Array<AttachmentsPost.Images>,
    val imagesWithBackground: Array<AttachmentsPost.Images>,
    val animationUrl: String,
    val isAllowed: Boolean,
) {}

data class Market(
    val id: Long,
    val ownerId: Long,
    val title: String,
    val description: String,
    val price: Price,
    val dimensions: Dimensions,
    val weight: Long,
    val category: Category,
    val thumbPhoto: String,
    val date: Long,
    val availability: Int = 0,  // 0 —  доступен. 1 —  удален. 2 —  недоступен.
    val isFavourite: Boolean = false,
    val sku: String,
    val rejectInfo: RejectInfo? = null,
    val extended: Int = 0, // extended = 1 - опциональные поля.
    val photos: Array<ImageAttachment>? = null,
    val canComment: Boolean = false,
    val canRepost: Boolean = false,
    val likes: Likes? = null,
    val buttonTitle: String, // Купить / Перейти в магазин / Купить билет
) {
    data class Price(
        val amount: String,
        val currency: Currency,
        val oldAmount: String,
        val text: String
    ) {
        data class Currency(
            val id: Long,
            val name: String,
        ) {
        }
    }

    data class Dimensions(
        val width: Long,
        val height: Long,
        val length: Long,
    ) {}

    data class Category(
        val id: Long,
        val name: String,
        val section: Section,
    ) {
        data class Section(
            val id: Long,
            val name: String,
        ) {}

    }

    data class RejectInfo(
        val title: String,
        val description: String,
        val buttons: Array<Button>, //кнопки «Удалить» и «Написать в поддержку».
        val moderationStatus: Int,
        val infoLink: String,
        val whileToSupportLink: String,
    ) {

    }

    data class Likes(
        val userLikes: Long = 0,
        val count: Long = 0,
    ) {}

} //Товар

/**
 * Market album
 *
 * @property id
 * @property ownerId
 * @property title
 * @property isMain
 * @property isHidden
 * @property photo
 * @property count
 * @constructor Create empty Market album
 */
data class MarketAlbum(
    val id: Long,
    val ownerId: Long,
    val title: String,
    val isMain: Boolean = false,
    val isHidden: Boolean = false,
    val photo: ImageAttachment,
    val count: Int,

    ) {} //Подборка товаров

/**
 * Donut
 *
 * @property isDonut запись доступна только платным подписчикам VK Donut
 * @property paidDuration время, в течение которого запись будет доступна только платным подписчикам VK Donut
 * @property canPublishFreeCopy можно ли открыть запись для всех пользователей, а не только подписчиков VK Donut
 * @property editMode информация о том, какие значения VK Donut можно изменить в записи - all or duration
 * @constructor Create Donut
 */
data class Donut( // Информация о записи VK Donut

    val isDonut: Boolean = false,
    val paidDuration: Long = 0,
    val canPublishFreeCopy: Boolean = false,
    val editMode: String = ""

) {

    class Placeholder { //TODO Заглушка для пользователей, которые не оформили подписку VK Donut. Отображается вместо содержимого записи.


    }

}

data class Button(
    val title: String,
    val action: Action,

    ) {
    data class Action(
        val type: String = "open_url",
        val url: String
    ) {}

}

/**
 * Заметка
 *
 * @property nId Идентификатор заметки.
 * @property title Заголовок заметки.
 * @property text Текст заметки.
 * @property ownerId ID автора заметки
 * @property privacy Уровень доступа к заметке: 0-все, 1-друзья, 2-друзья и друзья друзей, 3-только пользователь
 * @property commentPrivacy Уровень доступа к комментированию заметки: 0-все, 1-друзья, 2-друзья и друзья друзей,
 * 3-только пользователь
 * @property privacyView Настройки приватности просмотра заметки:
 * all-всем пользователям;
 * friends-друзьям текущего пользователя;
 * friends_of_friends / friends_of_friends_only-Доступно друзьям и друзьям друзей /
 * друзьям друзей текущего пользователя (friends_of_friends_only появился с версии 5.32);
 * nobody / only_me-Недоступно никому / доступно только мне;
 * list{list_id}-Доступно друзьям текущего пользователя из списка с идентификатором {list_id};
 * {user_id}-Доступно другу с идентификатором {user_id};
 * list{list_id}-Недоступно друзьям текущего пользователя из списка с идентификатором {list_id};
 * {user_id}-Недоступно другу с идентификатором {user_id}.
 * @property privacyComment Настройки приватности комментирования заметки:
 * all – всем пользователям;
 * friends – друзьям текущего пользователя;
 * friends_of_friends / friends_of_friends_only – Доступно друзьям и друзьям друзей /
 * друзьям друзей текущего пользователя (friends_of_friends_only появился с версии 5.32);
 * nobody / only_me – Недоступно никому / доступно только мне;
 * list{list_id} – Доступно друзьям текущего пользователя из списка с идентификатором {list_id};
 * {user_id} – Доступно другу с идентификатором {user_id};
 * list{list_id} – Недоступно друзьям текущего пользователя из списка с идентификатором {list_id};
 * {user_id} – Недоступно другу с идентификатором {user_id}.
 * @property isDelete была ли удалена заметка.
 * @property comments карта с комментариями к заметке (ID > объект комментария).
 * @constructor Creates a new note.
 *
 */
data class Note(

    val nId: Long = 0,
    var ownerId: Long,
    var title: String,
    var text: String,
    val date: Long,
    var totalComments: Int = 0,
    var readComments: Int = 0,
    var viewUrl: String = "",
    var privacyView: String = "friends",
    var canComment: Boolean = true,
    var privacy: Int = 1,
    var textWiki: String = "",
    var commentPrivacy: Int = 1,
    var privacyComment: String = "friends",
    var isDelete: Boolean = false,
    var comments: MutableSet<Long> = mutableSetOf(), //ID комментариев к заметке
) {


    data class Comment(
        val id: Long,
        val ownerId: Long,
        val noteID: Long,
        val noteOwnerId: Long,
        val date: Long,
        var message: String,
        var replyTo: Long = 0,
        var isDelete: Boolean = false, //пометка удаления комментария
    )
}

object WallService { //Singletone

    private var pastPostId: Long = 0
    private var posts = emptyArray<Post>() //создаем массив для хранения постов
    private var comments = emptyArray<Comment>() //создаем массив для хранения комментариев к постам

    /** Post's functions */
    fun add(post: Post): Post {
        val postWId = post.copy(generatePostId())
        println("postWId: ${postWId.id}")
        posts += postWId // добавляем в массив и возвращаем последний элемент.
        return posts.last()

    }

    private fun generatePostId(): Long { //Генерируем ID поста: дата + случайное число
        pastPostId = if (pastPostId < 1) System.currentTimeMillis() else pastPostId + 1
        return pastPostId
    }

    fun update(post: Post): Boolean { //Обновление записи

        for ((index, postF) in posts.withIndex()) {
            if (postF.id == post.id) {
                posts[index] = post
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
    }

    /** Post's comments functions */
    fun createComment(postId: Long, comment: Comment): Comment {


        for (postF in posts) {
            if (postF.id == postId) {

                comments += comment.copy();
                return comments.last()
            }
        }
        throw PostNotFoundException(postId)

    }

}

object NoteService { //Singletone

    private var notes: MutableMap<Long, Note> = mutableMapOf() // мапа для хранения заметок
    private var comments: MutableMap<Long, Note.Comment> = mutableMapOf() // мапа для хранения комментариев

    /**
     * Generate ID - генерация УИД для заметок/комментов
     *
     * @param isNote генерировать ID для заметки
     * @return уникальный идентификатор
     */
    private fun generateID(isNote: Boolean = false): Long {
        return if (isNote) {
            System.currentTimeMillis() + notes.size
        } else {
            System.currentTimeMillis() + comments.size
        }
    }

    /**
     * Add - добавление новой заметки
     *
     * @param ownerId
     * @param title
     * @param text
     * @param privacy
     * @param commentPrivacy
     * @param privacyView
     * @param privacyComment
     * @return ID добавленной заметки.
     */
    fun add(
        ownerId: Long,
        title: String,
        text: String,
        privacy: Int = 1,
        commentPrivacy: Int = 1,
        privacyView: String = "friends",
        privacyComment: String = "friends",
    ): Long {
        val newNote = Note(
            generateID(true),
            ownerId,
            title,
            text,
            System.currentTimeMillis(),
            privacy = privacy,
            commentPrivacy = commentPrivacy,
            privacyView = privacyView,
            privacyComment = privacyComment,
        )

        notes.put(newNote.nId, newNote)
        return newNote.nId
    }

    /**
     * Delete - удаление заметки
     *
     * @param noteId
     * @return 1 - успешно удалена, -1 если заметка не найдена.
     */
    fun delete(noteId: Long): Int {
        val fNote: Note
        return try {
            fNote = notes.getValue(noteId)
            fNote.isDelete = true
            notes.replace(fNote.nId, fNote.copy())
            1
        } catch (e: NoSuchElementException) {
            -1
        }

    }

    /**
     * Edit - редактирование заметки
     *
     * @param noteId
     * @param title
     * @param text
     * @param privacy
     * @param commentPrivacy
     * @param privacyView
     * @param privacyComment
     * @return 1 - успешно изменена, -1 не передан ни один параметр для изменения, -2 если заметка не найдена.
     */
    fun edit(
        noteId: Long, title: String, text: String, privacy: Int, commentPrivacy: Int, privacyView: String,
        privacyComment: String
    ): Int {

        val fNote: Note
        var noteChanged = false
        return try {
            fNote = notes.getValue(noteId)
            if (title.isNotEmpty()) {
                fNote.title = title
                noteChanged = true
            }
            if (text.isNotEmpty()) {
                fNote.text = text
                noteChanged = true
            }
            if (privacy >= 0) {
                fNote.privacy = privacy
                noteChanged = true
            }
            if (commentPrivacy >= 0) {
                fNote.commentPrivacy = commentPrivacy
                noteChanged = true
            }
            if (privacyView.isNotEmpty()) {
                fNote.privacyView = privacyView
                noteChanged = true
            }
            if (privacyComment.isNotEmpty()) {
                fNote.privacyComment = privacyComment
                noteChanged = true
            }
            if (noteChanged) {
                notes.replace(fNote.nId, fNote.copy())
                1
            } else {
                throw IllegalArgumentException()
            }
        } catch (e: NoSuchElementException) {
            -2
        } catch (e: IllegalArgumentException) {
            -1
        }

    }

    /**
     * Get получение списка заметок из массива ID
     *
     * @param noteIds
     * @return Set с заметками или пустой Set, если заметок нет.
     */
    fun get(noteIds: Array<Long>): Set<Note> { // offset, sort,  count, userId: Long = -1

        val findNotes: MutableSet<Note> = mutableSetOf()

        for (noteId in noteIds) {
            try {
                val fNote: Note = notes.getValue(noteId)
                if (!fNote.isDelete) {
                    findNotes.add(fNote)
                } else {
                    throw NoSuchElementException()
                }
            } catch (e: NoSuchElementException) {
                println("Заметка с ID $noteId не найдена.")
            }
        }

        return findNotes
    }

    /**
     * Get by id - получение заметки по ID
     *
     * @param noteId
     * @return объект Note или null
     */
    fun getById(noteId: Long): Note? { // ownerId: , needWiki: T2
        return try {
            val fNote: Note = notes.getValue(noteId)
            if (fNote.isDelete) { //Удаленные не возвращаем
                throw NoSuchElementException()
            } else {
                fNote
            }
        } catch (e: NoSuchElementException) {
            println("Заметка с ID $noteId не найдена.")
            null
        }

    }

    /**
     * Create comment - добавление коммента к заметке
     *
     * @param noteId
     * @param ownerId
     * @param message
     * @return ID комментария или -1 (заметка не найдена)
     */

    fun createComment(noteId: Long, ownerId: Long, message: String): Long { // guid reply_to
        val note: Note

        return try {
            note = notes.getValue(noteId)
            val nComent: Note.Comment = Note.Comment(
                generateID(),
                ownerId,
                noteId,
                note.ownerId,
                System.currentTimeMillis(),
                message,
            )
            comments.put(nComent.id, nComent.copy())
            note.comments.add(nComent.id)
            notes.replace(note.nId, note.copy())
            nComent.id
        } catch (e: NoSuchElementException) {
            println("Заметка с ID $noteId не найдена. Добавление комментария невозможно.")
            -1
        }
    }

    /**
     * Delete comment удаление комментария у заметки
     *
     * @param commentId
     * @return 1 (успешно удалён), -1 (комментарий не найден)
     */
    fun deleteComment(commentId: Long): Int { // ownerId: Long
        val fComment: Note.Comment
        return try {
            fComment = comments.getValue(commentId)
            fComment.isDelete = true
            comments.replace(fComment.id, fComment.copy())
            1
        } catch (e: NoSuchElementException) {
            -1
        }

    }

    /**
     * Edit comment - редактирование комментария
     *
     * @param commentId
     * @param message
     * @return 1 (успешно отредактирован), -1 (пустое сообщение/мин длина < 2),
     * -2 (комментарий не найден)
     */
    fun editComment(commentId: Long, message: String): Int { //ownerId: Long = 0

        val fComment: Note.Comment
        return try {
            if (message.isEmpty() || message.length < 2) {
                throw IllegalArgumentException()
            }
            fComment = comments.getValue(commentId)
            if (fComment.isDelete) {
                throw NoSuchElementException()
            }
            fComment.message = message
            comments.replace(fComment.id, fComment.copy())
            1

        } catch (e: NoSuchElementException) {
            -2
        } catch (e: IllegalArgumentException) {
            -1
        }
    }

    /**
     * Get comments - получение всех комментариев заметки (без удаленных)
     *
     * @param noteId
     * @return Set с комментариями или null (заметка не найдена)
     */
    fun getComments(noteId: Long): Set<Note.Comment>? { // sort, offset, count ownerId: Long = 0
        val note: Note
        val findComments: MutableSet<Note.Comment> = mutableSetOf()
        try {
            note = notes.getValue(noteId)
            if (note.isDelete) {
                throw NoSuchElementException()
            }
        } catch (e: NoSuchElementException) {
            println("Заметка с ID $noteId не найдена.")
            return null
        }

        for (commentId in note.comments) {
            try {
                val fComment: Note.Comment = comments.getValue(commentId)
                if (!fComment.isDelete) {
                    findComments.add(fComment.copy())
                } else {
                    throw NoSuchElementException()
                }
            } catch (_: NoSuchElementException) {
                continue
            }
        }

        return findComments
    }

    /**
     * Restore comment - восстановление удаленного комментария
     *
     * @param commentId
     * @return 1 (успешно восстановлен), -1 (не найден)
     */
    fun restoreComment(commentId: Long): Int { //ownerId: Long = 0
        val fComment: Note.Comment
        return try {
            fComment = comments.getValue(commentId)
            fComment.isDelete = false
            comments.replace(fComment.id, fComment.copy())
            1
        } catch (e: NoSuchElementException) {
            -1
        }
    }
}

fun main() {
    val servicePost = WallService
    val serviceNote = NoteService

    //Attachments
    val attachments = Array<Attachments>(2) {
        ImageAttachment(
            id = 223232323,
            date = 123112312321,
            image = ImageAttachment.Image(
                width = 1600,
                height = 800,
                sizes = Array(1) {
                    ImageAttachment.ImageSize(
                        "m",
                        "https://pp.vk.me/c633825/v633825034/736e/SKfi-9SeR0I.jpg",
                        130,
                        87
                    )
                },
                albumId = 43534534534,
                ownerId = 5454545544554,
                date = 123112312321,
                userId = 435345345345
            )
        )
    }
    attachments.set(
        1,
        ImageAttachment(
            id = 223232324,
            date = 123112312321,
            image = ImageAttachment.Image(
                date = 123112312321,
                albumId = 435134534534,
                ownerId = 5454545544554,
                userId = 435345345345,
                sizes = Array(1) {
                    ImageAttachment.ImageSize(
                        "m",
                        "https://pp.vk.me/c633825/v633825034/73e4565e/SKfi-9SeR0I.jpg",
                        131,
                        88
                    )
                },
                width = 1600,
                height = 800
            )
        )
    )

    var post = servicePost.add(Post(ownerId = 112457, fromId = 123456, text = "Test post", attachments = attachments))
    post = post.copy(text = "Testovyi post")
    servicePost.update(post)

    /*
        val ownerId = 23432432423423432

        val newNoteId0 = serviceNote.add(ownerId, "test title0", "txt0") //создаем заметку
        val newNoteId = serviceNote.add(ownerId, "test title1", "txt") //создаем заметку
        println("new note $newNoteId")

        var newComment = serviceNote.createComment(newNoteId, ownerId, "first comment") // создаем коммент к заметке
        println("new Comment1 $newComment")
        newComment = serviceNote.createComment(newNoteId, ownerId, "second comment") // создаем ещё коммент к заметке
        println("new Comment2 $newComment")

        val newComment1 =
            serviceNote.createComment(111321322323, ownerId, "second 1 comment") // создаем коммент к заметке c несущ ID
        println("new Comment for 111321322323 note =  $newComment1")

        //Вывод заметок по ID <array>
        val notesIds: Array<Long> = arrayOf(newNoteId0, newNoteId)
        var notes = serviceNote.get(notesIds) // получаем комменты к заметке
        if (notes.isNotEmpty()) {
            val fComments = StringBuilder()
            fComments.append("Найденные заметке: \n")
            for (note in notes) {
                fComments.append("${note.title}: ${note.text}\n")
            }

            println(fComments.toString())
        }
        //Вывод заметок по ID с пустым <array>
        notes = serviceNote.get(emptyArray())
        println("find notes with emptyArray = $notes")

        //Вывод заметки по ID
        var note: Note? = serviceNote.getById(newNoteId0)
        println("find note by ID  = $note")
        //Вывод заметки по ID с несущ ID
        note = serviceNote.getById(455454545454)
        println("find note by fake ID  = $note")

        //Вывод всех комментариев к заметке
        var noteComments = serviceNote.getComments(newNoteId)
        if (!noteComments.isNullOrEmpty()) {
            val allComments = StringBuilder()
            allComments.append("Все комментарии к заметке: \n")
            for (comment in noteComments) {
                allComments.append("${comment.message} \n")
            }

            println(allComments.toString())
        }
        //Вывод всех комментариев к заметке с несущ ID
        noteComments = serviceNote.getComments(324234234324234234)
        println("comments for fake Note ID: $noteComments")


        //редактирование заметки
        var editNoteResult = serviceNote.edit(newNoteId0, "changed title", "", -1, -1, "", "")
        println("result edit note: $editNoteResult")
        //редактирование заметки с незап парамтерами
        editNoteResult = serviceNote.edit(newNoteId0, "", "", -1, -1, "", "")
        println("result edit note: $editNoteResult")
        //редактирование заметки с несущ ID
        editNoteResult = serviceNote.edit(21314324234234, "changed 1title", "", -1, -1, "", "")
        println("result edit note with fake ID: $editNoteResult")

        //Редактирование комментария
        var editCommentResult = serviceNote.editComment(newComment, "text changed comment")
        println("result edit comment: $editCommentResult")
        //Редактирование комментария с незап. парам.
        editCommentResult = serviceNote.editComment(newComment, "")
        println("result edit comment: $editCommentResult")
        //Редактирование комментария с несущ ID
        editCommentResult = serviceNote.editComment(234324234324, "fake test")
        println("result edit comment with fake ID: $editCommentResult")

        //Удаление заметки
        var deleteNoteResult = serviceNote.delete(newNoteId)
        println("attempt to delete Note result =  $deleteNoteResult")
        //Удаление заметки с несущ ID
        deleteNoteResult = serviceNote.delete(2131231231231)
        println("attempt to delete Note with fake ID. result =  $deleteNoteResult")
        //удаление комментария
        var deleteCommentResult = serviceNote.deleteComment(newComment)
        println("delete comment. result =  $deleteCommentResult")
        //удаление комментария с несуш ID
        deleteCommentResult = serviceNote.deleteComment(454545454545454)
        println("attempt to delete comment with fake ID. result =  $deleteCommentResult")

        //Восстановление комментария
        var restoreCommentResult = serviceNote.restoreComment(newComment)
        println("recover comment. result =  $restoreCommentResult")
        //Восстановление комментария с несущ ID
        restoreCommentResult = serviceNote.restoreComment(445454545454545454)
        println("attempt to recover comment with fake ID. result =  $restoreCommentResult")

        */
}