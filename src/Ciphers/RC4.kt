package Ciphers

import kotlin.experimental.xor

open class RC4(val keyByte: ByteArray) : StreamEncipher {
    val byteSize = 256
    private var SBox = ByteArray(byteSize)


    override fun encode(plainBytes: ByteArray): ByteArray {
        var i = 0
        var j = 0
        for (byteInd in plainBytes.indices){
            i = (i + 1) % byteSize
            j = (j + SBox[i]) % byteSize
            val temp = SBox[i]
            SBox[i] = SBox[j]
            SBox[j] = temp
            val K = SBox[(SBox[i] + SBox[j]) % byteSize]
            plainBytes[byteInd] = plainBytes[byteInd] xor K
        }
        return plainBytes;
    }

    override fun decode(cipherBytes: ByteArray): ByteArray {
        var i = 0
        var j = 0
        for (byteInd in cipherBytes.indices){
            i = (i + 1) % byteSize
            j = (j + SBox[i]) % byteSize
            val temp = SBox[i]
            SBox[i] = SBox[j]
            SBox[j] = temp
            val K = SBox[(SBox[i] + SBox[j]) % byteSize]
            cipherBytes[byteInd] = cipherBytes[byteInd] xor K
        }
        return cipherBytes;
    }

    init {
        for (i in SBox.indices){
            SBox[i] = i.toByte()
        }
        var j = 0
        for (i in SBox.indices){
            j = (j + SBox[i]+ keyByte[i % keyByte.size]) % byteSize
            val temp = SBox[i]
            SBox[i] = SBox[j]
            SBox[j] = temp
        }
    }
}