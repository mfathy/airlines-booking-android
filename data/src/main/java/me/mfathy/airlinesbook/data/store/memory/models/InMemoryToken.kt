package me.mfathy.airlinesbook.data.store.memory.models

data class InMemoryToken(val clintId: String = "",
                         val accessToken: String = "",
                         val tokenType: String = "",
                         val expiresIn: Long = 0)