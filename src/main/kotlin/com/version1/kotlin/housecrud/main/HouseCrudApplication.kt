package com.version1.kotlin.housecrud.main

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@ComponentScan(basePackages = ["com.version1.kotlin.housecrud"])
@EnableJdbcRepositories(basePackages = ["com.version1.kotlin.housecrud.repository"])
@EnableCaching
@EnableScheduling
class HouseCrudApplication

fun main(args: Array<String>) {
	runApplication<HouseCrudApplication>(*args)
}
