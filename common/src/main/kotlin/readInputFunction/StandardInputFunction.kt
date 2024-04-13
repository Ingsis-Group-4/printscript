package readInputFunction

class StandardInputFunction : ReadInputFunction {
    override fun read(string: String): ReadInputFunctionResult {
        println(string)
        val value = readlnOrNull()
        return if (isNull(value)) {
            ReadInputFunctionSuccess(value!!)
        } else {
            ReadInputFunctionFailure()
        }
    }

    private fun isNull(value: String?) = value != null
}
