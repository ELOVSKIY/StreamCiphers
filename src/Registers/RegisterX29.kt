package Registers

class RegisterX29(registerState: String) : Register(registerState) {
    override val xorBit: Int = 2
    override var regPos: Int = 0
    override var regSize: Int = 29
}