package com.childrenofparadiseorphanage.api.config.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.Instant

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, Any?>> {
        val errors=ex.bindingResult.fieldErrors.map { "${it.field}:${it.defaultMessage}" }
        val body= mapOf(
            "timestamp" to Instant.now(),
            "status" to HttpStatus.BAD_REQUEST.value(),
            "errors" to errors
        )

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }
}