package com.childrenofparadiseorphanage.api.service

import com.childrenofparadiseorphanage.api.model.Message
import com.childrenofparadiseorphanage.api.repository.MessageRepository
import org.springframework.stereotype.Service

@Service
class MessageService(private val db: MessageRepository) {

    fun saveMessage(message: Message): Message = db.save(message)

    fun getAllMessages()= db.findAll().toList()

    fun getUnreadMessages(): List<Message> = db.findByMarkReadFalse()

    fun getReadMessages(): List<Message> = db.findByMarkReadTrue()

    fun updateMarkRead(id: Long, isRead: Boolean) {
        var updatable= db.findById(id).orElseThrow()
        updatable.markRead=isRead
        db.save(updatable)
    }

    fun deleteMessage(id: Long)=db.deleteById(id)

}