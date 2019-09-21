package Ciphers

open interface StreamEncipher {
    fun encode(plainBytes: ByteArray): ByteArray

    fun decode(cipherBytes: ByteArray): ByteArray
}