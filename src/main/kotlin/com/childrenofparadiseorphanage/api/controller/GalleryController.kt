package com.childrenofparadiseorphanage.api.controller

import com.childrenofparadiseorphanage.api.model.Gallery
import com.childrenofparadiseorphanage.api.service.GalleryService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/gallery")
class GalleryController(private val service: GalleryService) {

    @GetMapping
    fun getAllFromGallery()=service.getAllFromGallery()

    @PostMapping
    fun createGalleryItem(@RequestBody gallery: Gallery) = service.saveToGallery(gallery)

    @DeleteMapping("/{id}")
    fun deleteGalleryItem(@PathVariable id: Long)=service.deleteFromGallery(id)
}