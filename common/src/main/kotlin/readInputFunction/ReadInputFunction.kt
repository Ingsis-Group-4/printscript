package readInputFunction

interface ReadInputFunctionResult

class ReadInputFunctionSuccess(val value: String) : ReadInputFunctionResult

class ReadInputFunctionFailure : ReadInputFunctionResult

interface ReadInputFunction {
    fun read(string: String): ReadInputFunctionResult
}
