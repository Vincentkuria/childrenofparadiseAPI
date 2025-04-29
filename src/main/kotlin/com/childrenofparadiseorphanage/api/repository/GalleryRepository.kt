package com.childrenofparadiseorphanage.api.repository

import com.childrenofparadiseorphanage.api.model.Gallery
import org.springframework.data.domain.Sort
import org.springframework.data.repository.CrudRepository

interface GalleryRepository: CrudRepository<Gallery, Long>{
    fun findAll(sort: Sort): List<Gallery>
}