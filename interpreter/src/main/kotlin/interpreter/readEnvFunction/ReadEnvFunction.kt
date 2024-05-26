package interpreter.readEnvFunction

interface ReadEnvFunction {
    fun read(name: String): String?
}
