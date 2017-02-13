package net.boreeas.reweave.data

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Malte Schütze
 */
data class Deck(
        val validVersion: String?,
        val primaryColor: Int,
        val secondaryColor: Int,
        val name: String?,
        val immutable: Boolean,
        val id: String?,
        val commanderType: Long,
        @SerializedName("errors")
        private val _errors: List<Any>?,
        @SerializedName("cards")
        private val _cards: List<Long>?
) {
    val errors: List<Any>
        get() = _errors ?: ArrayList()

    val cards: List<Long>
        get() = _cards ?: ArrayList()
}