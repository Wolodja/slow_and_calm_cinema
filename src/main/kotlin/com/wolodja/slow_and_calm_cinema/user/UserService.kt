package com.wolodja.slow_and_calm_cinema.user

import com.wolodja.slow_and_calm_cinema.comon.MovieNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserService(val userRepository: UserRepository, val passwordEncoder: PasswordEncoder) {

    fun getUserEntity(userId: UUID): User {
        return userRepository.findByIdOrNull(userId) ?: throw MovieNotFoundException("User with id $userId does not exist")
    }

    fun registerNewViewerAccount(userDto: UserDto): UserDto? {
        validateUser(userDto)
        val userToSave = User(username = userDto.username, password = passwordEncoder.encode(userDto.password), role = UserRole.VIEWER.name)
        val savedUser = userRepository.save(userToSave)
        return UserDto(username = savedUser.username, id = savedUser.id, role = UserRole.valueOf(savedUser.role), password = "")
    }

    private fun validateUser(userDto: UserDto) {
        if (userDto.password.isNullOrBlank()) {
            throw IllegalArgumentException("Password was not provided.")
        }
        val user = userRepository.findByUsername(userDto.username)
        if (user.isPresent) {
            throw IllegalArgumentException("There is an account with user name:" + userDto.username)
        }
    }
}