package com.wolodja.slow_and_calm_cinema.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/viewer")
class UserController(val userService: UserService) {


    @PostMapping("/register")
    fun createViewer(@RequestBody user: UserDto): ResponseEntity<Any> {
        val registeredAccount = userService.registerNewViewerAccount(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredAccount)
    }

}