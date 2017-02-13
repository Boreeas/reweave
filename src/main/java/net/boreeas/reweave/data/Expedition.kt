/*
 *    Copyright 2017 Malte Schütze
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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