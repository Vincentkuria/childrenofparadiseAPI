package com.childrenofparadiseorphanage.api.service

import com.childrenofparadiseorphanage.api.model.Message
import com.childrenofparadiseorphanage.api.repository.MessageRepository
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.Optional

@Service
class MessageService(private val db: MessageRepository, private val entityManager: EntityManager) {

    @Transactional
    fun saveMessage(message: Message): Message {
        val thisMessage=db.save(message)
        entityManager.refresh(thisMessage)
        return thisMessage
    }

    fun getAllMessages()= db.findAll().toList()

    fun getUnreadMessages(): List<Message> = db.findByMarkReadFalse()

    fun getReadMessages(): List<Message> = db.findByMarkReadTrue()

    fun updateMarkRead(id: Long, isRead: Boolean): Any {
        val updatable = db.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,"Message with provided id not found")
        }
        updatable.markRead=isRead
        return db.save(updatable)
    }

    fun deleteMessage(id: Long)=db.deleteById(id)

}