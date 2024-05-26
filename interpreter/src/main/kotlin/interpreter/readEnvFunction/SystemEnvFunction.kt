package interpreter.readEnvFunction

class SystemEnvFunction : ReadEnvFunction {
    override fun read(name: String): String? {
        return System.getenv(name)
    }
}
