package Registers

class RegisterX28(registerState: String): Register(registerState) {
    override val xorBit: Int = 3
    override val regSize: Int = 28
    override var regPos: Int = 0
}