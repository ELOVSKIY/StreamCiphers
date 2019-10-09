package Ciphers

import Registers.*
import kotlin.experimental.xor

open class Geffe: StreamEncipher {
    private var register1: Register
    private var register2: Register
    private var register3: Register
    private var keyBits = ByteArray(0)

    constructor(registerState1: String, registerState2: String, registerState3: String){
        register1 = RegisterX29(registerState1)
        register2 = RegisterX23(registerState2)
        register3 = RegisterX28(registerState3)

    }

    override fun code(plainBytes: ByteArray): ByteArray {
        val plainBits = parseByteArrayToBitArray(plainBytes)
        keyBits = ByteArray(plainBits.size)
        for (i in plainBits.indices){
            keyBits[i] = (if (register3.nextBit() == 0.toByte())
                register1.nextBit() else register2.nextBit())
            plainBits[i] = plainBits[i] xor keyBits[i]

        }
        return parseBitArrayToByteArray(plainBits)
    }


    override fun getKeyBits(): ByteArray{
        return parseBitArrayToByteArray(keyBits)
    }

}