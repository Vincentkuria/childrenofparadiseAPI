package com.childrenofparadiseorphanage.api.repository

import com.childrenofparadiseorphanage.api.model.Message
import org.springframework.data.repository.CrudRepository

interface MessageRepository: CrudRepository<Message, Long> {

    fun findByMarkReadFalse(): List<Message>

    fun findByMarkReadTrue(): List<Message>
}