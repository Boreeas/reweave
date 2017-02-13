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
