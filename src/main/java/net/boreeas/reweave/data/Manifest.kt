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