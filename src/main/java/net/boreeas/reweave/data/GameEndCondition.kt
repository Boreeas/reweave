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
enum class GameEndCondition(val code: Int) {
    WIN(0),
    LOSS(1),
    LOSS_CONCEDE(2),
    WIN_CONCEDE(3),
    DRAW(4);

    companion object {
        @JvmStatic
        fun byCode(code: Int): GameEndCondition {
            return values().first { it.code == code }
        }
    }
}