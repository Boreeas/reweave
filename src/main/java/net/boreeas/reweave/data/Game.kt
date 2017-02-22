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
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * @author Malte Schütze
 */
data class Game(
        val adjustedEndCondition: GameEndCondition?,
        val gameId: String?,
        val opponentDisplayName: String?,
        val opponentId: String?,
        @SerializedName("start_date")
        private val _startDate: String?
) {
    /**
     * Retrieve the game start date as an Instant. Currently, time zone information is missing from the start date field
     * of the api. In it's absence, UTC is assumed.
     */
    val startDate: OffsetDateTime?
        get() = if (_startDate != null) OffsetDateTime.of(LocalDateTime.parse(_startDate, DATE_FORMAT), ZoneOffset.UTC) else null

    companion object {
        val DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss")
    }
}