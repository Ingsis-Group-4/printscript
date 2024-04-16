package writer

class LogWriter : Writer {
    private var output = ""

    override fun write(
        content: String,
        path: String,
    ) {
        output = output.plus(content)
    }

    fun getOutput(): String {
        return output
    }
}
