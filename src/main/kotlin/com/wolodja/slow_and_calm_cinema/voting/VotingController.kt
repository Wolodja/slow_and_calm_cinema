package com.wolodja.slow_and_calm_cinema.voting

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/voting")
class VotingController(private val votingService: VotingService) {


    @Operation(summary = "Create showing for movie", security = [SecurityRequirement(name = "Basic Authentication")])
    @ApiResponses(
        value = [
            ApiResponse(description = "Voting created", responseCode = "201", content = [Content()]),
            ApiResponse(description = "Invalid voting", responseCode = "400", content = [Content()]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [Content()]),
            ApiResponse(description = "User don't have permission to perform this action", responseCode = "403", content = [Content()]),
            ApiResponse(description = "Movie not exists", responseCode = "404", content = [Content()]),
        ]
    )
    @PostMapping
    fun createShowing(@RequestBody voting: VotingDto): ResponseEntity<Any> {
        votingService.saveVoting(voting)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}