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