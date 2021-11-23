package com.wolodja.slow_and_calm_cinema.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/viewer")
class UserController(val userService: UserService) {

    @Operation(summary = "Register new viewer account. Provide only username and password.")
    @ApiResponses(
        value = [
            ApiResponse(description = "Viewer account created", responseCode = "201", content = [Content()]),
            ApiResponse(description = "Invalid user data", responseCode = "400", content = [Content()])
        ]
    )
    @PostMapping("/register")
    fun createViewer(@RequestBody user: UserDto): ResponseEntity<Any> {
        val registeredAccount = userService.registerNewViewerAccount(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredAccount)
    }

}