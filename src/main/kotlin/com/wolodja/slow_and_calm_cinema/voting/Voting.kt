package com.wolodja.slow_and_calm_cinema.voting

import com.wolodja.slow_and_calm_cinema.BaseEntity
import com.wolodja.slow_and_calm_cinema.movie.Movie
import com.wolodja.slow_and_calm_cinema.user.User
import org.springframework.data.repository.CrudRepository
import java.util.*
import javax.persistence.*

@Entity(name = "voting")
@Table(name = "voting")
class Voting (

    @ManyToOne(optional = false)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_voting_to_movie_id"), nullable = false)
    val movie: Movie,

    val rating: Int,

    @ManyToOne(optional = false)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_voting_to_user_id"), nullable = false)
    val user: User,


    ) : BaseEntity()


interface VotingRepository : CrudRepository<Voting, UUID>{
    fun findByUserAndAndMovie(user: User, movie: Movie): Optional<Voting>
}

data class VotingDto(
    val id: UUID?,
    val movieId: UUID,
    val userId: UUID,
    val rating: Int
)