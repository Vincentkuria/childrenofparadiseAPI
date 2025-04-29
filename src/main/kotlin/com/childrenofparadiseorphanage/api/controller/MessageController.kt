package com.childrenofparadiseorphanage.api.controller

import com.childrenofparadiseorphanage.api.model.Message
import com.childrenofparadiseorphanage.api.service.MessageService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/messages")
class MessageController(private val service: MessageService) {

    @PostMapping
    fun storeMassage(@RequestBody @Valid message: Message) = service.saveMessage(message)

    @GetMapping("/all")
    fun allMessages() = service.getAllMessages()

    @GetMapping("/unread")
    fun unreadMessages() = service.getUnreadMessages()

    @GetMapping("/read")
    fun readMessages() = service.getReadMessages()

    @PatchMapping
    fun updateMarkRead(@RequestParam id: Long, @RequestParam isRead: Boolean) = service.updateMarkRead(id,isRead)

    @DeleteMapping("/{id}")
    fun deleteMessage(@PathVariable id: Long) = service.deleteMessage(id)


}