package com.childrenofparadiseorphanage.api.service

import com.childrenofparadiseorphanage.api.model.Gallery
import com.childrenofparadiseorphanage.api.repository.GalleryRepository
import org.springframework.stereotype.Service

@Service
class GalleryService(private val db: GalleryRepository) {

    fun getAllFromGallery()=db.findAll().toList()

    fun saveToGallery(gallery: Gallery)=db.save(gallery)

    fun deleteFromGallery(id: Long)=db.deleteById(id)
}