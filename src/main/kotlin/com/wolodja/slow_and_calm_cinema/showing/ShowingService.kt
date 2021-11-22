package com.wolodja.slow_and_calm_cinema.showing

import com.wolodja.slow_and_calm_cinema.comon.Money
import com.wolodja.slow_and_calm_cinema.movie.MovieProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class ShowingService(
    val showingRepository: ShowingRepository,
    val movieProvider: MovieProvider
) {

    fun getActualShowings(movieId: UUID): List<ShowingDto>? {
        val movie = movieProvider.getMovieEntity(movieId)
        return showingRepository.getByMovieAndTimeIsAfter(movie, LocalDateTime.now()).map { showingToShowingDto(it) }
    }

    fun saveShowing(showingDto: ShowingDto) {
        validateShowing(showingDto)
        val movie = movieProvider.getMovieEntity(showingDto.movieId)
        val showing = Showing(movie, showingDto.time, Money.dollars(showingDto.priceFrom).amount, Money.dollars(showingDto.priceTo).amount)
        showingRepository.save(showing)
    }


    fun updateShowing(showingId: UUID, showingDto: ShowingDto) {
        validateShowing(showingDto)
        val showing = showingRepository.findByIdOrNull(showingId) ?: throw IllegalArgumentException("Provided showing does not exist.")
        showing.apply {
            time = showingDto.time
            priceFrom = showingDto.priceFrom
            priceTo = showingDto.priceTo
        }
        showingRepository.save(showing)
    }

    private fun showingToShowingDto(showing: Showing): ShowingDto {
        return ShowingDto(id = showing.id, movieId = showing.movie.id, time = showing.time, priceFrom = showing.priceFrom, priceTo = showing.priceTo)
    }

    private fun validateShowing(showingDto: ShowingDto) {
        if (showingDto.time.isBefore(LocalDateTime.now())) {
            throw IllegalArgumentException("Showing time can't be in past.")
        }
        if (showingDto.priceFrom < BigDecimal.ZERO || showingDto.priceTo < BigDecimal.ZERO || showingDto.priceFrom > showingDto.priceTo) {
            throw IllegalArgumentException("Invalid price provided.")
        }
    }

}