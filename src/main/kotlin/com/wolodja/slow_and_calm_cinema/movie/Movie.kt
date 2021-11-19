package com.wolodja.slow_and_calm_cinema.movie

import org.springframework.data.repository.CrudRepository
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "movie")
@Table(name = "movie")
class Movie(

    @Id
    val imdbId: String,

    @Column(nullable = false)
    val title: String,

)

interface MovieRepository : CrudRepository<Movie, String>

data class MovieDTO(
    val imdbId: String,
    val title: String
)
