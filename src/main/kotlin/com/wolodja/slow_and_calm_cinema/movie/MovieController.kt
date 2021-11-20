package com.wolodja.slow_and_calm_cinema.movie

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/movie")
class MovieController(
    val movieProvider: MovieProvider){

    @GetMapping("/{movieId}")
    fun getById(@PathVariable("movieId") movieId: UUID) : ResponseEntity<MovieDto>{
        val result = movieProvider.getMovie(movieId)
        return ResponseEntity.status(HttpStatus.OK).body(result)
    }

}