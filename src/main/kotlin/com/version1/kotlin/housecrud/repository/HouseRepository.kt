package com.version1.kotlin.housecrud.repository

import com.version1.kotlin.housecrud.model.House
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface HouseRepository : CrudRepository<House, String>{

    @Query("select * from houses")
    fun findHouses(): List<House>

//    @Query("select * from houses with")
//    fun findByAddress(address: House.Address): Optional<House>
}