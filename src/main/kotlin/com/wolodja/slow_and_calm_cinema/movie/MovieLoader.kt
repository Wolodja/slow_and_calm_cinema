package com.wolodja.slow_and_calm_cinema.movie

import com.wolodja.slow_and_calm_cinema.client.OmdbMovieResponse
import com.wolodja.slow_and_calm_cinema.client.OmdbRestClient
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class MovieLoader(val movieRepository: MovieRepository) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(this.javaClass)

    val omdbRestClient: OmdbRestClient = OmdbRestClient()

    val movieIds = listOf("tt0232500", "tt0322259", "tt0463985", "tt1013752", "tt1596343", "tt1905041", "tt2820852", "tt4630562")

    override fun run(vararg args: String?) {
        if (movieRepository.count() == 0L) {
            loadMoviesFromOmdb()
        }
    }

    private fun loadMoviesFromOmdb() {
        movieIds.mapNotNull { omdbRestClient.getMovie(it) }
            .filter { isValidMovie(it) }
            .map {
                Movie(
                    title = it.Title,
                    imdbId = it.imdbID,
                    releaseDate = it.Released,
                    runtime = it.Runtime,
                    imdbRating = it.imdbRating,
                    director = it.Director,
                    description = it.Plot
                )
            }.forEach { movie ->
                movieRepository.save(movie)
                log.info("Movie with imdbID : " + movie.imdbId + " was fetched.")
            }
    }

    private fun isValidMovie(movieResponse: OmdbMovieResponse): Boolean {
        val rating = movieResponse.imdbRating
        return rating.precision() <= 2 && rating.scale() == 1 && rating.toDouble() >= 0 && rating.toDouble() <= 10
    }
}