package de.bunk.domain

data class Repository(
    val id: String,
    val name: String,
    val description: String?,
    val primaryLanguage: String?,
    val starCount: Int,
    val cursor: String
)