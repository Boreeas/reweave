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
data class House(
        @SerializedName("admins")
        val _admins: List<String>?,
        val emblem: String? = null,
        val houseId: String? = null,
        val houseName: String? = null,
        val isOutpost: Boolean = false,
        @SerializedName("members")
        val _members: List<String>?,
        @SerializedName("owners")
        val _owners: List<String>?,
        val slogan: String? = null,
        val stats: Stats?
) {

    val admins: List<String>
        get() = _admins ?: ArrayList()

    val members: List<String>
        get() = _members ?: ArrayList()

    val owners: List<String>
        get() = _owners ?: ArrayList()

    data class Stats(
            @SerializedName("expeditions")
            private val _expeditions: Map<String, Any>?,

            @SerializedName("factions")
            private val _factions: Map<Faction, FactionStats>?,

            @SerializedName("players")
            private val _players: Map<String, PlayerStats>?,
            val version: Int = 0
    ) {
        val expeditions: Map<String, Any>
            get() = _expeditions ?: HashMap()

        val factions: Map<Faction, FactionStats>
            get() = _factions ?: HashMap()

        val players: Map<String, PlayerStats>
            get() = _players ?: HashMap()

        override fun toString(): String {
            return "Stats(expeditions=$expeditions, factions=$factions, players=$players, version=$version)"
        }
    }

    data class FactionStats(
            val played: Int
    )

    data class PlayerStats(
            val wins: Int
    )

    data class Island(
            val houseId: String? = null,
            val islandPresentationData: IslandPresentationData? = null
    )

    data class PresentationData(
            val houseName: String? = null,
            val emblem: String? = null
    )

    override fun toString(): String {
        return "House(admins=$admins, emblem=$emblem, houseId=$houseId, houseName=$houseName, isOutpost=$isOutpost, members=$members, owners=$owners, slogan=$slogan, stats=$stats)"
    }
}