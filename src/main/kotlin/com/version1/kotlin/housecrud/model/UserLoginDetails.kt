package com.version1.kotlin.housecrud.model

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.beans.ConstructorProperties

@Table("USERS")
data class UserLoginDetails @ConstructorProperties("id", "email", "password") constructor(@Id @Schema(hidden = true)val id: String?, val email: String, var password: String)