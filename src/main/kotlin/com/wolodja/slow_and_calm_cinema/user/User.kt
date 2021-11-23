package com.wolodja.slow_and_calm_cinema.user

import com.wolodja.slow_and_calm_cinema.comon.BaseEntity
import com.wolodja.slow_and_calm_cinema.voting.Voting
import org.springframework.data.repository.CrudRepository
import java.util.*
import javax.persistence.*

@Entity(name = "user")
@Table(name = "user_table")
class User(

    @Column(nullable = false)
    val username: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val role: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    val votes: List<Voting> = emptyList(),

    ) : BaseEntity()

interface UserRepository : CrudRepository<User, UUID>{
    fun findByUsername(username: String): Optional<User>
}

//TODO extract to entity
enum class UserRole  {
    VIEWER, OWNER
}

data class UserDto(
    val id: UUID?,
    val username: String,
    val password: String?,
    val role: UserRole?
)
