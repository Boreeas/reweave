package net.boreeas.reweave.data

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Malte Schütze
 */
data class Expedition(
        @SerializedName("active_players")
        private val _activePlayers: Map<String, Long>?,

        @SerializedName("contributors")
        val _contributors: Map<String, PlayerContribution>?,

        val expeditionClass: String? = null,
        val expeditionId: String? = null,
        val houseId: String? = null,
        val housePresentationData: House.PresentationData? = null,
        val isTutorial: Boolean = false,
        val numQuestsCompleted: Int = 0,
        val percentComplete: Double = 0.0,
        val percentProgress: Double = 0.0,
        val presentationData: ExpeditionIslandPresentationData? = null,

        @SerializedName("quest_node_connections")
        val _questNodeConnections: List<String>?, // One string per connection, format: <node>:VAULT or VAULT:<node>

        @SerializedName("quest_node_slots")
        val _questNodeSlots: Map<String, Quest.Node>?,

        @SerializedName("quests")
        val _quests: List<Quest>? = null,

        val transientData: TransientData?
) {
    val activePlayers: Map<String, Long>
        get() = _activePlayers ?: HashMap()

    val contributors: Map<String, PlayerContribution>
        get() = _contributors ?: HashMap()

    val questNodeConnections: List<String>
        get() = _questNodeConnections ?: ArrayList()

    val questNodeSlots: Map<String, Quest.Node>
        get() = _questNodeSlots ?: HashMap()

    val quests: List<Quest>
        get() = _quests ?: ArrayList()

    data class TransientData(
            val shapeMask: String? = null,
            val shapeMaskDimensions: Int = 0
    )

    data class PlayerContribution(
            val displayName: String? = null,
            val percent: Double = 0.0
    )
}