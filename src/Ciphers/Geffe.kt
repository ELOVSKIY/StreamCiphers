package Ciphers

import kotlin.experimental.xor

open class Geffe: StreamEncipher {
    private var decodeBits1: ByteArray
    private var encodeBits1: ByteArray
    private var decodeBits2: ByteArray
    private var encodeBits2: ByteArray
    private var decodeBits3: ByteArray
    private var encodeBits3: ByteArray

    constructor(registerState1: String, registerState2: String, registerState3: String){
        decodeBits1 = parseStringToBitArray(registerState1)
        encodeBits1 = parseStringToBitArray(registerState1)
        decodeBits2 = parseStringToBitArray(registerState2)
        encodeBits2 = parseStringToBitArray(registerState2)
        decodeBits3 = parseStringToBitArray(registerState3)
        encodeBits3 = parseStringToBitArray(registerState3)
    }
    override fun decode(cipherBytes: ByteArray): ByteArray {
        val cipherBits = parseByteArrayToBitArray(cipherBytes)
        for (i in cipherBits.indices){
            if (i % registerSize == 0 && i != 0){
                updateRegister(decodeBits1)
                updateRegister(decodeBits2)
                updateRegister(decodeBits3)
            }
            cipherBits[i] = cipherBits[i] xor
                    (if (decodeBits3[i  % registerSize] == 0.toByte())
                        decodeBits1[i  % registerSize] else decodeBits2[i  % registerSize])
        }
        return parseBitArrayToByteArray(cipherBits)
    }

    override fun encode(plainBytes: ByteArray): ByteArray {
        val plainBits = parseByteArrayToBitArray(plainBytes)
        for (i in plainBits.indices){
            if (i % registerSize == 0 && i != 0){
                updateRegister(encodeBits1)
                updateRegister(encodeBits2)
                updateRegister(encodeBits3)
            }
            plainBits[i] = plainBits[i] xor
                    (if (encodeBits3[i % registerSize] == 0.toByte())
                        encodeBits1[i  % registerSize] else encodeBits2[i % registerSize])
        }
        return parseBitArrayToByteArray(plainBits)
    }

    fun getExample(): String{
        val exampleRegister = ByteArray(registerSize)
        for (i in exampleRegister.indices){
            exampleRegister[i] = if (encodeBits3[i] == 0.toByte()) encodeBits1[i] else encodeBits2[i]
        }
        return parseBitArrayToString(exampleRegister)
    }

}