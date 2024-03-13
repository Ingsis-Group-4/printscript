package org.example.parser

import org.example.parser.status.Status

data class ParserState(
    //podríamos hacer una interfaz de Error y que haya una clase Valid y una Invalid y usamos pattern matching
    val status: Status,
    val lastValidatedIndex: Int,
) {
    fun getMessage(): String {
        return status.getMessage()
    }
}
