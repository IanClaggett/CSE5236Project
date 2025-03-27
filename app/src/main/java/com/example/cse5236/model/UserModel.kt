package com.example.cse5236.model



data class User(
    val username: String,
    val difficulty: String,
    val scores: List<Score>
)