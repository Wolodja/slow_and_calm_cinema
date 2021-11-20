package com.wolodja.slow_and_calm_cinema.movie

import com.wolodja.slow_and_calm_cinema.BaseEntity
import org.springframework.data.repository.CrudRepository
import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity(name = "movie")
@Table(name = "movie")
class Movie(

    @Column(nullable = false)
    val imdbId: String,

    @Column(nullable = false)
    val title: String,

    val releaseDate: String?,

    val runtime: String?,

    val imdbRating: BigDecimal?,

    val description: String?,

    val director: String?,

) : BaseEntity()

interface MovieRepository : CrudRepository<Movie, UUID>

data class MovieDto(
    val id: UUID,
    val title: String,
    val releaseDate: String?,
    val runtime: String?,
    val imdbRating: BigDecimal?,
    val description: String?,
    val director: String?,
)
