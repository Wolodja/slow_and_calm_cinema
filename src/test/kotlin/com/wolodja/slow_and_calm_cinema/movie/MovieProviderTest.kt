package com.wolodja.slow_and_calm_cinema.movie

import com.wolodja.slow_and_calm_cinema.SampleDataProvider
import com.wolodja.slow_and_calm_cinema.comon.MovieNotFoundException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal
import java.util.*

internal class MovieProviderTest {

    private val movieRepository: MovieRepository = mockk()

    private val movieProvider = MovieProvider(movieRepository)


    @Test
    fun `should return movie without votes`() {

        //given
        val movieId = UUID.randomUUID()
        every { movieRepository.findByIdOrNull(movieId) } returns SampleDataProvider.movie

        //when
        val movieDto = movieProvider.getMovie(movieId)

        //then
        verify(exactly = 1) { movieRepository.findByIdOrNull(movieId) }
        assert(movieDto.title == SampleDataProvider.movie.title)
        assert(movieDto.rating == MovieProvider.MOVIE_WITHOUT_VOTES_RATING)
    }

    @Test
    fun `should return movie with votes`() {

        //given
        val movieId = UUID.randomUUID()
        every { movieRepository.findByIdOrNull(movieId) } returns SampleDataProvider.movieWithVotes

        //when
        val movieDto = movieProvider.getMovie(movieId)

        //then
        verify(exactly = 1) { movieRepository.findByIdOrNull(movieId) }
        assert(movieDto.title == SampleDataProvider.movie.title)
        assert(movieDto.rating is BigDecimal)
    }

    @Test
    fun `should throw error when movie not found`() {

        //given
        val movieId = UUID.randomUUID()
        every { movieRepository.findByIdOrNull(movieId) } returns null

        //when
        val exception = assertThrows(MovieNotFoundException::class.java) {
            movieProvider.getMovie(movieId)
        }

        //then
        verify(exactly = 1) { movieRepository.findByIdOrNull(movieId) }
        assert(exception.message == "Movie with id $movieId does not exist")
    }

}