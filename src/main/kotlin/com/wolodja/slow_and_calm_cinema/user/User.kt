package com.wolodja.slow_and_calm_cinema.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.wolodja.slow_and_calm_cinema.BaseEntity
import org.springframework.data.repository.CrudRepository
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity(name = "user")
@Table(name = "user_table")
class User(

    @Column(nullable = false)
    val username: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val role: String,

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
