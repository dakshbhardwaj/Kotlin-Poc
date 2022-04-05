package com.example.findmyage.models

import java.io.Serializable

data class Weather(
    val id: Integer,
    val main: String,
    val description: String,
    val icon: String

): Serializable