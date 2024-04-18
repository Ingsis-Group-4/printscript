package logger

class ErrorLogger : Logger {
    private var error: String = ""

    override fun log(message: String) {
        error = message
    }

    fun getError(): String {
        return error
    }
}
