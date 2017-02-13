package net.boreeas.reweave.data

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Malte Schütze
 */
data class DeckList(@SerializedName("decks") private val _decks: List<Deck>?) {
    val decks: List<Deck>
        get() = _decks ?: ArrayList()
}