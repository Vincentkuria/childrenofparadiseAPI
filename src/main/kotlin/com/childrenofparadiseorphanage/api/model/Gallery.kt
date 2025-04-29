package com.childrenofparadiseorphanage.api.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import java.sql.Timestamp

@Entity
@Table(name = "gallery")
data class Gallery(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=null,

    @field:NotBlank
    var image: String,

    @field:NotBlank
    var title: String,

    @Column(insertable = false, updatable = false)
    var createdAt: Timestamp?=null
)
