package net.boreeas.reweave.data

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Malte Schütze
 */
data class MapList(
        val default: Long,
        @SerializedName("maps")
        val _maps: List<Long>?
) {
    val maps: List<Long>
        get() = _maps ?: ArrayList()
}