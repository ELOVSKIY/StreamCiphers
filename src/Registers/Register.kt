package Registers

import Ciphers.parseStringToBitArray
import kotlin.experimental.xor

abstract class Register(registerState: String) {
    abstract val xorBit: Int
    abstract val regSize: Int
    abstract var regPos: Int;
    abstract var register: ByteArray

    init {
        register = parseStringToBitArray(registerState)
    }

    fun updateRegister(){
        for (i in register) {
            shiftRegister()
        }
    }

    fun shiftRegister(){
        val generatedBit =
                register[register.size - regSize] xor register[register.size - xorBit] // Осторожно тестировалось на других
        for (i in 0 until register.size - 1) {
            register[i] = register[i + 1]
        }
        register[register.size - 1] = generatedBit
    }

    fun nextBit(): Byte{
        val bite = register[regPos]
        regPos++
        if (regPos == regSize) {
            updateRegister()
            regPos = 0
        }
        return bite
    }

}