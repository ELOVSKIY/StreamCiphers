package Ciphers

import java.lang.StringBuilder
import kotlin.experimental.and
import kotlin.experimental.xor
import kotlin.math.abs

const val registerSize = 29

fun parseByteArrayToBitString(byteArray: ByteArray): String {
    val builder = StringBuilder()
    for (i in byteArray.indices) {
        if ((i % 5 == 0) and (i != 0))
            builder.append('\n')
        builder.append(parseByteToBitString(byteArray[i]))
        builder.append(" ")
    }
    return builder.toString()
}

fun parseByteArrayToBitArray(byteArray: ByteArray): ByteArray{
    val bitArray = ByteArray(8 * byteArray.size)
    for (i in byteArray.indices){
        val bits = parseByteToBits(byteArray[i])
        for (j in bits.indices){
            bitArray[i * 8 + j] = bits[j]
        }
    }
    return bitArray
}

fun parseByteToBits(byte: Byte): ByteArray {
    val bits = ByteArray(8)
    val masks =
            byteArrayOf(1.toByte(), 2.toByte(), 4.toByte(), 8.toByte(), 16.toByte(), 32.toByte(), 64.toByte(), 128.toByte())
    for (i in masks.indices) {
        var bit = byte and masks[i]
        bit = (bit.toInt() shr i).toByte()
        bit = abs(bit.toInt()).toByte()
        bits[i] = bit
    }
    return bits
}

fun parseByteToBitString(byte: Byte): String {
    val builder = StringBuilder()
    val bits = parseByteToBits(byte)
    for (i in bits.indices) {
        builder.append(bits[i].toString())
    }
    builder.reverse()
    return builder.toString()
}

fun parseBitArrayToByteArray(bitArray: ByteArray): ByteArray{
    val byteArray = ByteArray(bitArray.size / 8)
    val bitsInByte = ByteArray(8)
    for (i in 0 until bitArray.size / 8){
        for (j in 0..7){
            bitsInByte[j] = bitArray[i * 8 + j]
        }
        byteArray[i] = parseBitsToByte(bitsInByte)
    }
    return byteArray
}

fun parseBitsToByte(bits: ByteArray): Byte {
    var byte = 0.toByte()
    for (i in 0..7) {
        val tempBit = (bits[i].toInt() shl i).toByte()
        byte = byte xor tempBit
    }
    return byte
}

fun parseStringToBitArray(bits: String): ByteArray{
    val bitArray = ByteArray(registerSize)
    for (i in bits.indices){
        val bit = (bits[i].toByte() - 48).toByte()
        bitArray[i] = bit
    }
    return bitArray
}

fun parseBitArrayToString(bitArray: ByteArray): String{
    val builder = StringBuilder()
    for (i in bitArray){
        builder.append((i + 48).toChar())
    }
    return builder.toString()
}

fun shiftRegister(register: ByteArray) { // возможно придется поменять
    val generatedBit =
            register[register.size - 29] xor register[register.size - 2] // Осторожно тестировалось на других
    for (i in 0 until register.size - 1) {
        register[i] = register[i + 1]
    }
    register[register.size - 1] = generatedBit
}

fun updateRegister(keyRegister: ByteArray) {
    for (i in keyRegister) {
        shiftRegister(keyRegister)
    }
}