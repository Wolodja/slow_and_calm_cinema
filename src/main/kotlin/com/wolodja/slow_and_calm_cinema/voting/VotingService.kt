package com.wolodja.slow_and_calm_cinema.voting

import com.wolodja.slow_and_calm_cinema.movie.MovieProvider
import com.wolodja.slow_and_calm_cinema.user.UserService
import org.springframework.stereotype.Service

@Service
class VotingService(private val votingRepository: VotingRepository, private val movieProvider: MovieProvider, private val userService: UserService) {

    //TODO: check if user from Basic Auth is the same as user from votingDto
    fun saveVoting(votingDto: VotingDto) {
        validateRating(votingDto.rating)
        val user = userService.getUserEntity(votingDto.userId)
        val movie = movieProvider.getMovieEntity(votingDto.movieId)
        val existingVoting = votingRepository.findByUserAndAndMovie(user, movie)
        if (existingVoting.isPresent) {
            throw IllegalArgumentException("Voting from user ${user.username} for movie ${movie.title} exists.")
        }
        votingRepository.save(Voting(user = user, movie = movie, rating = votingDto.rating))
    }

    private fun validateRating(rating: Int) {
        if (rating < 1 || rating > 5) {
            throw IllegalArgumentException("Invalid movie rating.")
        }
    }
}