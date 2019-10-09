package Registers

class RegisterX23(registerState: String): Register(registerState) {
    override val xorBit: Int = 5
    override val regSize: Int = 23
    override var regPos: Int = 0
}