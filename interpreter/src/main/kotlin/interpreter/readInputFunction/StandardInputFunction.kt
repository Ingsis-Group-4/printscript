package interpreter.readInputFunction

class StandardInputFunction : ReadInputFunction {
    override fun read(string: String): String? {
        print(string)
        val value = readlnOrNull()
        return if (isNotNull(value)) {
            value
        } else {
            null
        }
    }

    private fun isNotNull(value: String?) = value != null
}
