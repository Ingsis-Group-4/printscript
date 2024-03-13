package org.example.parser.status

class SuccessStatus(private val message: String? = null): Status {
    override fun hasError(): Boolean {
        return false
    }

    override fun getMessage(): String {
        return message ?: "Process finished correctly with no errors."
    }
}