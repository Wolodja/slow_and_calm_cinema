package com.wolodja.slow_and_calm_cinema.movie

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class MovieProvider(
    val movieRepository: MovieRepository
) {

    fun getMovieById(movieId: UUID): MovieDto {
        val movie = movieRepository.findByIdOrNull(movieId) ?: throw MovieNotFoundException("Movie with id $movieId does not exist")
        return movieToMovieDto(movie)
    }


    private fun movieToMovieDto(movie: Movie): MovieDto {
        return MovieDto(
            id = movie.id,
            title = movie.title,
            releaseDate = movie.releaseDate,
            runtime = movie.runtime,
            imdbRating = movie.imdbRating,
            description = movie.description,
            director = movie.director
        )
    }
}