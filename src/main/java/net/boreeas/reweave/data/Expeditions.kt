package net.boreeas.reweave.data

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Malte Schütze
 */
data class Expeditions(
        @SerializedName("joined_expeditions")
        private val _joinedExpeditions: List<Expedition>?,

        @SerializedName("twitch_expeditions")
        private val _twitchExpeditions: List<Expedition>?
) {
    val joinedExpeditions: List<Expedition>
        get() = _joinedExpeditions ?: ArrayList()

    val twitchExpeditions: List<Expedition>
        get() = _twitchExpeditions ?: ArrayList()

    override fun toString(): String {
        return "Expeditions(joinedExpeditions=$joinedExpeditions, twitchExpeditions=$twitchExpeditions)"
    }
}
