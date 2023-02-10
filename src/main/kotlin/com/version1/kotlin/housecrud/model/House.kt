package com.version1.kotlin.housecrud.model

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.io.Serializable
import java.math.BigDecimal
import java.util.Date

@Table("HOUSES")
@Schema(description = "Model for a house.")
data class House(
    @Schema(description = "House ID.")
    @Id val id: String?,
//    @Schema(description = "Address of house.")
//    val address: Address,
    @Schema(description = "Number of bedrooms.")
    val bedrooms: Int,
    @Schema(description = "Number of bathrooms.")
    val bathrooms: Int,
    @Schema(description = "Floor area in m&#253.")
    val area: Int?,
    @Schema(description = "Date the house was built on.")
    val buildDate: Date,
    @Schema(description = "Current market price value of the house.")
    val marketPrice: BigDecimal,
    @Schema(description = "Currency of market price.")
    val currency: String,
    @Schema(description = "Estate agent selling the house.")
    val estateAgent: String
    ) : Serializable{
//    class Address(
//        val addressLine1: String,
//        val addressLine2: String?,
//        val addressLine3: String?,
//        val city: String,
//        val postcode: String
//    )
}