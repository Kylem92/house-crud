package com.version1.kotlin.housecrud.service

import com.version1.kotlin.housecrud.model.UserLoginDetails
import com.version1.kotlin.housecrud.model.UserSecurity
import com.version1.kotlin.housecrud.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserDetailsService(
    val repository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByEmail(username) ?: throw UsernameNotFoundException("$username not found")
        return UserSecurity(
            user.email,
            user.password,
            Collections.singleton(SimpleGrantedAuthority("user"))
        )
    }
    fun saveUser(user: UserLoginDetails): UserLoginDetails {
        return repository.save(user)
    }
}