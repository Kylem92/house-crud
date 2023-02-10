package com.version1.kotlin.housecrud.controller

import com.version1.kotlin.housecrud.model.UserLoginDetails
import com.version1.kotlin.housecrud.service.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserLoginDetailsController(val userService: UserDetailsService, private val passwordEncoder: BCryptPasswordEncoder) {

    @PostMapping("/signup")
    fun saveUser(@RequestBody user: UserLoginDetails): UserLoginDetails {
        user.password = passwordEncoder.encode(user.password)
        return userService.saveUser(user)
    }

}