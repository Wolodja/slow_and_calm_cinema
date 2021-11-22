package com.wolodja.slow_and_calm_cinema.movie

import com.wolodja.slow_and_calm_cinema.comon.MovieNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

@Service
@Transactional(readOnly = true)
class MovieProvider(
    val movieRepository: MovieRepository
) {
    companion object {
        const val MOVIE_WITHOUT_VOTES_RATING = "Movie has no votes."
    }

    fun getMovie(movieId: UUID): MovieDto {
        return movieToMovieDto(getMovieEntity(movieId))
    }

    fun getMovieEntity(movieId: UUID): Movie {
        return movieRepository.findByIdOrNull(movieId) ?: throw MovieNotFoundException("Movie with id $movieId does not exist")
    }

    private fun movieToMovieDto(movie: Movie): MovieDto {
        val votes = movie.votes
        val rating = if (votes.isEmpty()) {
            MOVIE_WITHOUT_VOTES_RATING
        } else {
            BigDecimal(votes.map { it.rating }.average()).setScale(1, RoundingMode.HALF_EVEN)
        }

        return MovieDto(
            id = movie.id,
            title = movie.title,
            releaseDate = movie.releaseDate,
            runtime = movie.runtime,
            imdbRating = movie.imdbRating,
            description = movie.description,
            director = movie.director,
            rating = rating
        )
    }
}