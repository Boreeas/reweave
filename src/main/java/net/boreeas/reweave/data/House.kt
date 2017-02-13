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