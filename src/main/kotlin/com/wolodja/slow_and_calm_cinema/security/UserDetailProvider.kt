package com.wolodja.slow_and_calm_cinema.security

import com.wolodja.slow_and_calm_cinema.user.User
import com.wolodja.slow_and_calm_cinema.user.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class UserDetailProvider(val userRepository: UserRepository) : UserDetailsService {


    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByUsername(username.orEmpty())
        if (user.isPresent)
            return UserDetailImpl(user.get())
        throw UsernameNotFoundException("User not exists")

    }

    class UserDetailImpl(private val user: User) : UserDetails {

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
            return mutableListOf(SimpleGrantedAuthority("ROLE_" + user.role))
        }

        override fun getPassword(): String {
            return user.password
        }

        override fun getUsername(): String {
            return user.username
        }

        override fun isAccountNonExpired(): Boolean {
            return true
        }

        override fun isAccountNonLocked(): Boolean {
            return true
        }

        override fun isCredentialsNonExpired(): Boolean {
            return true
        }

        override fun isEnabled(): Boolean {
            return true
        }
    }

}