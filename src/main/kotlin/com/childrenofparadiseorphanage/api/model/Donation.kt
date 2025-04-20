package com.childrenofparadiseorphanage.api.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Timestamp
import java.time.Instant

@Entity
@Table(name = "donations")
data class Donation(
//    TODO validate
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    var email: String,
    var phone: String,
    @Column(updatable = false)
    var amount: Long,
    @Column(updatable = false)
    var reference: String?,
    var status: Boolean=false,//TODO use string for status &also change in db
    @Column(updatable = false)
    var createdAt: Timestamp?,
    var updatedAt: Timestamp = Timestamp.from(Instant.now())
)
