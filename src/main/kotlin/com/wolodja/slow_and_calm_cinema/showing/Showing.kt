package com.wolodja.slow_and_calm_cinema.showing

import com.wolodja.slow_and_calm_cinema.comon.BaseEntity
import com.wolodja.slow_and_calm_cinema.movie.Movie
import org.springframework.data.repository.CrudRepository
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "showing")
@Table(name = "showing")
class Showing(

    @ManyToOne(optional = false)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_showing_to_movie_id"), nullable = false)
    val movie: Movie,

    var time: LocalDateTime,

    var priceFrom: BigDecimal,

    var priceTo: BigDecimal

) : BaseEntity()

interface ShowingRepository : CrudRepository<Showing, UUID> {
    fun getByMovieAndTimeIsAfter(movie: Movie, timeAfter: LocalDateTime): List<Showing>
}

data class ShowingDto(
    val id: UUID?,
    val movieId: UUID,
    val time: LocalDateTime,
    val priceFrom: BigDecimal,
    val priceTo: BigDecimal,
)