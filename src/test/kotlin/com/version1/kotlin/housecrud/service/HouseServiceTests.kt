package com.version1.kotlin.housecrud.service

import com.version1.kotlin.housecrud.model.DateRange
import com.version1.kotlin.housecrud.model.House
import com.version1.kotlin.housecrud.model.PriceInfo
import com.version1.kotlin.housecrud.repository.HouseRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.assertEquals

class HouseServiceTests {
    private val houseRepository: HouseRepository = mockk()
    private val houseService = HouseService(houseRepository)

    private val HOUSE_ID = "id"

    @Test
    fun findHouses_test(){
        //given
        val house = createHouse()
        every { houseRepository.findHouses() } returns listOf(house)
        //when
        val result = houseService.findHouses()
        //then
        verify(exactly = 1) { houseRepository.findHouses() }
        assertEquals(listOf(house), result)
    }

    @Test
    fun saveHouse_test(){
        //given
        val house = createHouse()
        every { houseRepository.save(house) } returns house
        //when
        val result = houseService.save(house)
        //then
        verify(exactly = 1) { houseRepository.save(house) }
        assertEquals(house, result)
    }

    @Test
    fun findById_test(){
        //given
        val house = createHouse()
        every { houseRepository.findById(HOUSE_ID) } returns Optional.of(house)
        //when
        val result = houseService.findById(HOUSE_ID)
        //then
        verify(exactly = 1) { houseRepository.findById(HOUSE_ID) }
        assertEquals(Optional.of(house), result)
    }

    @Test
    fun deleteById_test(){
        //given
        every { houseRepository.deleteById(HOUSE_ID) } returns Unit
        //when
        houseService.deleteById(HOUSE_ID)
        //then
        verify(exactly = 1) { houseRepository.deleteById(HOUSE_ID) }
    }

    @Test
    fun updateHouse_test(){
        //given
        val house = createHouse()
        every { houseRepository.save(house) } returns house
        //when
        val result = houseService.update(house)
        //then
        verify(exactly = 1) { houseRepository.save(house) }
        assertEquals(house, result)
    }

    @Test
    fun getPriceInfoById_test(){
        //given
        val priceInfo = PriceInfo(HOUSE_ID, BigDecimal(2000000), "EUR")
        val house = createHouse()
        every { houseRepository.findById(HOUSE_ID) } returns Optional.of(house)
        //when
        val result = houseService.getPriceInfoById(HOUSE_ID)
        //then
        verify(exactly = 1) { houseRepository.findById(HOUSE_ID) }
        assertEquals(priceInfo, result)
    }

    @Test
    fun getPriceInfoWithinRange_test(){
        //given
        val dateRange = DateRange(createDate("01/01/2020"), createDate("01/01/2025"))
        val priceInfo = PriceInfo(HOUSE_ID, BigDecimal(2000000), "EUR")
        val house = createHouse()
        every { houseRepository.findHouses() } returns listOf(house)
        //when
        val result = houseService.getPriceInfoWithinRange(dateRange)
        //then
        verify(exactly = 1) { houseRepository.findHouses() }
        assertEquals(listOf(priceInfo), result)
    }

    private fun createHouse(): House {
        val date: Date = createDate("08/07/2023")
        return House(HOUSE_ID, 3, 1, 150, date, BigDecimal(2000000), "EUR", "Estate Agent")
    }

    private fun createDate(date: String): Date {
        val dateFor = SimpleDateFormat("dd/MM/yyyy")
        return dateFor.parse(date)
    }

}