package com.example.findmyage.models

data class News(
    val news: List<New>,
    val status: Boolean,
    val total_pages: Int
)