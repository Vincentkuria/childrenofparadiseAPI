package com.childrenofparadiseorphanage.api.controller

import com.childrenofparadiseorphanage.api.model.Gallery
import com.childrenofparadiseorphanage.api.service.GalleryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

data class CreateRequest(val image: MultipartFile, val title: String)

@RestController
@RequestMapping("/gallery")
class GalleryController(private val service: GalleryService) {

    @GetMapping
    fun getAllFromGallery()=service.getAllFromGallery()

    @PostMapping
    fun createGalleryItem(@ModelAttribute createRequest: CreateRequest): Any{
        if (createRequest.image.isEmpty) {
            return ResponseEntity.badRequest().body("Image is required")
        }
        if (createRequest.title.length>255){
            return ResponseEntity.badRequest().body("title/description text too long, keep it <255")
        }
        /**
         * service.uploadImage(image) returns a relative image path, to get the full url
         * do <your domain> + relativeImagePath, this makes sure that even after changing server
         * there will be no need to rename the database image urls
         */
        val relativeImagePath=service.uploadImage(createRequest.image)
        return service.saveToGallery(Gallery(image = relativeImagePath, title = createRequest.title))

    }

    @DeleteMapping("/{id}")
    fun deleteGalleryItem(@PathVariable id: Long)=service.deleteFromGallery(id)
}