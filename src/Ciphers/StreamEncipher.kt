package Ciphers

open interface StreamEncipher {
    fun code(plainBytes: ByteArray): ByteArray

    fun getKeyBits(): ByteArray;
}