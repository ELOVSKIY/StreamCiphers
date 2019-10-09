package Ciphers

import Registers.*
import kotlin.experimental.xor

open class Geffe: StreamEncipher {
    private var register1: Register
    private var register2: Register
    private var register3: Register

    constructor(registerState1: String, registerState2: String, registerState3: String){
        register1 = RegisterX29(registerState1)
        register2 = RegisterX23(registerState2)
        register3 = RegisterX28(registerState3)

    }
    override fun decode(cipherBytes: ByteArray): ByteArray {
        val cipherBits = parseByteArrayToBitArray(cipherBytes)
        for (i in cipherBits.indices){
            cipherBits[i] = cipherBits[i] xor
                    (if (register3.nextBit() == 0.toByte())
                        register1.nextBit() else register2.nextBit())
        }
        return parseBitArrayToByteArray(cipherBits)
    }

    override fun encode(plainBytes: ByteArray): ByteArray {
        val plainBits = parseByteArrayToBitArray(plainBytes)
        for (i in plainBits.indices){
            plainBits[i] = plainBits[i] xor
                    (if (register3.nextBit() == 0.toByte())
                        register1.nextBit() else register2.nextBit())
        }
        return parseBitArrayToByteArray(plainBits)
    }

    fun getExample(): String{
        return ""
    }

}