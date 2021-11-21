package com.wolodja.slow_and_calm_cinema.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AuthenticationProvider(val userDetailProvider: UserDetailProvider, val passwordEncoder: PasswordEncoder) {

    @Bean
    fun authProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailProvider)
        authProvider.setPasswordEncoder(passwordEncoder)
        return authProvider
    }
}