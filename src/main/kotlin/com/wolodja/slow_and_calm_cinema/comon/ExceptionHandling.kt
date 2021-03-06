package com.wolodja.slow_and_calm_cinema.comon

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandling {

    @ExceptionHandler(value = [MovieNotFoundException::class])
    fun exception(exception: MovieNotFoundException): ResponseEntity<Any> {
        return ResponseEntity(
            exception.message,
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun exception(exception: IllegalArgumentException): ResponseEntity<Any> {
        return ResponseEntity(
            exception.message,
            HttpStatus.BAD_REQUEST
        )
    }
}

class MovieNotFoundException(message: String?) : RuntimeException(message)