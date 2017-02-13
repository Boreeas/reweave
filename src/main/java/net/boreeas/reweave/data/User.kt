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

/**
 * @author Malte Schütze
 */
data class User(
        val activeGame: String? = null,
        val displayName: String? = null,
        val houseId: String? = null,
        val houseShardfallExpedition: String? = null,
        val isDev: Boolean = false,
        val questsCompleted: Int = 0,
        val status: Int = 0,
        val twitchDisplayName: String? = null,
        val twitchId: Int? = null,
        val userId: String? = null,
        val wins: Int = 0
) {

    data class PrivateData(
            val acceptedEula: Boolean = false,
            val lastReroll: String? = null,
            val userId: String? = null
    )
}