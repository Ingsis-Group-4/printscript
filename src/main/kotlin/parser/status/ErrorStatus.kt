package org.example.parser.status

class ErrorStatus(private val message: String): Status {
    // en vez de usar strings deberiamos usar error types
    override fun hasError(): Boolean {
        return true
    }

    override fun getMessage(): String {
        return message
    }
}