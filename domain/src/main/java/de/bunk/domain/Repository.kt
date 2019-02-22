package de.bunk.domain

data class Repository(
    val id: String,
    val name: String,
    val owner: String,
    val description: String?,
    val primaryLanguage: String?,
    val starCount: Int,
    val avatarUrl: String?
) {
    companion object {
        fun unknownRepository() = Repository(
            "unknown id",
            "unknown mame",
            "unknown owner",
            null,
            null,
            -1,
            null
        )
    }
}