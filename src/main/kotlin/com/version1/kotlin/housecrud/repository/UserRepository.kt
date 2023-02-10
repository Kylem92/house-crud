package com.version1.kotlin.housecrud.repository

import com.version1.kotlin.housecrud.model.UserLoginDetails
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserLoginDetails, String> {
    @Query("select * from users")
    fun findAllUsers(): List<UserLoginDetails>

    fun findByEmail(email: String): UserLoginDetails?
}