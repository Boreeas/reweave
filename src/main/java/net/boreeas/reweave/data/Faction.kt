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
enum class Faction(val internalName: String) {
    STEELSINGER("BigCreatureFaction"), // Petra, red
    FATEKEEPER("ControlFaction"), // Mori, blue
    LANDSHAPER("EarthbenderFaction"), // Vardan, green
    BLOODBINDER("SacrificeFaction"), // Sabine, purple,
    PACKRUNNER("SwarmFaction"), // Juro, yellow
    WAYFINDER("DirectBurnFaction"); // Wynn, orange

    companion object {
        @JvmStatic
        fun byInternalName(name: String): Faction? {
            return Faction.values().find {
                faction ->
                faction.internalName.equals(name, ignoreCase = true)
            }
        }
    }
}