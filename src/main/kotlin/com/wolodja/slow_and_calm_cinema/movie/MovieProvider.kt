package com.wolodja.slow_and_calm_cinema.movie

import com.wolodja.slow_and_calm_cinema.comon.MovieNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class MovieProvider(
    val movieRepository: MovieRepository
) {
    fun getMovie(movieId: UUID): MovieDto {
        return movieToMovieDto(getMovieEntity(movieId))
    }

    fun getMovieEntity(movieId: UUID): Movie {
        return movieRepository.findByIdOrNull(movieId) ?: throw MovieNotFoundException("Movie with id $movieId does not exist")
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