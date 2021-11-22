package com.wolodja.slow_and_calm_cinema.showing

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/v1/showing")
class ShowingController(private val showingService: ShowingService) {

    @GetMapping("/movie/{movieId}")
    fun getById(@PathVariable("movieId") movieId: UUID): ResponseEntity<List<ShowingDto>> {
        val result = showingService.getActualShowings(movieId)
        return ResponseEntity.status(HttpStatus.OK).body(result)
    }

    @PostMapping
    fun createShowing(@RequestBody showing: ShowingDto): ResponseEntity<Any> {
        showingService.saveShowing(showing)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping("/{showingId}")
    fun updateShowing(@PathVariable("showingId") showingId: UUID, @RequestBody showing: ShowingDto): ResponseEntity<Any> {
        showingService.updateShowing(showingId, showing)
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}