package interpreter.readEnvFunction

class EnvMapFunction(
    private val envMap: Map<String, String>,
) : ReadEnvFunction {
    override fun read(name: String): String? {
        return envMap[name]
    }
}
