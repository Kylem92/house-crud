package com.version1.kotlin.housecrud.config

import com.version1.kotlin.housecrud.service.AuthenticationFilter
import com.version1.kotlin.housecrud.service.AuthorizationFilter
import com.version1.kotlin.housecrud.service.UserDetailsService
import com.version1.kotlin.housecrud.utils.JwtTokenUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfiguration(
    private val userDetailsService: UserDetailsService,
) {
    private val jwtToken = JwtTokenUtil()

    private fun authManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.userDetailsService(userDetailsService)
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authenticationManager = authManager(http)
        http.authorizeRequests().antMatchers("/user/**", "/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**")
            .permitAll()
            .anyRequest().authenticated().and().csrf().disable()
            .authenticationManager(authenticationManager)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .addFilter(AuthenticationFilter(jwtToken, authenticationManager))
            .addFilter(AuthorizationFilter(jwtToken, userDetailsService, authenticationManager))


        http.headers().frameOptions().disable()

        return http.build()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
