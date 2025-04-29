package com.childrenofparadiseorphanage.api.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.sql.Timestamp


@Entity
@Table(name = "messages")
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    val id: Long,

    @field:NotBlank(message = "name field is required")
    var name: String,

    @field:Pattern( regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",message = "a valid email is required")
    var email: String,

    @field:NotBlank(message = "phone number is required")
    @field:Size(message = "valid phone number is required", min = 6,max=20)
    var phone: String,

    @field:NotBlank(message = "message is required")
    var message: String,

    var markRead: Boolean?=false,

    @Column(insertable = false, updatable = false)
    val createdAt: Timestamp?,

)
