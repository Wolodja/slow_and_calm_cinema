package com.wolodja.slow_and_calm_cinema.showing

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/v1/showing")
class ShowingController(private val showingService: ShowingService) {

    @Operation(summary = "Get showings for movie id")
    @ApiResponses(
        value = [
            ApiResponse(
                description = "List of showings",
                responseCode = "200",
                content = [Content(mediaType = "application/json", array = ArraySchema(schema = Schema(implementation = ShowingDto::class)))]
            ),
            ApiResponse(description = "Movie not exists", responseCode = "404", content = [Content()])
        ]
    )
    @GetMapping("/movie/{movieId}")
    fun getById(@PathVariable("movieId") movieId: UUID): ResponseEntity<List<ShowingDto>> {
        val result = showingService.getActualShowings(movieId)
        return ResponseEntity.status(HttpStatus.OK).body(result)
    }

    @Operation(summary = "Create showing for movie", security = [SecurityRequirement(name = "Basic Authentication")])
    @ApiResponses(
        value = [
            ApiResponse(description = "Showing created", responseCode = "201", content = [Content()]),
            ApiResponse(description = "Invalid showing", responseCode = "400", content = [Content()]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [Content()]),
            ApiResponse(description = "User don't have permission to perform this action", responseCode = "403", content = [Content()]),
            ApiResponse(description = "Movie not exists", responseCode = "404", content = [Content()]),
        ]
    )
    @PostMapping
    fun createShowing(@RequestBody showing: ShowingDto): ResponseEntity<Any> {
        showingService.saveShowing(showing)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }


    @Operation(summary = "Update existing showing", security = [SecurityRequirement(name = "Basic Authentication")])
    @ApiResponses(
        value = [
            ApiResponse(description = "Showing updated", responseCode = "200", content = [Content()]),
            ApiResponse(description = "Invalid showing", responseCode = "400", content = [Content()]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [Content()]),
            ApiResponse(description = "User don't have permission to perform this action", responseCode = "403", content = [Content()])
        ]
    )
    @PutMapping("/{showingId}")
    fun updateShowing(@PathVariable("showingId") showingId: UUID, @RequestBody showing: ShowingDto): ResponseEntity<Any> {
        showingService.updateShowing(showingId, showing)
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}