package com.wolodja.slow_and_calm_cinema

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SecurityScheme(
    name = "Basic Authentication",
    type = SecuritySchemeType.HTTP,
    scheme = "basic"
)
@OpenAPIDefinition(
    info = Info(title = "Slow nad Calm cinema api", version = "v1")
)
@SpringBootApplication
class SlowAndCalmCinemaApplication

fun main(args: Array<String>) {
    runApplication<SlowAndCalmCinemaApplication>(*args)
}
