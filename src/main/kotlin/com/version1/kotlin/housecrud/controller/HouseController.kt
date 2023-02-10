package com.version1.kotlin.housecrud.controller

import com.version1.kotlin.housecrud.model.DateRange
import com.version1.kotlin.housecrud.model.House
import com.version1.kotlin.housecrud.model.PriceInfo
import com.version1.kotlin.housecrud.service.HouseService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/house")
class HouseController(val houseService: HouseService) {
    private val logger = KotlinLogging.logger {}

    @Operation(summary = "Get all houses", description = "Returns a list of all houses in the database.")
    @ApiResponse(responseCode = "202", description = "List of houses",
    content = [Content(mediaType = "application/json", array = (
            ArraySchema(schema = Schema(implementation = House::class))))])
    @GetMapping("/getall")
    fun getAll(): List<House> = houseService.findHouses()

    @Operation(summary = "Add house", description = "Adds a house to the database.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202", description = "Successful response",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = House::class))]),
            ApiResponse(responseCode = "400", description = "Bad request")
    ])
    @PostMapping("/add")
    fun saveHouse(@RequestBody house: House) = houseService.save(house)

    @Operation(summary = "Update house", description = "Updates a house in the database.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202", description = "Successful response",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = House::class))]),
            ApiResponse(responseCode = "400", description = "Bad request")
        ])
    @PutMapping("/update")
    fun updateHouse(@RequestBody house: House): House = houseService.update(house)

    @Operation(summary = "Get house by ID", description = "Returns a house in the database.")
    @ApiResponse(responseCode = "202", description = "Single house",
        content = [Content(mediaType = "application/json",
                schema = Schema(implementation = House::class))])
    @GetMapping("/findbyid/{id}")
    fun findById(@PathVariable id: String): Optional<House> = houseService.findById(id)

    @Operation(summary = "Delete by ID", description = "Deletes the house iwth the specified ID from the database.")
    @ApiResponse(responseCode = "202", description = "Successful response")
    @DeleteMapping("/deletebyid/{id}")
    fun deleteById(@PathVariable id: String): Unit = houseService.deleteById(id)

    @Operation(summary = "Get price info of a house by ID", description = "Returns the price info of a house in the database.")
    @ApiResponse(responseCode = "202", description = "Price info of a house",
        content = [Content(mediaType = "application/json",
            schema = Schema(implementation = PriceInfo::class))])
    @GetMapping("/getpricebyid/{id}")
    fun getPriceInfoById(@PathVariable id: String): PriceInfo? {
        logger.info { "Getting price info with ID: $id" }
        return houseService.getPriceInfoById(id)
    }

    @Operation(summary = "Get list of price info of houses within a specified build date range", description = "Returns a list of price info for all houses in the database within a build date range.")
    @ApiResponse(responseCode = "202", description = "List of price info",
        content = [Content(mediaType = "application/json", array = (
                ArraySchema(schema = Schema(implementation = PriceInfo::class))))])
    @PostMapping("/getpricewithinrange")
    fun getPriceInfoWithinRange(@RequestBody buildDateRange: DateRange): List<PriceInfo> = houseService.getPriceInfoWithinRange(buildDateRange)

//    @Cacheable(value = ["houseByAddress"], key = "#address")
//    @GetMapping("/findbyaddress/{id}")
//    fun findByAddress(@RequestBody address: House.Address): Optional<House> {
//        logger.info { "Getting house with address: $address" }
//        return houseService.findByAddress(address)
//    }

}