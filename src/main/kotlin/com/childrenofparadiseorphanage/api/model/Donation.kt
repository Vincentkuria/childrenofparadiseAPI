package com.childrenofparadiseorphanage.api.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.sql.Timestamp
import java.time.Instant

@Entity
@Table(name = "donations")
data class Donation(
//    TODO validate
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null,

    @field:NotBlank(message = "phone number is required")
    var name: String,

    @field:Pattern( regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",message = "a valid email is required")
    var email: String,

    @field:NotBlank(message = "phone number is required")
    @field:Size(message = "valid phone number is required", min = 6,max=20)
    var phone: String,

    var amount: Long,

    @Column(updatable = false)
    var reference: String?=null,

    var status: Status?= Status.PENDING,

    @Column(updatable = false, insertable = false)
    var createdAt: Timestamp?=null,
    var updatedAt: Timestamp = Timestamp.from(Instant.now())
)

enum class Status{
    ABANDONED,
    FAILED,
    ONGOING,
    PENDING,
    PROCESSING,
    QUEUED,
    REVERSED,
    SUCCESS,
}
