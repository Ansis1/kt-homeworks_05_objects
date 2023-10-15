package ru.neotology

import javax.swing.text.StyledEditorKit.BoldAction

/**
 * Пост
 *
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
    val attachments: Array<Attachments> = emptyArray(),
    val signerId: Int = 0,
    val copyHistory: Array<Post> = emptyArray(),
    val canPin: Boolean = false,
    val canDelete: Boolean = false,
    val canEdit: Boolean = false,
    val isPinned: Boolean = false,
    val markedAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val postponedId: Int = 0,
    val likes: Likes = Likes(),
    val comments: Comments = Comments()
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

        val count: Int = 0, //число пользователей, скопировавших запись;
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


    data class Attachments(
//TODO

        val type: String,
        // val dataType: <T>
    )


    data class Donut( //TODO Информация о записи VK Donut

        val isDonut: Boolean = false, /* запись доступна только платным подписчикам VK Donut */
        val paidDuration: Long = 0, /* время, в течение которого запись будет доступна только платным подписчикам VK Donut.*/
        val canPublishFreeCopy: Boolean = false, /* можно ли открыть запись для всех пользователей, а не только подписчиков VK Donut*/
        val editMode: String = "" /* информация о том, какие значения VK Donut можно изменить в записи - all or duration*/

    ) {
        class Placeholder { //TODO Заглушка для пользователей, которые не оформили подписку VK Donut. Отображается вместо содержимого записи.


        }

    }

}

object WallService { //Singletone

    private var posts = emptyArray<Post>() //создаем массив для хранения постов
    fun add(post: Post): Post {
        val postWId = post.copy(generatePostId())
        println("postWId: ${postWId.id}")
        posts += postWId // добавляем в массив и возвращаем последний элемент.
        return posts.last()


    }

    private fun generatePostId(): Long { //Генерируем ID поста: дата + случайное число

        return System.currentTimeMillis() + (0..50).random().toLong()

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

}

fun main() {
    val service = WallService
    var post = service.add(Post(ownerId = 112457, fromId = 123456, text = "Test post"))
    println("Original post: $post")
    post = post.copy(text = "Testovyi post")
    service.update(post)
    println("Change post: $post")
}