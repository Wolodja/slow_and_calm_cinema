package com.wolodja.slow_and_calm_cinema.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Configuration
@EnableWebSecurity
class CustomWebSecurityConfigurerAdapter(val authenticationProvider: DaoAuthenticationProvider) : WebSecurityConfigurerAdapter() {

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .antMatcher("/api/v1/**")
            .authorizeRequests()
            .antMatchers("/api/v1/showing/*").hasRole("OWNER")
            .antMatchers("/api/v1/showing/movie/*").permitAll()
            .antMatchers("/api/v1/viewer/register").permitAll()
            .antMatchers("/api/v1/movie/*").permitAll()
            .anyRequest().hasRole("OWNER")
            .and()
            .httpBasic()
    }
}

