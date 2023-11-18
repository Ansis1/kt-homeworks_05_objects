package ru.neotology

import java.lang.Exception

class PostNotFoundException(postId: Long) : Exception() {
    val idP: Long = postId
    override fun getLocalizedMessage(): String {
        return "Не удалось найти пост с ID: $idP"
    }
}