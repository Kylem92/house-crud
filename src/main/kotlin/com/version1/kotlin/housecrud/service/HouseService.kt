package com.version1.kotlin.housecrud.service

import com.version1.kotlin.housecrud.model.DateRange
import com.version1.kotlin.housecrud.model.House
import com.version1.kotlin.housecrud.model.PriceInfo
import com.version1.kotlin.housecrud.repository.HouseRepository
import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.redis.core.TimeToLive
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.streams.toList


@Service
class HouseService(val db: HouseRepository) {
    private val logger = KotlinLogging.logger {}

    @Cacheable(value = ["allhouses1"])
    fun findHouses() : List<House> {
        logger.info { "Getting all houses" }
        return db.findHouses()
    }

    fun save(house: House) = db.save(house)

    @Cacheable(value = ["houseById"], key = "#id")
    @TimeToLive(unit = TimeUnit.SECONDS)
    fun findById(id: String): Optional<House> {
        logger.info { "Getting house with ID: $id" }
        return db.findById(id)
    }

    fun deleteById(id: String): Unit = db.deleteById(id)

    @CacheEvict(value = ["priceById", "houseById"], key = "#house.id")
    fun update(house: House): House = db.save(house)

    @Cacheable(value = ["priceById"], key = "#id")
    fun getPriceInfoById(id: String): PriceInfo? {
        val house: Optional<House> = findById(id)
        return if (house.isPresent) mapPriceInfo(house.get()) else null
    }

    fun getPriceInfoWithinRange(dateRange: DateRange) : List<PriceInfo> {
        return db.findHouses().stream().filter{checkInRange(it.buildDate, dateRange)}
            .map(this::mapPriceInfo).toList()
    }

    fun checkInRange(buildDate: Date, dateRange: DateRange): Boolean {
        return buildDate in dateRange.fromDate..dateRange.toDate
    }

    fun mapPriceInfo(house: House): PriceInfo{
        return PriceInfo(house.id, house.marketPrice, house.currency)
    }

    @CacheEvict(value = ["allhouses1"], allEntries = true)
    // @Scheduled(fixedRate = 10000000)
    fun emptyHotelsCache() {
        logger.info("emptying all houses cache")
    }

//    @Bean
//    fun timeoutCacheManager(redisTemplate: RedisTemplate<*, *>?): CacheManager? {
//        val cacheManager = RedisCacheManager(redisTemplate)
//        cacheManager.setDefaultExpiration(10000)
//        return cacheManager
//    }
//    fun<T : Any> Optional<T>.toNullable(): T? = this.orElse(null);
}