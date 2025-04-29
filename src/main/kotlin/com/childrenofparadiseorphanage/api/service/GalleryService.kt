package com.childrenofparadiseorphanage.api.service

import com.childrenofparadiseorphanage.api.model.Gallery
import com.childrenofparadiseorphanage.api.repository.GalleryRepository
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID
import kotlin.io.path.Path

@Service
class GalleryService(private val db: GalleryRepository, private val entityManager: EntityManager) {

    fun getAllFromGallery()=db.findAll(Sort.by(Sort.Direction.DESC,"createdAt")).toList()

    @Transactional
    fun saveToGallery(gallery: Gallery): Gallery {
        val mySavedGalleryItem=db.save(gallery)
        entityManager.refresh(mySavedGalleryItem)
        return mySavedGalleryItem
    }

    fun deleteFromGallery(id: Long){
        val galleryItem: Gallery?=db.findById(id).orElse(null)
        galleryItem?.let {
            val deleted=deleteImage(galleryItem.image)
            print("$deleted image is deleted from drive")
        }

       return db.deleteById(id)
    }

    fun uploadImage(image : MultipartFile):String {
        val uniqueName = UUID.randomUUID().toString()+"-"+image.originalFilename
        val uploadDir= Paths.get("uploads/")
        if(!Files.exists(uploadDir)) Files.createDirectories(uploadDir)
        val relativePath="uploads/$uniqueName"
        image.transferTo(Paths.get(relativePath))
        return relativePath
    }

    fun deleteImage(relativePath: String): Boolean {
        try {
            val path = Paths.get(relativePath)
            return Files.deleteIfExists(path)
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
    }
}