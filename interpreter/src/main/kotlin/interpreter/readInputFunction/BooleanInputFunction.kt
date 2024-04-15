package interpreter.readInputFunction

class BooleanInputFunction : ReadInputFunction {
    override fun read(string: String): String? {
        return "true"
    }
}
