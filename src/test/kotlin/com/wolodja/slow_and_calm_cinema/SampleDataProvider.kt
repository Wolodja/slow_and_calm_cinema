package com.wolodja.slow_and_calm_cinema

import com.wolodja.slow_and_calm_cinema.movie.Movie
import com.wolodja.slow_and_calm_cinema.showing.Showing
import com.wolodja.slow_and_calm_cinema.showing.ShowingDto
import com.wolodja.slow_and_calm_cinema.user.User
import com.wolodja.slow_and_calm_cinema.voting.Voting
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class SampleDataProvider private constructor() {

    companion object {
        val viewerJohn = User(username = "john", password = "Smith", role = "VIEWER")
        val viewerBella = User(username = "bella", password = "Cool", role = "VIEWER")
        val movie = Movie(
            imdbId = "tt123",
            title = "Fast & Furious",
            description = "Good movie",
            director = "John",
            runtime = "105 min",
            releaseDate = null,
            imdbRating = BigDecimal.ONE
        )
        val vote1 = Voting(
            movie = movie,
            rating = 3,
            user = viewerJohn
        )
        val vote2 = Voting(
            movie = movie,
            rating = 4,
            user = viewerBella
        )
        val movieWithVotes = Movie(
            imdbId = "tt123",
            title = "Fast & Furious",
            description = "Good movie",
            director = "John",
            runtime = "105 min",
            releaseDate = null,
            imdbRating = BigDecimal.ONE,
            votes = listOf(vote1, vote2)
        )
        val showing = Showing(
            movie = movie,
            time = LocalDateTime.now().plusDays(1),
            priceFrom = BigDecimal.ONE,
            priceTo = BigDecimal.TEN
        )
        val showingDto = ShowingDto(
            id = UUID.randomUUID(),
            movieId = UUID.randomUUID(),
            time = LocalDateTime.now().plusDays(1),
            priceFrom = BigDecimal.ONE,
            priceTo = BigDecimal.TEN
        )
        val showingDtoWithInvalidTime = ShowingDto(
            id = UUID.randomUUID(),
            movieId = UUID.randomUUID(),
            time = LocalDateTime.now().minusDays(1),
            priceFrom = BigDecimal.ONE,
            priceTo = BigDecimal.TEN
        )
        val showingDtoWithNegativePrice = ShowingDto(
            id = UUID.randomUUID(),
            movieId = UUID.randomUUID(),
            time = LocalDateTime.now().plusDays(1),
            priceFrom = BigDecimal.valueOf(-1L),
            priceTo = BigDecimal.TEN
        )
        val showingDtoWithInvalidPrices = ShowingDto(
            id = UUID.randomUUID(),
            movieId = UUID.randomUUID(),
            time = LocalDateTime.now().plusDays(1),
            priceFrom = BigDecimal.TEN,
            priceTo = BigDecimal.ZERO
        )
    }
}