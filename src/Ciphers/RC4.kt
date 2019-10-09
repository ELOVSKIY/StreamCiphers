package Ciphers

import kotlin.experimental.xor

open class RC4(val U: ByteArray) : StreamEncipher {
    private val byteSize = 256
    private var S = ByteArray(byteSize)


    override fun code(plainBytes: ByteArray): ByteArray {
        var i = 0
        var j = 0
        for (byteInd in plainBytes.indices){
            i = ((i + 1) + byteSize) % byteSize
            j = ((j + S[i]) + byteSize) % byteSize
            val temp = S[i]
            S[i] = S[j]
            S[j] = temp
            val K = S[((S[i] + S[j]) + byteSize) % byteSize]
            plainBytes[byteInd] = plainBytes[byteInd] xor K
        }
        return plainBytes;
    }


    override fun getKeyBits(): ByteArray {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    init {
        for (i in S.indices){
            S[i] = i.toByte()
        }
        var j: Int = 0
        for (i in S.indices){
            j = ((j + S[i]+ U[i % U.size]) + byteSize) % byteSize
            val temp = S[i]
            S[i] = S[j]
            S[j] = temp
        }
    }
}