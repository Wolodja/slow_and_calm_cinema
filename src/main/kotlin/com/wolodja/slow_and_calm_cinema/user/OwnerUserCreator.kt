package com.wolodja.slow_and_calm_cinema.user

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class OwnerUserCreator(private val userRepository: UserRepository) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun run(vararg args: String?) {
        if (userRepository.count() == 0L) {
            createOwnerUser()
        }
    }

    private fun createOwnerUser() {
        val owner = User(username = "calm_owner", password = BCryptPasswordEncoder().encode("calm_password"), role = UserRole.OWNER.name)
        userRepository.save(owner)
        log.info("Owner user created.")
    }
}