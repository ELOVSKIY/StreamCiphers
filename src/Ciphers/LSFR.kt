package Ciphers

import Registers.*
import kotlin.experimental.*



open class LSFR(registerState: String) : StreamEncipher {
    private var register: Register = RegisterX29(registerState)

    override fun code(plainBytes: ByteArray): ByteArray {
        val plainBits = parseByteArrayToBitArray(plainBytes)
        for (i in plainBits.indices){
            plainBits[i] = plainBits[i] xor register.nextBit()
        }
        return parseBitArrayToByteArray(plainBits)
    }


    override fun getKeyBits(): ByteArray {
       return ByteArray(0)
    }
}
