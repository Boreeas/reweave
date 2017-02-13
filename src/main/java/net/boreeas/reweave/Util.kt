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

package net.boreeas.reweave

import java.io.DataInputStream
import java.util.*

/**
 * Miscellaneous helper methods
 * Created on 4/14/2014.
 */

/**
 * Dump the target bytes into a list of strings. Each element of the list is a single line, with
 * up to 16 bytes formatted. For example, a line may look like this:
 * <code>
 * 0a 18 30 2e 35 2e 31 31   39 37 37 2d 32 30 30 30 | ..0.5.11 977-2000
 * </code>
 */
fun hexdump(data: ByteArray): List<String> {

    val result = ArrayList<String>(data.size / 16)
    var i = 0
    while (i < data.size) {
        val builder = StringBuilder()
        val asChar = StringBuilder()
        run {
            var j = i
            while (j < i + 16 && j < data.size) {
                if (j == i + 8) {
                    builder.append("   ")
                    asChar.append(" ")
                } else if (j != i) {
                    builder.append(" ")
                }
                builder.append(String.format("%02x", data[j]))
                asChar.append(if (isPrintable(data[j].toChar())) data[j].toChar() else '.')
                j++
            }
        }

        for (j in builder.length..48) {
            builder.append(' ')
        }

        result.add(builder.toString() + " | " + asChar)
        i += 16
    }
    return result
}

fun isPrintable(c: Char): Boolean {
    val block = Character.UnicodeBlock.of(c)
    return !Character.isISOControl(c) && block != null && block !== Character.UnicodeBlock.SPECIALS
}

/**
 * Read an utf8-encoded string with a single byte as a length prefix from the data input stream
 * @return the string
 */
fun DataInputStream.readShortUtf8(): String {
    val len = this.readUnsignedByte()
    val buffer = ByteArray(len)
    this.readFully(buffer)
    return kotlin.text.String(buffer, Charsets.UTF_8)
}