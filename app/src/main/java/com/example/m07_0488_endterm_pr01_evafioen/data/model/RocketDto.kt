package com.example.m07_0488_endterm_pr01_evafioen.data.model

import com.google.gson.annotations.SerializedName

data class RocketDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("active")
    val active: Boolean,

    @SerializedName("flickr_images")
    val images: List<String> = emptyList(),

    @SerializedName("first_flight")
    val firstFlight: String?,

    @SerializedName("cost_per_launch")
    val costPerLaunch: Double?,

    @SerializedName("success_rate_pct")
    val successRatePct: Double?,

    @SerializedName("wikipedia")
    val wikipedia: String?
)
