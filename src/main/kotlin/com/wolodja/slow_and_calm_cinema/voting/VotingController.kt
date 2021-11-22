package com.wolodja.slow_and_calm_cinema.voting

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/voting")
class VotingController(private val votingService: VotingService) {


    @PostMapping
    fun createShowing(@RequestBody voting: VotingDto): ResponseEntity<Any> {
        votingService.saveVoting(voting)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}