package com.wolodja.slow_and_calm_cinema.movie

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/movie")
class MovieController(val movieProvider: MovieProvider) {

    @Operation(summary = "Get showings for movie id")
    @ApiResponses(
        value = [
            ApiResponse(
                description = "List of showings",
                responseCode = "200",
                content = [Content(mediaType = "application/json", array = ArraySchema(schema = Schema(implementation = MovieDto::class)))]
            ),
            ApiResponse(description = "Movie not exists", responseCode = "404", content = [Content()])
        ]
    )
    @GetMapping("/{movieId}")
    fun getById(@PathVariable("movieId") movieId: UUID): ResponseEntity<MovieDto> {
        val result = movieProvider.getMovie(movieId)
        return ResponseEntity.status(HttpStatus.OK).body(result)
    }

}