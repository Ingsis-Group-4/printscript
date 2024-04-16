package writer

interface Writer {
    fun write(
        content: String,
        path: String,
    )
}
