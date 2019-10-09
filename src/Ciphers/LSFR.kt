package Ciphers

import Registers.*
import kotlin.experimental.*



open class LSFR : StreamEncipher {
    private var register: Register

    constructor(registerState: String){
        register = RegisterX29(registerState)
    }

    override fun encode(plainBytes: ByteArray): ByteArray {
        val plainBits = parseByteArrayToBitArray(plainBytes)
        for (i in plainBits.indices){
            plainBits[i] = plainBits[i] xor register.nextBit()
        }
        return parseBitArrayToByteArray(plainBits)
    }

    override fun decode(cipherBytes: ByteArray): ByteArray {
        val cipherBits = parseByteArrayToBitArray(cipherBytes)
        for (i in cipherBits.indices){
            cipherBits[i] = cipherBits[i] xor register.nextBit()
        }
        return parseBitArrayToByteArray(cipherBits)
    }
}
