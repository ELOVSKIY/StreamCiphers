package Ciphers

import kotlin.experimental.*



open class LSFR : StreamEncipher {
    private var decodeBits: ByteArray
    private var encodeBits: ByteArray

    constructor(registerState: String){
        decodeBits = parseStringToBitArray(registerState)
        encodeBits = parseStringToBitArray(registerState)
    }

    override fun encode(plainBytes: ByteArray): ByteArray {
        val plainBits = parseByteArrayToBitArray(plainBytes)
        for (i in plainBits.indices){
            if (i % registerSize == 0 && i != 0)
                updateRegister(encodeBits)
            plainBits[i] = plainBits[i] xor encodeBits[i % registerSize]
        }
        return parseBitArrayToByteArray(plainBits)
    }

    override fun decode(cipherBytes: ByteArray): ByteArray {
        val cipherBits = parseByteArrayToBitArray(cipherBytes)
        for (i in cipherBits.indices){
            if (i % registerSize == 0 && i != 0)
                updateRegister(decodeBits)
            cipherBits[i] = cipherBits[i] xor decodeBits[i % registerSize]
        }
        return parseBitArrayToByteArray(cipherBits)
    }
}
