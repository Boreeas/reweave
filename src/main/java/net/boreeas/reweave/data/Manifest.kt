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

import net.boreeas.reweave.readShortUtf8
import java.io.DataInputStream
import java.io.InputStream

/**
 * @author Malte Schütze
 */
class Manifest(raw: InputStream) {

    val version: String
    val environment: String
    val remaining: ByteArray

    init {
        val input = DataInputStream(raw)
        var unk_byte = input.readUnsignedByte() // 0x0a as of 08/02/2017
        version = input.readShortUtf8()
        unk_byte = input.readUnsignedByte() // 0x12 as of 08/02/2017
        environment = input.readShortUtf8()
        remaining = input.readBytes()
    }
}