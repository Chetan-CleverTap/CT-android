package com.clevertap.demo

import com.clevertap.android.sdk.variables.annotations.Variable
import java.util.*
import kotlin.collections.HashMap

class ProductExperiences private constructor() {

    init {
        val ads:HashMap<String,Any> = HashMap()
        ads["adCapPerHour"] = 0
        ads["adsCoolDownMinutes"] = 10
        ads["adsDurationMinutes"] = 0.5
        ads["enableAds"] = true

        val content:HashMap<String,Any> = HashMap()
        content["genreAffinity"] = "comedy"
        content["liveSportsInterest"] = true
        content["adsDurationMinutes"] = 0.5
        content["enableAds"] = true


    }
}