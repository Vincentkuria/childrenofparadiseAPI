package com.childrenofparadiseorphanage.api.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Timestamp


@Entity
@Table(name = "messages")
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    val id: Long,
    var name: String,
    var email: String,
    var phone: String,
    var message: String,
    var markRead: Boolean=false,
    @Column(insertable = false, updatable = false)
    val createdAt: Timestamp?,

)
