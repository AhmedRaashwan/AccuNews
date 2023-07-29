package com.rashwan.accunews.entities

data class NewsEntity(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>,
    val title: String
)