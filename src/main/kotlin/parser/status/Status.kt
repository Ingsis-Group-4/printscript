package org.example.parser.status

interface Status {
    fun hasError(): Boolean
    fun getMessage(): String
}
